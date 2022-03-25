package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.Carousel;
import xyz.lattice.mall.util.PageQueryUtil;

import java.util.List;

/**
 * 轮播图Mapper接口
 */

public interface CarouselMapper {
    // 删除一条记录
    int deleteByPrimaryKey(Integer carouselId);

    // 保存一条新记录
    int insert(Carousel record);

    // 保存一条新记录
    int insertSelective(Carousel record);

    // 根据主键查询记录
    Carousel selectByPrimaryKey(Integer carouselId);

    // 修改记录
    int updateByPrimaryKeySelective(Carousel record);

    // 修改记录
    int updateByPrimaryKey(Carousel record);

    // 查询分页数据
    List<Carousel> findCarouselList(PageQueryUtil pageUtil);

    // 查询总数
    int getTotalCarousels(PageQueryUtil pageUtil);

    // 批量删除
    int deleteBatch(Integer[] ids);

    // 查询固定数量的记录
    List<Carousel> findCarouselsByNum(@Param("number") int number);
}
