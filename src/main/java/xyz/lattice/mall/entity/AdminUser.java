package xyz.lattice.mall.entity;

/**
 * 管理员用户实体类
 */

public class AdminUser {
    // 管理员ID
    private Integer adminUserId;
    // 管理员用户名
    private String loginUserName;
    // 管理员密码
    private String loginPassword;
    // 管理员昵称
    private String nickName;
    // 管理员是否被锁定, 0未锁定, 1为已锁定无法登录
    private Byte locked;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.strip();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.strip();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.strip();
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", adminUserId=" + adminUserId +
                ", loginUserName=" + loginUserName +
                ", loginPassword=" + loginPassword +
                ", nickName=" + nickName +
                ", locked=" + locked +
                "]";
    }
}
