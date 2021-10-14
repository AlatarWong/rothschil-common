package io.github.rothschil.design;

import io.github.rothschil.design.adapter.StandardAdapter;
import io.github.rothschil.design.iterator.Aggregate;
import io.github.rothschil.design.iterator.ConcreteAggregate;
import io.github.rothschil.design.iterator.Iterator;
import io.github.rothschil.design.observer.Article;
import io.github.rothschil.design.observer.sync.Fans;
import io.github.rothschil.design.proxy.Access;
import io.github.rothschil.design.proxy.Accessible;
import io.github.rothschil.design.proxy.dynamic.CglibProxy;
import io.github.rothschil.design.proxy.stat.StaticProxy;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.github.rothschil.design.decorator.demo.Component;
import io.github.rothschil.design.decorator.demo.ConcreteComponent;
import io.github.rothschil.design.decorator.demo.ConcreteDecorator;
import io.github.rothschil.design.decorator.war3.Changer2Girl;
import io.github.rothschil.design.decorator.war3.Changer2Succubus;
import io.github.rothschil.design.decorator.war3.DmHunter;
import io.github.rothschil.design.decorator.war3.Original;
import io.github.rothschil.design.observer.sync.Blogger;

@DisplayName("设计模式")
@Slf4j
public class TestDesign {

    long beginTime;
    long end;

    @BeforeEach
    public void before(){
        beginTime = System.currentTimeMillis();
    }

    @AfterEach
    public void after(){
        end = System.currentTimeMillis();
    }

    @AfterEach
    public void afterAll(){
        log.info("[AfterAll] 总共耗时={} 毫秒",(end-beginTime));
    }

    @DisplayName("观察者模式")
    @Test
    public void testObserver() {
        Blogger blogger = new Blogger("Sam.Von.Abram");
        Fans fan1 = new Fans("张三");
        Fans fan2 = new Fans("李四");
        Fans fan3 = new Fans("朱元昌");
        blogger.addObserver(fan1);
        blogger.addObserver(fan2);
        blogger.addObserver(fan3);
        Article article1 = new Article("富豪","怎么成为富豪","");
        blogger.productArticle(blogger,article1);
    }

    @DisplayName("转换器模式")
    @Test
    public void testAdapter() {
        StandardAdapter adapter = new StandardAdapter();
        log.info("国标 {}",adapter.nationalStandard());
        log.warn("输出欧盟标准 {}",adapter.euStandards());
        log.warn("输出日本标准 {}",adapter.japaneseStandard());

    }

    @DisplayName("代理模式-静态代理")
    @Test
    public void testProxy2Static() {
        Access access = new StaticProxy(new Accessible());
        access.login();
    }

    @DisplayName("代理模式-动态代理-样例 1")
    @Test
    public void testProxy2DynamicDemo1() {
        CglibProxy proxy = new CglibProxy();
        Access access = (Access)proxy.getInstall(new Accessible());
        access.login();
    }

    @DisplayName("代理模式-动态代理-样例 2")
    @Test
    public void testProxy2DynamicDemo2() {
        CglibProxy proxy = new CglibProxy();
        // 自定义创建 Enhancer
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Accessible.class);
        enhancer.setCallback(proxy);
        Accessible access = (Accessible)enhancer.create();
        access.login();
    }

    @DisplayName("迭代器模式")
    @Test
    public void testIterator() {
        Aggregate ag = new ConcreteAggregate();
        ag.add("Java");
        ag.add("Python");
        ag.add("C++");
        ag.add("Visul Basic");
        Iterator it = ag.getIterator();
        while (it.hasNext()) {
            Object ob = it.next();
            log.warn(ob.toString() + "\t");
        }
        Object ob = it.first();
        log.warn("First：" + ob.toString());
    }

    @DisplayName("装饰器模式")
    @Test
    public void testDecorator() {
        Component cp = new ConcreteComponent();
        cp.say();
        log.warn("=====================");
        Component cpt = new ConcreteDecorator(cp);
        cpt.say();
    }

    @DisplayName("装饰器模式应用")
    @Test
    public void testDecoratorApp() {
        DmHunter hunter = new Original();
        hunter.display();

        DmHunter succubus = new Changer2Succubus(hunter);
        succubus.display();

        DmHunter girl = new Changer2Girl(hunter);
        girl.display();
    }
}
