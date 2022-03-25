package xyz.lattice.mall.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.lattice.mall.common.MallCategoryLevelEnum;
import xyz.lattice.mall.common.MallException;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.controller.vo.MallSearchGoodsVO;
import xyz.lattice.mall.dao.GoodsCategoryMapper;
import xyz.lattice.mall.dao.MallGoodsMapper;
import xyz.lattice.mall.entity.GoodsCategory;
import xyz.lattice.mall.entity.MallGoods;
import xyz.lattice.mall.service.MallGoodsService;
import xyz.lattice.mall.util.BeanUtil;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.PageResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MallGoodsServiceImpl implements MallGoodsService {
    private final MallGoodsMapper goodsMapper;
    private final GoodsCategoryMapper goodsCategoryMapper;

    public MallGoodsServiceImpl(MallGoodsMapper goodsMapper, GoodsCategoryMapper goodsCategoryMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsCategoryMapper = goodsCategoryMapper;
    }

    @Override
    public PageResult getMallGoodsPage(PageQueryUtil pageUtil) {
        List<MallGoods> goodsList = goodsMapper.findMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalMallGoods(pageUtil);
        return new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public String saveMallGoods(MallGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveMallGoods(List<MallGoods> mallGoodsList) {
        if (!CollectionUtils.isEmpty(mallGoodsList)) {
            goodsMapper.batchInsert(mallGoodsList);
        }
    }

    @Override
    public String updateMallGoods(MallGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        MallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        MallGoods temp2 = goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MallGoods getMallGoodsById(Long id) {
        MallGoods mallGoods = goodsMapper.selectByPrimaryKey(id);
        if (mallGoods == null) {
            MallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return mallGoods;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchMallGoods(PageQueryUtil pageUtil) {
        List<MallGoods> goodsList = goodsMapper.findMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalMallGoodsBySearch(pageUtil);
        List<MallSearchGoodsVO> mallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            mallSearchGoodsVOS = BeanUtil.copyList(goodsList, MallSearchGoodsVO.class);
            for (MallSearchGoodsVO mallSearchGoodsVO : mallSearchGoodsVOS) {
                String goodsName = mallSearchGoodsVO.getGoodsName();
                String goodsIntro = mallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    mallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    mallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return new PageResult(mallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
    }
}
