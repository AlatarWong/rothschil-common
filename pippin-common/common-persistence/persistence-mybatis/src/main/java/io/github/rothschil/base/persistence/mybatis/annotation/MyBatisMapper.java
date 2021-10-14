package io.github.rothschil.base.persistence.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 标识MyBatis的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * 结合Springboot 整个注解暂时用不上 推荐使用 @MapperScan(basePackages = {"xyz.wongs.drunkard.**.mapper"}) 这样完成mapper的扫描
 *
 * @author WCNGS@QQ.COM
 * @since 2013-8-28
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisMapper {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

}