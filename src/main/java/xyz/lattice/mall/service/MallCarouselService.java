package xyz.lattice.mall.service;

import xyz.lattice.mall.controller.vo.MallIndexCarouselVO;
import xyz.lattice.mall.entity.Carousel;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.PageResult;

import java.util.List;

/**
 * 轮播图业务接口
 */

public interface MallCarouselService {
    // 查询后台管理系统轮播图分页数据
    PageResult getCarouselPage(PageQueryUtil pageUtil);
    // 新增一条轮播图记录
    String saveCarousel(Carousel carousel);
    // 修改一条轮播图记录
    String updateCarousel(Carousel carousel);
    // 根据主键查询轮播图记录
    Carousel getCarouselById(Integer id);
    // 批量删除轮播图记录
    Boolean deleteBatch(Integer[] ids);
    // 返回固定数量的轮播图对象(首页调用)
    List<MallIndexCarouselVO> getCarouselsForIndex(int number);
}
