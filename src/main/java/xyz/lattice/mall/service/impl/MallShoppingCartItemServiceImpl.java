package xyz.lattice.mall.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.controller.vo.MallShoppingCartItemVO;
import xyz.lattice.mall.dao.MallGoodsMapper;
import xyz.lattice.mall.dao.MallShoppingCartItemMapper;
import xyz.lattice.mall.entity.MallGoods;
import xyz.lattice.mall.entity.MallShoppingCartItem;
import xyz.lattice.mall.service.MallShoppingCartItemService;
import xyz.lattice.mall.util.BeanUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MallShoppingCartItemServiceImpl implements MallShoppingCartItemService {
    private final MallShoppingCartItemMapper mallShoppingCartItemMapper;

    private final MallGoodsMapper mallGoodsMapper;

    public MallShoppingCartItemServiceImpl(MallShoppingCartItemMapper mallShoppingCartItemMapper, MallGoodsMapper mallGoodsMapper) {
        this.mallShoppingCartItemMapper = mallShoppingCartItemMapper;
        this.mallGoodsMapper = mallGoodsMapper;
    }

    @Override
    public String saveMallCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem temp = mallShoppingCartItemMapper.selectByUserIdAndGoodsId(mallShoppingCartItem.getUserId(), mallShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setGoodsCount(mallShoppingCartItem.getGoodsCount());
            return updateMallCartItem(temp);
        }
        MallGoods mallGoods = mallGoodsMapper.selectByPrimaryKey(mallShoppingCartItem.getGoodsId());
        //商品为空
        if (mallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = mallShoppingCartItemMapper.selectCountByUserId(mallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (mallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (mallShoppingCartItemMapper.insertSelective(mallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateMallCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem mallShoppingCartItemUpdate = mallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItem.getCartItemId());
        if (mallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (mallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!mallShoppingCartItemUpdate.getUserId().equals(mallShoppingCartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (mallShoppingCartItem.getGoodsCount().equals(mallShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        mallShoppingCartItemUpdate.setGoodsCount(mallShoppingCartItem.getGoodsCount());
        mallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (mallShoppingCartItemMapper.updateByPrimaryKeySelective(mallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MallShoppingCartItem getMallCartItemById(Long mallShoppingCartItemId) {
        return mallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        MallShoppingCartItem mallShoppingCartItem = mallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (mallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(mallShoppingCartItem.getUserId())) {
            return false;
        }
        return mallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<MallShoppingCartItemVO> getMyShoppingCartItems(Long mallUserId) {
        List<MallShoppingCartItemVO> mallShoppingCartItemVOS = new ArrayList<>();
        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.selectByUserId(mallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(mallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> mallGoodsIds = mallShoppingCartItems.stream().map(MallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MallGoods> mallGoods = mallGoodsMapper.selectByPrimaryKeys(mallGoodsIds);
            Map<Long, MallGoods> mallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(mallGoods)) {
                mallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (MallShoppingCartItem mallShoppingCartItem : mallShoppingCartItems) {
                MallShoppingCartItemVO mallShoppingCartItemVO = new MallShoppingCartItemVO();
                BeanUtil.copyProperties(mallShoppingCartItem, mallShoppingCartItemVO);
                if (mallGoodsMap.containsKey(mallShoppingCartItem.getGoodsId())) {
                    MallGoods mallGoodsTemp = mallGoodsMap.get(mallShoppingCartItem.getGoodsId());
                    mallShoppingCartItemVO.setGoodsCoverImg(mallGoodsTemp.getGoodsCoverImg());
                    String goodsName = mallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    mallShoppingCartItemVO.setGoodsName(goodsName);
                    mallShoppingCartItemVO.setSellingPrice(mallGoodsTemp.getSellingPrice());
                    mallShoppingCartItemVOS.add(mallShoppingCartItemVO);
                }
            }
        }
        return mallShoppingCartItemVOS;
    }

}
