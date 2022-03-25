package xyz.lattice.mall.service.impl;

import org.springframework.stereotype.Service;
import xyz.lattice.mall.dao.AdminUserMapper;
import xyz.lattice.mall.entity.AdminUser;
import xyz.lattice.mall.service.AdminUserService;
import xyz.lattice.mall.util.MD5Util;

import javax.annotation.Resource;

/**
 * 管理员用户业务接口实现
 */

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;

    // 将密码转化为md5, 再进行数据库查询
    @Override
    public AdminUser login(String userName, String password) {
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        return adminUserMapper.login(userName, passwordMD5);
    }

    // 获取用户
    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    // 修改用户密码
    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            //比较原密码是否正确
            if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
                //设置新密码并修改
                adminUser.setLoginPassword(newPasswordMd5);
                //修改成功则返回true
                return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
            }
        }
        return false;
    }

    // 修改用户昵称
    @Override
    public Boolean updateName(Integer loginUserId, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            // 设置新名称并修改
            adminUser.setNickName(nickName);
            //修改成功则返回true
            return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
        }
        return false;
    }
}
