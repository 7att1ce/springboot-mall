package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.MallShoppingCartItem;

import java.util.List;

public interface MallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(MallShoppingCartItem record);
    // 保存一条新记录
    int insertSelective(MallShoppingCartItem record);

    MallShoppingCartItem selectByPrimaryKey(Long cartItemId);
    // 根据userId和goodsId查询记录
    MallShoppingCartItem selectByUserIdAndGoodsId(@Param("mallUserId") Long mallUserId, @Param("goodsId") Long goodsId);

    List<MallShoppingCartItem> selectByUserId(@Param("mallUserId") Long mallUserId, @Param("number") int number);
    // 根据userId查询当前用户已添加了多少条记录
    int selectCountByUserId(Long mallUserId);

    int updateByPrimaryKeySelective(MallShoppingCartItem record);

    int updateByPrimaryKey(MallShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}
