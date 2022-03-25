package xyz.lattice.mall.entity;

import java.util.Date;

public class MallOrderItem {
    private Long orderItemId; // 订单关联购物项主键id
    private Long orderId; // 订单主键id
    private Long goodsId; // 关联商品id
    private String goodsName; // 下单时商品的名称(订单快照)
    private String goodsCoverImg; // 下单时商品的主图(订单快照)
    private Integer sellingPrice; // 下单时商品的价格(订单快照)
    private Integer goodsCount; // 数量(订单快照)
    private Date createTime; // 创建时间

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg == null ? null : goodsCoverImg.trim();
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", goodsCoverImg=" + goodsCoverImg +
                ", sellingPrice=" + sellingPrice +
                ", goodsCount=" + goodsCount +
                ", createTime=" + createTime +
                "]";
    }
}
