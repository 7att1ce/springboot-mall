package xyz.lattice.mall.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.common.IndexConfigTypeEnum;
import xyz.lattice.mall.common.MallException;
import xyz.lattice.mall.controller.vo.MallIndexCarouselVO;
import xyz.lattice.mall.controller.vo.MallIndexCategoryVO;
import xyz.lattice.mall.controller.vo.MallIndexConfigGoodsVO;
import xyz.lattice.mall.service.MallCarouselService;
import xyz.lattice.mall.service.MallCategoryService;
import xyz.lattice.mall.service.MallIndexConfigService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private MallCarouselService mallCarouselService;

    @Resource
    private MallIndexConfigService mallIndexConfigService;

    @Resource
    private MallCategoryService mallCategoryService;

    @GetMapping({"/", "/index", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<MallIndexCategoryVO> categories = mallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MallException.fail("分类数据不完善");
        }
        List<MallIndexCarouselVO> carousels = mallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<MallIndexConfigGoodsVO> hotGoods = mallIndexConfigService.getConfigGoodsForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<MallIndexConfigGoodsVO> newGoods = mallIndexConfigService.getConfigGoodsForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<MallIndexConfigGoodsVO> recommendGoods = mallIndexConfigService.getConfigGoodsForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMEND.getType(), Constants.INDEX_GOODS_RECOMMEND_NUMBER);
        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoods", hotGoods);//热销商品
        request.setAttribute("newGoods", newGoods);//新品
        request.setAttribute("recommendGoods", recommendGoods);//推荐商品
        return "mall/index";
    }
}
