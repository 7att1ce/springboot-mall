package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.MallGoods;
import xyz.lattice.mall.entity.StockNumDTO;
import xyz.lattice.mall.util.PageQueryUtil;

import java.util.List;

public interface MallGoodsMapper {

    int deleteByPrimaryKey(Long goodsId);

    int insert(MallGoods record);
    // 保存一条新记录
    int insertSelective(MallGoods record);
    // 根据主键id获取记录
    MallGoods selectByPrimaryKey(Long goodsId);
    // 搜索商品
    MallGoods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);
    // 修改一条记录
    int updateByPrimaryKeySelective(MallGoods record);

    int updateByPrimaryKeyWithBLOBs(MallGoods record);

    int updateByPrimaryKey(MallGoods record);
    // 查询分页数据
    List<MallGoods> findMallGoodsList(PageQueryUtil pageUtil);
    // 查询总数
    int getTotalMallGoods(PageQueryUtil pageUtil);

    List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<MallGoods> findMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("mallGoodsList") List<MallGoods> mallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);
    // 批量修改记录
    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);
}
