package xyz.wongs.drunkard.design.decorator.demo;

import lombok.extern.slf4j.Slf4j;

/** 为构件提供具体的实现
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 10:29
 * @since 1.0.0
 */
@Slf4j
public class ConcreteComponent implements Component{


    public ConcreteComponent() {
        log.info("[创建构建的具体实现类]");
    }

    @Override
    public void say() {
        log.info("[具体实现]");
    }
}
