package xyz.lattice.mall.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.common.MallException;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.controller.vo.MallGoodsDetailVO;
import xyz.lattice.mall.controller.vo.SearchPageCategoryVO;
import xyz.lattice.mall.entity.MallGoods;
import xyz.lattice.mall.service.MallCategoryService;
import xyz.lattice.mall.service.MallGoodsService;
import xyz.lattice.mall.util.BeanUtil;
import xyz.lattice.mall.util.PageQueryUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodController {
    @Resource
    private MallGoodsService mallGoodsService;
    @Resource
    private MallCategoryService mallCategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (params.get("page") == null) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !(params.get("goodsCategoryId") + "").isEmpty()) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = mallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !(params.get("orderBy") + "").isEmpty()) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !(params.get("keyword") + "").trim().isEmpty()) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", mallGoodsService.searchMallGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            MallException.fail("参数异常");
        }
        MallGoods goods = mallGoodsService.getMallGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            MallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        MallGoodsDetailVO goodsDetailVO = new MallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "mall/detail";
    }
}
