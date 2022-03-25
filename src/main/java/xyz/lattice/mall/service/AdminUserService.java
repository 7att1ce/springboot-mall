package xyz.lattice.mall.service;

import xyz.lattice.mall.entity.AdminUser;

/**
 * 管理员用户业务接口
 */

public interface AdminUserService {

    // 登录
    AdminUser login(String userName, String password);
    // 获取用户
    AdminUser getUserDetailById(Integer loginUserId);
    // 更改用户密码
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);
    // 更改用户昵称
    Boolean updateName(Integer loginUserId, String nickName);
}
