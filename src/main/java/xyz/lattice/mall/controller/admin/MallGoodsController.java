package xyz.lattice.mall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.common.MallCategoryLevelEnum;
import xyz.lattice.mall.common.MallException;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.entity.GoodsCategory;
import xyz.lattice.mall.entity.MallGoods;
import xyz.lattice.mall.service.MallCategoryService;
import xyz.lattice.mall.service.MallGoodsService;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.Result;
import xyz.lattice.mall.util.ResultGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class MallGoodsController {
    @Resource
    private MallGoodsService mallGoodsService;
    @Resource
    private MallCategoryService mallCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "mall_goods");
        return "admin/mall_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), MallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), MallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/mall_goods_edit";
            }
        }
        MallException.fail("分类数据不完善");
        return "error/error";
    }

    // 添加
    @PostMapping("/goods/save")
    @ResponseBody
    public Result save(@RequestBody MallGoods mallGoods) {
        if (mallGoods.getGoodsName().isEmpty()
                || mallGoods.getGoodsIntro().isEmpty()
                || mallGoods.getTag().isEmpty()
                || Objects.isNull(mallGoods.getOriginalPrice())
                || Objects.isNull(mallGoods.getGoodsCategoryId())
                || Objects.isNull(mallGoods.getSellingPrice())
                || Objects.isNull(mallGoods.getStockNum())
                || Objects.isNull(mallGoods.getGoodsSellStatus())
                || mallGoods.getGoodsCoverImg().isEmpty()
                || mallGoods.getGoodsDetailContent().isEmpty()) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallGoodsService.saveMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        MallGoods mallGoods = mallGoodsService.getMallGoodsById(goodsId);
        if (mallGoods.getGoodsCategoryId() > 0) {
            if (mallGoods.getGoodsCategoryId() != null || mallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = mallCategoryService.getGoodsCategoryById(mallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), MallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = mallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), MallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firstCategory = mallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firstCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firstCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (mallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), MallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = mallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), MallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", mallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/mall_goods_edit";
    }

    // 列表
    @GetMapping("/goods/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (params.get("page") == null || params.get("limit") == null) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallGoodsService.getMallGoodsPage(pageUtil));
    }

    // 修改
    @PostMapping("/goods/update")
    @ResponseBody
    public Result update(@RequestBody MallGoods mallGoods) {
        if (Objects.isNull(mallGoods.getGoodsId())
                || mallGoods.getGoodsName().isEmpty()
                || mallGoods.getGoodsIntro().isEmpty()
                || mallGoods.getTag().isEmpty()
                || Objects.isNull(mallGoods.getOriginalPrice())
                || Objects.isNull(mallGoods.getSellingPrice())
                || Objects.isNull(mallGoods.getGoodsCategoryId())
                || Objects.isNull(mallGoods.getStockNum())
                || Objects.isNull(mallGoods.getGoodsSellStatus())
                || mallGoods.getGoodsCoverImg().isEmpty()
                || mallGoods.getGoodsDetailContent().isEmpty()) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallGoodsService.updateMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    // 详情
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        MallGoods goods = mallGoodsService.getMallGoodsById(id);
        return ResultGenerator.genSuccessResult(goods);
    }

    // 批量修改销售状态
    @PutMapping("/goods/status/{sellStatus}")
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (mallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }
}
