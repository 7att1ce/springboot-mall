package xyz.lattice.mall.entity;

import java.util.Date;

public class MallShoppingCartItem {
    private Long cartItemId; // 购物项主键id
    private Long userId; // 用户主键id
    private Long goodsId; // 关联商品id
    private Integer goodsCount; // 数量(最大为5)
    private Byte isDeleted; // 删除标识字段(0-未删除 1-已删除)
    private Date createTime; // 创建时间
    private Date updateTime; // 最新修改时间

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", cartItemId=" + cartItemId +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", goodsCount=" + goodsCount +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "]";
    }
}
