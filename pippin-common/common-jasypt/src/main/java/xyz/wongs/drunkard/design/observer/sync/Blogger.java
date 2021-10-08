package xyz.wongs.drunkard.design.observer.sync;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.wongs.drunkard.design.observer.Article;

import java.util.Observable;

/** 被观察者，对观察者对象的引用进行抽象保存
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 13:58
 * @since 1.0.0
 */
@Slf4j
public class Blogger extends Observable {

    @Getter
    private String blogName;

    public Blogger(String blogName){
        this.blogName=blogName;
    }

    public void productArticle(Blogger blogger, Article article){
        setChanged();
        notifyObservers(article);
        log.error("博主 {} 发表主题为 {} 的微博",blogger.getBlogName(),article.getTitile());    }

}
