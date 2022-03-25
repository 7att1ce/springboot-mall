package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.IndexConfig;
import xyz.lattice.mall.util.PageQueryUtil;

import java.util.List;

public interface IndexConfigMapper {
    // 删除一条记录
    int deleteByPrimaryKey(Long configId);
    // 保存一条新记录
    int insert(IndexConfig record);
    // 保存一条新记录
    int insertSelective(IndexConfig record);
    // 根据主键查询记录
    IndexConfig selectByPrimaryKey(Long configId);
    // 根据主键查询记录
    IndexConfig selectByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);
    // 修改记录
    int updateByPrimaryKeySelective(IndexConfig record);
    // 修改记录
    int updateByPrimaryKey(IndexConfig record);
    // 查询分页数据
    List<IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);
    // 查询总数
    int getTotalIndexConfigs(PageQueryUtil pageUtil);
    // 批量删除
    int deleteBatch(Long[] ids);
    // 根据配置类型查询固定数量的记录
    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}
