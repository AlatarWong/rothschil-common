package xyz.wongs.drunkard.alipay.service.impl.hb;

import com.alipay.api.response.MonitorHeartbeatSynResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xyz.wongs.drunkard.alipay.config.PayConst;
import xyz.wongs.drunkard.alipay.model.builder.AlipayHeartbeatSynRequestBuilder;
import xyz.wongs.drunkard.alipay.service.AlipayMonitorService;
import xyz.wongs.drunkard.base.message.enums.Status;
import xyz.wongs.drunkard.base.message.exception.DrunkardException;
import xyz.wongs.drunkard.base.property.PropConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** 抽象的交易保障数据收集器，系统商创建自己的子类用于自定义收集数据,参考DemoHbRunner
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 *
 * @date 2021/9/23 - 10:13
 * @since 1.0.0
 */
public abstract class AbsHbRunner implements Runnable {
    protected Log log = LogFactory.getLog(getClass());

    // 获取交易保障所需的信息
    public abstract AlipayHeartbeatSynRequestBuilder getBuilder();

    // 获取商户授权令牌，系统商通过此令牌帮助商户发起请求，完成业务
    public abstract String getAppAuthToken();

    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
    private AlipayMonitorService monitorService;

    private long delay = 0;
    private long duration = 0;

    public AbsHbRunner(AlipayMonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @Override
    public void run() {
        AlipayHeartbeatSynRequestBuilder builder = getBuilder();
        builder.setAppAuthToken(getAppAuthToken());

        MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);

        StringBuilder sb = new StringBuilder(response.getCode())
                                .append(":")
                                .append(response.getMsg());
        if (StringUtils.isNotEmpty(response.getSubCode())) {
            sb.append(", ")
            .append(response.getSubCode())
            .append(":")
            .append(response.getSubMsg());
        }
        log.info(sb.toString());
    }

    public void schedule() {
        try {
            if (delay == 0) {
                delay = Integer.parseInt(PropConfig.getProperty(PayConst.HEART_DELAY));
            }
            if (duration == 0) {
                duration = Integer.parseInt(PropConfig.getProperty(PayConst.CANCEL_DURATION));
            }
        } catch (NumberFormatException e) {
            throw new DrunkardException(Status.NUMBER_FORMAT);
        }
        scheduledService.scheduleAtFixedRate(this, delay, duration, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduledService.shutdown();
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
