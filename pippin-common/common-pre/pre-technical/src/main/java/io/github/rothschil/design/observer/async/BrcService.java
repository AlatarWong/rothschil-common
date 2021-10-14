package io.github.rothschil.design.observer.async;

import io.github.rothschil.design.observer.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import io.github.rothschil.common.utils.bean.SpringUtils;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/8 - 14:55
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
