package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.GoodsCategory;
import xyz.lattice.mall.util.PageQueryUtil;

import java.util.List;

public interface GoodsCategoryMapper {

    // 删除一条记录
    int deleteByPrimaryKey(Long categoryId);

    // 保存一条新记录
    int insert(GoodsCategory record);

    // 保存一条新记录
    int insertSelective(GoodsCategory record);

    // 根据主键查询记录
    GoodsCategory selectByPrimaryKey(Long categoryId);

    // 根据分类等级和分类名称查询一条分类记录
    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    // 修改记录
    int updateByPrimaryKeySelective(GoodsCategory record);

    // 修改记录
    int updateByPrimaryKey(GoodsCategory record);

    // 查询分页数据
    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    // 查询总数
    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    // 批量删除
    int deleteBatch(Integer[] ids);

    // 根据父类分类id、分类等级和数量查询分类列表
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);
}
