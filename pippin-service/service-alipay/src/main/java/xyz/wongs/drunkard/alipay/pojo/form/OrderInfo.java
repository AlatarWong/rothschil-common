package xyz.wongs.drunkard.alipay.pojo.form;

import xyz.wongs.drunkard.common.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 订单信息
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/24 - 14:49
 * @since 1.0.0
 */
public class OrderInfo implements Serializable {

    /**
     * 订单编号
     */
    private String outTradeNo;
    /**
     * 订单名称
     */
    private String subject;

    /**
     * 付款金额
     */
    private int totalAmount;

    /**
     * 不可打折金额
     */
    private float unAmount;

    /**
     * 统一商品编号
     */
    private List<String> alipayGoodsIds;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getUnAmount() {
        return unAmount;
    }

    public void setUnAmount(float unAmount) {
        this.unAmount = unAmount;
    }

    public List<String> getAlipayGoodsIds() {
        if(null ==alipayGoodsIds ){
            alipayGoodsIds = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                alipayGoodsIds.add(StringUtils.getRandomString(4));
            }
        }
        return alipayGoodsIds;
    }

    public void setAlipayGoodsIds(List<String> alipayGoodsIds) {
        this.alipayGoodsIds = alipayGoodsIds;
    }
}
