## 1. JUnit5 简介

`Spring Boot 2.2.0` 版本开始引入 `JUnit5` 作为单元测试默认库, `JUnit5` 作为最新版本的 `JUnit` 框架, 它与之前版本的 `JUnit` 框架有很大的不同,由三个不同子项目的几个不同模块组成。

    JUnit5 = JUnitPlatform + JUnitJupiter + JUnitVintage

- `JUnitPlatform`: `JUnitPlatform` 是在 `JVM` 上启动测试框架的基础,不仅支持 `JUnit` 自制的测试引擎,其它测试引擎也都可以接入.

- `JUnitJupiter`: `JUnitJupiter` 提供了 `JUnit5` 的新的编程模型,是 `JUnit5` 新特性的核心.内部包含了一个测试引擎,用于在 `JUnitPlatform` 上运行.

- `JUnitVintage`: 由于 `JUnit` 已经发展多年,为了照顾老的项目,`JUnitVintage` 提供了兼容 `JUnit 4.x`,`JUnit 3.x` 的测试引擎.

`SpringBoot 2.4` 以上版本移除了默认对 `Vintage` 的依赖.如果需要兼容 `JUnit4.x` 版本,需要自行引入（不能使用 `junit4` 的功能 @Test）

[详情见 JUnit5 官网](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)

## 2. 常用注解

| 注解  | 说明  |
| ---- | ---- |
|  @Test | 表示方法是测试方法,但是与 JUnit4 的 @Test 不同,它的职责非常单一不能声明任何属性,拓展的测试将会由 Jupiter 提供额外测试  |
| @DisplayName | 为测试类或者测试方法设置展示名称  |
| @BeforeAll |  表示在所有单元测试之前执行 |
| @BeforeEach | 表示在每个单元测试之前执行  |
| @Timeout |  表示测试方法运行如果超过了指定时间将会返回错误 |
| @Disabled |  表示测试类或测试方法不执行 |
| @RepeatedTest | 表示方法可重复执行  |
| @ExtendWith |  为测试类或测试方法提供扩展类引用 |
| @AfterEach | 表示在每个单元测试之后执行  |
| @AfterAll |  表示在所有单元测试之后执行 |
