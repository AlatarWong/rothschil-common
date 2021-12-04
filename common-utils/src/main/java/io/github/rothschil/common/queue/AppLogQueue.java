package io.github.rothschil.common.queue;

import io.github.rothschil.base.aop.queue.AbstractQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 异步处理日志的队列，将需要存储的日志放入这个队列中
 *
 * @author WCNGS@QQ.COM
 * @date 20/11/13 16:14
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Component
public class AppLogQueue extends AbstractQueue {

    private static final Logger LOG = LoggerFactory.getLogger(AppLogQueue.class);
    private static final LinkedBlockingQueue<AppLogHandler> queue = new LinkedBlockingQueue<>(200);

    AppLogQueue(){
        super(queue);
    }


}
