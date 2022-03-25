package xyz.lattice.mall.service;

import xyz.lattice.mall.controller.vo.MallIndexConfigGoodsVO;
import xyz.lattice.mall.entity.IndexConfig;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.PageResult;

import java.util.List;

public interface MallIndexConfigService {
    // 查询后台管理系统首页配置分页数据
    PageResult getConfigsPage(PageQueryUtil pageUtil);
    // 新增一条首页配置记录
    String saveIndexConfig(IndexConfig indexConfig);
    // 修改一条首页配置记录
    String updateIndexConfig(IndexConfig indexConfig);
    // 返回固定数量的首页配置商品对象(首页调用)
    List<MallIndexConfigGoodsVO> getConfigGoodsForIndex(int configType, int number);
    // 批量删除
    Boolean deleteBatch(Long[] ids);
}
