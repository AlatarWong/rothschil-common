package xyz.wongs.drunkard.design.observer.async;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wongs.drunkard.design.observer.Article;

import java.util.Observable;

/** 被观察者，对观察者对象的引用进行抽象保存
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 13:58
 * @since 1.0.0
 */
@Component
@Slf4j
public class BloggerService extends Observable {

    @Getter
    @Setter
    private String blogName;

    public BloggerService(){}

    public void productArticle(Article article){
        setChanged();
        notifyObservers(article);
        log.error("博主 {} 发表主题为 {} 的微博",blogName,article.getTitile());    }

}
