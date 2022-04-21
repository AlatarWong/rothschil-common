package io.github.rothschil;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class BaseTest {

    long beginTime;
    long end;

    @BeforeEach
    public void beforeAll() {
        beginTime = System.currentTimeMillis();
    }

    @AfterEach
    public void after() {
        end = System.currentTimeMillis();
        log.info("[AfterAll] 总共耗时={} 毫秒",(end-beginTime));
    }


    @Test
    @DisplayName("测试方法一")
    public void test01() {
        System.out.println("@DisplayName: 为测试类或者测试方法设置展示名称");
    }


    @Test
    @DisplayName("测试方法二")
    @Timeout(value=1,unit= TimeUnit.MICROSECONDS)
    public void test02() {
        System.out.println("@Timeout: 表示测试方法运行如果超过了指定时间将会返回错误");
    }
}
