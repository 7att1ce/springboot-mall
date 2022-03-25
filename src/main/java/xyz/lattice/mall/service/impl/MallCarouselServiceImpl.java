package xyz.lattice.mall.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.controller.vo.MallIndexCarouselVO;
import xyz.lattice.mall.dao.CarouselMapper;
import xyz.lattice.mall.entity.Carousel;
import xyz.lattice.mall.service.MallCarouselService;
import xyz.lattice.mall.util.BeanUtil;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.PageResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 轮播图服务接口实现
 */

@Service
public class MallCarouselServiceImpl implements MallCarouselService {

    private final CarouselMapper carouselMapper;

    public MallCarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselMapper = carouselMapper;
    }

    @Override
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = carouselMapper.findCarouselList(pageUtil);
        int total = carouselMapper.getTotalCarousels(pageUtil);
        return new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public String saveCarousel(Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCarousel(Carousel carousel) {
        Carousel temp = carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(new Date());
        if (carouselMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return carouselMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<MallIndexCarouselVO> getCarouselsForIndex(int number) {
        List<MallIndexCarouselVO> mallIndexCarouselVOS = new ArrayList<>(number);
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);
        if (!CollectionUtils.isEmpty(carousels)) {
            mallIndexCarouselVOS = BeanUtil.copyList(carousels, MallIndexCarouselVO.class);
        }
        return mallIndexCarouselVOS;
    }
}
