package xyz.wongs.drunkard.design.decorator.demo;

import lombok.extern.slf4j.Slf4j;

/**抽象装饰角色
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 10:31
 * @since 1.0.0
 */
@Slf4j
public class Decorator implements Component{

    private Component component;

    public Decorator() {

    }

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void say() {
        log.warn("装饰器，开始调用具体组件模块");
        component.say();
    }
}
