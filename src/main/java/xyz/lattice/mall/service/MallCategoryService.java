package xyz.lattice.mall.service;

import xyz.lattice.mall.controller.vo.MallIndexCategoryVO;
import xyz.lattice.mall.controller.vo.SearchPageCategoryVO;
import xyz.lattice.mall.entity.GoodsCategory;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.PageResult;

import java.util.List;

/**
 * 商品分类服务接口
 */

public interface MallCategoryService {
    // 查询后台管理系统分类分页数据
    PageResult getCategoriesPage(PageQueryUtil pageUtil);
    // 查询后台管理系统分类分页数据
    String saveCategory(GoodsCategory goodsCategory);
    // 修改一条分类记录
    String updateGoodsCategory(GoodsCategory goodsCategory);
    // 根据主键查询分类记录
    GoodsCategory getGoodsCategoryById(Long id);
    // 批量删除分类数据
    Boolean deleteBatch(Integer[] ids);
    // 根据parentId和level获取分类列表
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
    // 返回分类数据(首页调用)
    List<MallIndexCategoryVO> getCategoriesForIndex();
    // 返回分类数据(搜索页调用)
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);
}
