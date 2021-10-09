package xyz.wongs.drunkard.design;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wongs.drunkard.BaseAppTest;
import xyz.wongs.drunkard.design.observer.Article;
import xyz.wongs.drunkard.design.observer.async.BrcService;

@DisplayName("设计模式-Web")
@Slf4j
public class TestDesignByWeb extends BaseAppTest {

    @Autowired
    private BrcService brcService;

    @DisplayName("观察者模式-异步")
    @Test
    public void testObserver() {
        Article article1 = new Article("富豪","怎么成为富豪","");
        brcService.brcToBlog("Sam.Von.Abram",article1);
    }
}
