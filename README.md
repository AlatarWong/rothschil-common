## 1. 友情关联

本工程目前已经开源，源码目录分别在 `Gitee` 和 `Github` 中都有链接，希望对大家有帮助，最后别忘给 `Star`。

[Gitee链接：https://gitee.com/rothschil/rothschil-common](https://gitee.com/rothschil/rothschil-common)

[Github链接：https://github.com/rothschil/rothschil-common.git](https://github.com/rothschil/rothschil-common.git)

## 2. 目录结构




~~~
|---.gradle
|---Doc                 ------------------文档
|---gradle              ------------------Gradle配置文件
│  └─wrapper
│          gradle-wrapper.jar           --gradle-wrapper 主题功能
│          gradle-wrapper.properties    --Gradle当前配置的版本，以及从哪里获取
|---common-framework    ------------------常用框架模块
|---common-utils    ----------------------常用工具包，集成通用基类，供其他模块引入
|   │---build.gradle  --------------------工具包的构建文件
|   │---dependencies.gradle   ------------配置
|---common-persistence  ------------------持久化
|   │---persistence-jpa  -----------------工具包的构建文件
|   │---persistence-mybatis   ------------配置
│  .gitignore           ------------------配置git忽略索要文件
│  build.gradle         ------------------根目录的构建核心文件
│  gradle.properties    ------------------根目录的属性文件，这是默认命名
│  gradlew              
│  gradlew.bat          ------------------Gradle Wrapper
│  LICENSE              ------------------开源授权协议
│  README.md            ------------------项目描述
│  settings.gradle      ------------------Gradle模块 配置文件

~~~

## 3. 快速上手

