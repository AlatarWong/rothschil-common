package xyz.wongs.drunkard.design.observer.sync;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.wongs.drunkard.design.observer.Article;

import java.util.Observable;
import java.util.Observer;

/** 观察者
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 14:07
 * @since 1.0.0
 */
@Slf4j
public class Fans implements Observer {

    @Getter
    private String name;

    @Override
    public void update(Observable o, Object arg) {
        Blogger blogger=(Blogger)o;
        Article article=(Article)arg;
        log.error("粉丝{}，看见{}的博主发表主题为{}的微博，内容为{}",name,blogger.getBlogName(),article.getTitile(),article.getContext());

    }

    public Fans(String name){
        this.name=name;
    }
}
