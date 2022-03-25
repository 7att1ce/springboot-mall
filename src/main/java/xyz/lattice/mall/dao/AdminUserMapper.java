package xyz.lattice.mall.dao;

import org.apache.ibatis.annotations.Param;
import xyz.lattice.mall.entity.AdminUser;

/**
 * 管理员用户Mapper接口
 */

public interface AdminUserMapper {

    // 管理员用户登录
    AdminUser login(@Param("userName") String userName, @Param("password") String password);
    // 根据ID选择用户
    AdminUser selectByPrimaryKey(Integer adminUserId);
    // 修改用户信息
    int updateByPrimaryKeySelective(AdminUser record);
}
