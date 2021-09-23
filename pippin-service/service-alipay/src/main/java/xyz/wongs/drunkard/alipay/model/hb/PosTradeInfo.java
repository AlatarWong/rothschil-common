package xyz.wongs.drunkard.alipay.model.hb;

/** 机具商同步使用的交易结构体
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @description //TODO
 * @github <a>https://github.com/rothschil</a>
 * @date 2021/9/23 - 10:05
 * @version 1.0.0
 */
public class PosTradeInfo implements TradeInfo {
    private HbStatus status;
    private String time;
    private int timeConsume;

    private PosTradeInfo() {
        // no public constructor.
    }

    public static PosTradeInfo newInstance(HbStatus status, String time, int timeConsume) {
        PosTradeInfo info = new PosTradeInfo();
        if (timeConsume > 99 || timeConsume < 0) {
            timeConsume = 99;
        }
        info.setTimeConsume(timeConsume);
        info.setStatus(status);
        info.setTime(time);
        return info;
    }

    @Override
    public String toString() {
        return new StringBuilder(status.name())
                .append(time)
                .append(String.format("%02d", timeConsume))
                .toString();
    }

    @Override
    public HbStatus getStatus() {
        return status;
    }

    public void setStatus(HbStatus status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public double getTimeConsume() {
        return (double) timeConsume;
    }

    public void setTimeConsume(int timeConsume) {
        this.timeConsume = timeConsume;
    }
}
