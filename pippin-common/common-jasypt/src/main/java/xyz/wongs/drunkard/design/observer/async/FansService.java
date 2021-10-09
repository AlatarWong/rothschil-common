package xyz.wongs.drunkard.design.observer.async;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.thread.ThreadPoolsUtil;
import xyz.wongs.drunkard.design.observer.Article;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/** 观察者, 原型模式
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 14:07
 * @since 1.0.0
 */
@Component
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FansService implements Observer {

    @Getter
    @Setter
    private String name;

    /**
     * 线程池
     */
    private ExecutorService executorService = ThreadPoolsUtil.doCreate(5,"observer");

    @Override
    public void update(Observable o, Object arg) {
        executorService.execute(()->{
            BloggerService blogger=(BloggerService)o;
            Article article=(Article)arg;
            log.error("粉丝{}，看见{}的博主发表主题为{}的微博，内容为{}",name,blogger.getBlogName(),article.getTitile(),article.getContext());
        });

    }

    public FansService(){}
}
