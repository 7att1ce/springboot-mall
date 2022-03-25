package xyz.lattice.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class IndexConfig {
    private Long configId; // 首页配置项主键id
    private String configName; // 首页配置项主键id
    private Byte configType; // 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
    private Long goodsId; // 商品id 默认为0
    private String redirectUrl; // 点击后的跳转地址(默认不跳转)
    private Integer configRank; // 排序值(字段越大越靠前)
    private Byte isDeleted; // 排序值(字段越大越靠前)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; // 创建时间
    private Integer createUser; // 创建者id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime; // 最新修改时间
    private Integer updateUser; // 修改者id

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public Byte getConfigType() {
        return configType;
    }

    public void setConfigType(Byte configType) {
        this.configType = configType;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl == null ? null : redirectUrl.trim();
    }

    public Integer getConfigRank() {
        return configRank;
    }

    public void setConfigRank(Integer configRank) {
        this.configRank = configRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", configId=" + configId +
                ", configName=" + configName +
                ", configType=" + configType +
                ", goodsId=" + goodsId +
                ", redirectUrl=" + redirectUrl +
                ", configRank=" + configRank +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                "]";
    }
}
