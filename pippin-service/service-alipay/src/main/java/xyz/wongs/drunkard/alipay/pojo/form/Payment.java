package xyz.wongs.drunkard.alipay.pojo.form;

import java.io.Serializable;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/24 - 14:49
 * @version 1.0.0
 */
public class Payment implements Serializable {

    private String outTradeNo;
    private String subject;
    private int totalAmount;
    private float unAmount;

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

    @Override
    public String toString() {
        return "Payment{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", subject='" + subject + '\'' +
                ", totalAmount=" + totalAmount +
                ", unAmount=" + unAmount +
                '}';
    }
}
