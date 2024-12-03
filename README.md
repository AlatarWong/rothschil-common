![rothschil-common](https://socialify.git.ci/AlatarWong/rothschil-common/image?description=1&descriptionEditable=The%20darkness%20will%20not%20last%20forever%2C%20but%20you%20will%20forever%20in%20my%20heart&forks=1&issues=1&language=1&name=1&owner=1&pattern=Signal&pulls=1&stargazers=1&theme=Light)

## 1. 友情关联

本工程目前已经开源，源码目录分别在 `Gitee` 和 `Github` 中都有链接，希望对大家有帮助，最后别忘给 `Star`。

[Gitee链接：https://gitee.com/rothschil/rothschil-common](https://gitee.com/rothschil/rothschil-common)

[Github链接：https://github.com/rothschil/rothschil-common](https://github.com/rothschil/rothschil-common)

## 2. 目录结构

~~~
|---.gradle
|---Doc                 ------------------文档
|---gradle              ------------------Gradle配置文件
│  └─wrapper
│          gradle-wrapper.jar           --gradle-wrapper 主题功能
│          gradle-wrapper.properties    --Gradle当前配置的版本，以及从哪里获取
|---src    ------------------常用框架模块
│  .gitignore           ------------------配置git忽略索要文件
│  build.gradle         ------------------根目录的构建核心文件
│  gradle.properties    ------------------根目录的属性文件，这是默认命名
│  gradlew              
│  gradlew.bat          ------------------Gradle Wrapper
│  LICENSE              ------------------开源授权协议
│  README.md            ------------------项目描述
│  settings.gradle      ------------------Gradle模块 配置文件

~~~

## 3. 总体说明

本组件提供 `Mybatis` 、 `JPA` 、 `Elasticsearch` 和 `Redis` 四大类核心的服务，简化操作逻辑，这些模块都已经在中央仓库可以下载。

- `Maven` 格式

~~~xml

<dependency>
    <groupId>io.github.rothschil</groupId>
    <artifactId>persistence-{modleName}</artifactId>
    <version>${lastVersion}</version>
</dependency>

~~~

- `Gradle` 格式

~~~groovy

api('io.github.rothschil:persistence-{modleName}:${lastVersion}')

~~~

`modleName` 替换为自己想要使用的模块即可， `lastVersion` 为版本号。

对外提供常用功能如下：

- 这是 `Gradle` 项目结构的工程，提供常用的 Gradle 任务的编写和模块化构建样例，为从 `Maven` 转向 `Gradle` 提供学习和参考。
- 统一对返回数据进行标准化封装。
- 统一对异常进行个性化处理。
- 对 `Controller` 的访问进行 `AOP` 处理，支持异步写入消息和数据库，其中写入逻辑需要继承 `AbstactAppLogService` 完成实现。
- `MyBatis` 模块提供了对 `XML` 的动态加载，支持线上修改 `XML` 的逻辑，减少版本重启的耗时。
- 封装 `Elasticsearch` 对数据管理功能和索引的基本管理，提供数据查询分页。
-  `Redis`  提供 分布式锁、队列等常用操作。

## 4. 快速上手

### 4.1. persistence-mybatis

这是Mybatis基本封装，需要在 `Application` 注解类中添加 `mapper` 的路径，按照通配符匹配。

#### 4.1.1. Application类

~~~java

@MapperScan(basePackages = {"io.github.rothschil.**.mapper"})
@SpringBootApplication
public class MoonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonApplication.class,args);
    }
}

~~~

#### 4.1.2. application.yml配置

这是 `SpringBoot` 核心配置项，当然 `application` 的格式有很多种，这里只是用 `yml` 来举例，即写入自己 `Mybatis XML` 文件的 `classpath` 路径，其他的 `application` 格式以及数据源的配置，在此就不再赘述。

~~~yml

mybatis:
  mapperLocations: classpath:mapper/**/*.xml

~~~

#### 4.1.3. 实体Bean

`Bean` 定义我们只需要继承 `io.github.rothschil.common.po.BasePo`， 为我们将来使用框架提供了方便，关于是否是用 `lombok` 来对代码进行简化，这块不强制要求。

~~~java

import io.github.rothschil.common.po.BasePo;
import lombok.*;

@EqualsAndHashCode(callSuper=false)
@Builder(toBuilder=true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo extends BasePo<Long> {
    private Long id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String suffixName;
    private String md5;
}

~~~

#### 4.1.4. 持久层

定义接口，需要继承 `BaseMapper` ，这为我们封装更多的实体操作。

~~~java

import io.github.rothschil.base.persistence.mybatis.mapper.BaseMapper;
import io.github.rothschil.domain.entity.FileInfo;

public interface FileInfoMapper extends BaseMapper<FileInfo,Long> {

    void batchInsert(List<FileInfo> lists);

}

~~~

#### 4.1.5. mapper文件

~~~xml

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="io.github.rothschil.domain.mapper.FileInfoMapper" >
  <resultMap id="BaseResultMap" type="io.github.rothschil.domain.entity.FileInfo" >
    <id column="id" property="id" jdbcType="BIGINT" />
    <result column="file_name" property="fileName" jdbcType="VARCHAR" />
    <result column="file_path" property="filePath" jdbcType="VARCHAR" />
    <result column="file_size" property="fileSize" jdbcType="BIGINT" />
    <result column="suffix_name" property="suffixName" jdbcType="VARCHAR" />
    <result column="md5" property="md5" jdbcType="VARCHAR" />
  </resultMap>
  
  <sql id="Base_Column_List" >
    id, file_name, file_path, file_size, suffix_name, md5
  </sql>

  <insert id="batchInsert" parameterType="java.util.List">
      <!--演示用-->
  </insert>

  <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long" >
    <!--演示用-->
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Long" >
    <!--演示用-->
  </delete>
  <insert id="insert" parameterType="io.github.rothschil.domain.entity.FileInfo" >
    <!--演示用-->
  </insert>
  <insert id="insertSelective" parameterType="io.github.rothschil.domain.entity.FileInfo" >
    <!--演示用-->    
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="io.github.rothschil.domain.entity.FileInfo" >
    <!--演示用-->
  </update>
  <update id="updateByPrimaryKey" parameterType="io.github.rothschil.domain.entity.FileInfo" >
    <!--演示用-->
  </update>
</mapper>

~~~

#### 4.1.6. 业务层

~~~java

import io.github.rothschil.base.persistence.mybatis.service.BaseService;
import io.github.rothschil.domain.entity.FileInfo;
import io.github.rothschil.domain.mapper.FileInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**

 * @author <a>https://github.com/rothschil</a>
 * @date 2020/9/9 16:11
 * @since  1.0.0
*/
@Slf4j
@Service(value="fileInfoService")
@Transactional(readOnly = true)
public class FileInfoService extends BaseService<FileInfoMapper,FileInfo, Long> {

	@Transactional(readOnly = false)
	public void insert(List<FileInfo> lists){
        baseMpper.batchInsert(lists);
	}
}

~~~~

### 4.2. persistence-jpa

待补充