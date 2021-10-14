package io.github.rothschil.design.decorator.demo;

import lombok.extern.slf4j.Slf4j;

/** 装饰器
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 10:46
 * @since 1.0.0
 */
@Slf4j
public class ConcreteDecorator extends Decorator{

    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void say() {
        super.say();
        addFuntion();
    }

    public void addFuntion(){
        log.info("为构件提供额外的功能");
    }
}
