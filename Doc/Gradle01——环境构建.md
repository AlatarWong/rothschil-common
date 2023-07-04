
- [1. JDK、Maven、Nexus版本要求](#1-jdkmavennexus版本要求)
- [2. Gradle环境配置（Windows）](#2-gradle环境配置windows)
  - [2.1. gradle.properties](#21-gradleproperties)
  - [2.2. init.gradle](#22-initgradle)
- [3. IDEA工具集成](#3-idea工具集成)
  - [3.1. IDEA配置Gradle](#31-idea配置gradle)
  - [3.2. 创建和认识Java工程项目](#32-创建和认识java工程项目)
- [4. Gradle Wrapper](#4-gradle-wrapper)
- [5. 代码分析器](#5-代码分析器)


## 1. JDK、Maven、Nexus版本要求

本工程运行环境对应的版本 `JDK`版本在 `1.8`、`Maven`版本 `3.6`、`Nexus 3.34+ ` 三者的安装不在本工程内容中，自行百度脑补。

当前 `Gradle` 需要 8 到 16 之间的 `Java` 版本。尚不支持 `Java 17` 及更高版本，下图为 `Gradle` 兼容性表格。

| Java version | 对应 Gradle 版本 |
| --- | --- |
|	8	|	2	|
|	9	|	4.3	|
|	10	|	4.7	|
|	11	|	5	|
|	12	|	5.4	|
|	13	|	6	|
|	14	|	6.3	|
|	15	|	6.7	|
|	16	|	7	|


## 2. Gradle环境配置（Windows）

![官网](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210907173500.png)

- `Windows`下配置 `Gradle`环境配置，需要先从  [官网](https://gradle.org/releases/)  下载最新的官方发行版
- 将下载的压缩文件解压到一个路径下，例如：`D:\ProgramFiles\Gradle\gradle-7.2`
- 配置环境变量 `GRADLE_HOME` ，值为 `D:\ProgramFiles\Gradle\gradle-7.2`
- 将  `GRADLE_HOME` 添加到 path
- 再添加环境变量 `GRADLE_USER_HOME` ，这个是用户空间，指向磁盘中一个文件夹即可，例如：`E:\Repertory\RepositoryGradle\.gradle` 这个很重要
- `GRADLE_USER_HOME` 目录下我们新建两个文件，`gradle.properties`、`init.gradle`

`Gradle` 附带自己的 `Groovy` 库，因此不需要安装 `Groovy`，同时 `Gradle` 会忽略任何现有的 `Groovy` 安装。

### 2.1. gradle.properties

`Gradle` 配置项，可以控制用于执行构建的 Java 进程，如果在多个位置配置了一个选项，则以在这些位置中的任何一个中找到的第一个配置项为准：

- 命令行，使用 -P / --project-prop 环境选项设置
- `GRADLE_USER_HOME` 目录中的 `gradle.properties`
- 项目根目录下的 `gradle.properties`
- `Gradle` 安装目录中的 `gradle.properties`

`Gradle` 配置项主要有以下属性可用于配置

    org.gradle.caching=(true,false)
    当设置为 true 时，Gradle 将在可能的情况下重用任何先前构建的任务输出，从而使构建速度更快。了解有关使用构建缓存的更多信息。

    org.gradle.caching.debug=(true,false)
    设置为 true 时，每个任务的单个输入属性哈希值和构建缓存键都会记录在控制台上。了解有关任务输出缓存的更多信息

    org.gradle.parallel=(true,false)
    配置后，Gradle 将分叉到 org.gradle.workers.max JVM 以并行执行项目。要了解有关并行任务执行的更多信息，请参阅 Gradle 构建性能部分

    org.gradle.daemon=(true,false)
    当设置为 true 时，Gradle 守护程序用于运行构建。3.0开始默认开启。

当然 `Gradle` 配置项 还有其他属性可供选择，具体，比如日志、优先权、警告模式等。 此处给除一个常见的配置项，具体如下：

~~~properties
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
~~~

### 2.2. init.gradle

`Gradle` 全局配置，主要定义定义仓库

~~~gradle

allprojects{
   
    repositories {
   
        def ALIYUN_REPOSITORY_URL = 'http://maven.aliyun.com/nexus/content/groups/public'
        def ALIYUN_JCENTER_URL = 'http://maven.aliyun.com/nexus/content/repositories/jcenter'
        all {
            ArtifactRepository repo ->
            if(repo instanceof MavenArtifactRepository){
   
                def url = repo.url.toString()
                if (url.startsWith('https://repo1.maven.org/maven2')) {
   
                    project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_REPOSITORY_URL."
                    remove repo
                }
                if (url.startsWith('https://jcenter.bintray.com/')) {
   
                    project.logger.lifecycle "Repository ${repo.url} replaced by $ALIYUN_JCENTER_URL."
                    remove repo
                }
            }
        }
        maven {
            allowInsecureProtocol = true
            url ALIYUN_REPOSITORY_URL
            url ALIYUN_JCENTER_URL
        }
    }
}
~~~

![演示](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210907173930.png)

## 3. IDEA工具集成

演示所使用的IDEA版本是 `2021.1.2`。

![20210907170523](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210907170523.png)

### 3.1. IDEA配置Gradle

`IDEA` 集成 `Gradle`需要安装插件，否则无法使用，插件不论你在线安装还是离线安装都可以。

![20210907165919](https://abram.oss-cn-shanghai.aliyuncs.com/blog/sctel/20210907165919.png)

![20210907174102](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210907174102.png)

 `IDEA `中的 `Gradle user home` 后的选项内容将是配置的 `GRADLE_USER_HOME` 环境变量。 这个点非常会让人忽视的。

### 3.2. 创建和认识Java工程项目

![New Project_1](https://abram.oss-cn-shanghai.aliyuncs.com/blog/sctel/20210907165803.png)

![New Project_2](https://abram.oss-cn-shanghai.aliyuncs.com/blog/sctel/20210907165727.png)

- Name：项目名称
- Location：所在的路径
- GroupId、ArtifactId、Version

这些都与`Maven`雷同。

![New Project_3](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210907172055.png)

运行 `gradle build` 的输出结果

~~~cmd
17:21:31: Executing task 'build'...

> Task :compileJava NO-SOURCE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE
> Task :jar UP-TO-DATE
> Task :assemble UP-TO-DATE
> Task :compileTestJava NO-SOURCE
> Task :processTestResources NO-SOURCE
> Task :testClasses UP-TO-DATE
> Task :test NO-SOURCE
> Task :check UP-TO-DATE
> Task :build UP-TO-DATE

BUILD SUCCESSFUL in 131ms
1 actionable task: 1 up-to-date
17:21:31: Task execution finished 'build'.
~~~

![基本常用任务](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210907172205.png)

上图中罗列的基本任务，可以先认识下，下来重点描述下这两个文件 `build.gradle`、`setting.gradle`，这是我们`Gradle`工程开发最核心的两个文件。

- build.gradle

这个文件非常重要， `Gradle` 的核心配置文件，相当于 `Maven` 中的 `POM` ，此后工程开发都围绕着这个文件来编写。

~~~gradle
plugins { //插件
    id 'java'
}

group 'xyz.wongs.gradle'    // group 分组
version '1.0.0-SNAPSHOT'    // 版本

repositories {  // 仓库
    mavenCentral()
}

dependencies {  // 依赖
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
}

test {  // 测试
    useJUnitPlatform()
}
~~~

- setting.gradle

这是针对模块的引入配置文件，可以在此管理每个模块激活状态，一般在添加新模块的过程中使用。

~~~gradle
rootProject.name = 'demo'
~~~

## 4. Gradle Wrapper

在 `Maven`中，开发者和项目服务器都需要配置有相同的 `Maven`版本，才能保证应用正常编译执行，`Gradle`提供一个 `Gradle Wrapper`概念，可以项目中自带Gradle的处理环境，`Gradle Wrapper`简化 `Gradle`本身的安装、部署，避免 `Gradle`版本不通带来的各种问题。

在任意目录中 执行 `gradle wrapper`

![20210908092359](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908092359.png)

切换版本

| 操作 | 描述 |
|--|--|
| 切换 6.8 版本 | gradle wrapper --gradle-version 6.8 |
| 切换 6.5 版本 | gradle wrapper --gradle-version 6.5 |

![20210908093052](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908093052.png)

执行上述命令， `gradle-wrapper.properties` 中 `distributionUrl` 后的 URL 变为 `https\://services.gradle.org/distributions/gradle-6.8-bin.zip` ，版本切换成功。

![20210908093313](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908093313.png)

## 5. 代码分析器

所有开发人员编写的代码，在程序上线之前都一定要进行性能的检测，而对于 `Gradle` 项目来讲，也需要清楚的知道，项目代码构建的情况，包括构建环境、依赖库以及程序性能上存在的问题，这样方便与人沟通。为了解决这样的构建问题，所以在 `Gradle` 中就提供了一个 **Build Scan** 代码扫描工具（这是一个在 4.3 版本才开始提供）， 这个工具会自动的将本地构建的过程发送到指定服务器端，并且由服务器端上随机生成的可供访问的链接，通过链接进行注册，即可完成访问，这个分析器是 `gradle wrapper` 提供。

~~~cmd
gradlew build --scan
~~~

![20210908093948](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908093948.png)

输入邮箱，在邮箱中确认

![首页](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908094121.png)

![邮箱确认](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908094306.png)

![报告内容](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908094417.png)