package xyz.wongs.drunkard.design.observer.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.base.utils.bean.SpringUtils;
import xyz.wongs.drunkard.design.observer.Article;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 14:55
 * @since 1.0.0
 */
@Slf4j
@Component
public class BrcService {

    @Autowired
    private BloggerService bloggerService;

    @Async
    public void brcToBlog(String blogName, Article article){
        bloggerService.setBlogName(blogName);
        FansService fansService;
        for(int i=0;i<6;i++){
            fansService = SpringUtils.getBean("fansService");
            log.info(Thread.currentThread().getName()+ "[fansService hash Code]= {}",fansService.hashCode());
            fansService.setName("粉丝"+i);
            bloggerService.addObserver(fansService);
        }
        bloggerService.productArticle(article);
    }
}
