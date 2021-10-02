//package xyz.wongs;
//
//
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.concurrent.TimeUnit;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public abstract class BaseTest {
//
//    @Test
//    @BeforeAll
//    public static void beforeAll() {
//        System.out.println("@BeforeAll: 表示在所有单元测试之前执行");
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        System.out.println("@BeforeEach: 表示在每个单元测试之前执行");
//    }
//
//
//    @DisplayName("测试方法一")
//    @Test
//    public void test01() {
//        System.out.println("@DisplayName: 为测试类或者测试方法设置展示名称");
//    }
//
//    @Test
//    @DisplayName("测试方法二")
//    @Timeout(value=1,unit= TimeUnit.MICROSECONDS)
//    public void test02() {
//        System.out.println("@Timeout: 表示测试方法运行如果超过了指定时间将会返回错误");
//    }
//}
