<!-- TOC -->

- [1. 概述](#1-概述)
- [2. 友情关联](#2-友情关联)
- [3. 目录结构](#3-目录结构)
- [4. Maven与Gradle比较](#4-maven与gradle比较)
- [5. 环境构建](#5-环境构建)
- [6. Groovy](#6-groovy)
- [7. Gradle基础](#7-gradle基础)
  - [7.1. 任务创建](#71-任务创建)
  - [7.2. 执行任务](#72-执行任务)
    - [7.2.1. 追加任务动作](#721-追加任务动作)
    - [7.2.2. 执行方法](#722-执行方法)
  - [7.3. 属性](#73-属性)
  - [7.4. 继承](#74-继承)
  - [7.5. 任务依赖](#75-任务依赖)
  - [7.6. 文件处理](#76-文件处理)
    - [7.6.1. 本地文件](#761-本地文件)
    - [7.6.2. 文件集合](#762-文件集合)
    - [7.6.3. 文件树](#763-文件树)
    - [7.6.4. 文件拷贝](#764-文件拷贝)
  - [7.7. 依赖库范围](#77-依赖库范围)
  - [7.8. 日志处理](#78-日志处理)
  - [7.9. 常用命令](#79-常用命令)
- [8. Gradle进阶](#8-gradle进阶)
  - [8.1. 构建JAVA项目](#81-构建java项目)
  - [8.2. 多任务并行](#82-多任务并行)
    - [创建自定义任务类](#创建自定义任务类)
  - [8.3. 生命周期](#83-生命周期)
    - [8.3.1. 构建阶段](#831-构建阶段)
    - [8.3.2. 配置文件](#832-配置文件)
    - [8.3.3. 初始化](#833-初始化)
    - [8.3.4. 构建生命周期](#834-构建生命周期)
  - [8.4. 多环境](#84-多环境)
  - [8.5. 插件](#85-插件)
    - [8.5.1. 二进制插件](#851-二进制插件)
    - [8.5.2. 自定义插件](#852-自定义插件)
    - [8.5.3. 脚本插件](#853-脚本插件)
    - [8.5.4. 插件社区](#854-插件社区)
    - [8.5.5. 项目打包](#855-项目打包)
  - [8.6. 项目发布](#86-项目发布)

<!-- /TOC -->


![gradle](https://abram.oss-cn-shanghai.aliyuncs.com/blog/sctel/gradle.png)

## 1. 概述

`Pippin`, was a `Hobbit` of the Shire, and one of `Frodo Baggins'` youngest, but closest friends. He was a member of the Fellowship of the Ring and later became the thirty-second Thain of the Shire。 

`Gradle` 是一种开源构建自动化工具，其设计足够灵活，几乎可以构建任何类型的软件，是一个基于`Apache Ant`和 `Apache Maven`概念的项目自动化构建工具，它使用 `Groovy` 语言来声明项目设置，抛弃了 `XML` 的各类繁琐配置。以面向 `Java` 应用为主，当前支持的语言有除 `Java` 外，还有 `Groovy`、`Kotlin`、`Scala`、`C++`、`Swift`、`JavaScript`，计划未来支持更多的语言。

`Gradle` 具有以下特性：

- 高性能：`Gradle` 通过仅运行需要运行的任务来避免不必要的工作，因为它们的输入或输出已更改，还可以使用构建缓存来重用先前运行或来自不同机器（具有共享构建缓存）的任务输出。
- JVM基础：`Gradle` 在 `JVM` 上运行，必须安装 `Java Development Kit (JDK)` 才能使用它，这对于熟悉 `Java` 平台的用户来说是一个好处，可以在构建逻辑中使用标准的 `Java API`，例如自定义任务类型和插件，它还可以轻松地在不同平台上运行 `Gradle` 。
- 约定： `Gradle` 借鉴 `Maven` ，通过实现约定使常见类型的项目易于构建，应用适当的插件可以轻松地为许多项目创建精简的构建脚本，但是这些约定并不会限制，你可以覆盖它们，添加自己的任务，进行其他自定义行为。
- 可扩展性：可以扩展 `Gradle` 以提供您自己的任务类型甚至构建模型。
- IDE支持：支持主流 IDE 构建并与之交互，如 `Android Studio`、`IntelliJ IDEA`、 `Eclipse`、 `NetBeans`，`Gradle` 还支持生成将项目加载到 `Visual Studio` 所需的解决方案。
- 可分析：可在构建扫描过程中提供有关构建运行的大量信息，基于这些信息来识别构建性能问题，还可以与他人共享构建扫描，这在解决构建问题时特别有用。

 本工程主要是针对 `Gradle`的学习入门类项目，也是借助于托尔金指环王的人物命名的工程，在学习本工程之前，对学习者有如下要求：

- 面向`Java`语言的项目开发者，至少一年半以上开发类的经验，如果会 `Python` 更好， `Groovy`语言很多都是参考 `Python`。
- 掌握 `Maven`在项目开发中的基本运用，具体如下：
    - 熟练掌握 `Nexus` 私服，创建和维护私服
    - 掌握 `Maven`中环境构建、依赖管理、编译、打包、部署，并且可以基本运用
    - 了解 `Maven`中对插件的运用，如`maven-compiler-plugin`、`maven-resources-plugin`等
    - 了解 `Maven`中各类仓库的概念，中央仓库、本地仓库
- 了解项目中常用的版本命名规则

如果不满足上述要求，建议先去补充 `Maven`有关的知识，因为 `Gradle`和 `Maven`还是有很多类似的地方。

通过本工程的学习，会掌握 `Gradle`常见用法以及利用 `Groovy`编写任务来完成自身项目开发需要，当然本工程最后一段项目案例以集成 `SpringBoot` 完成项目模块的构建，也可以将此用来当作现实中用来改造既有项目为 `Gradle`项目的模板来使用。

## 2. 友情关联

本工程目前已经开源，源码目录分别在 `Gitee` 和 `Github` 中都有链接，希望对大家有帮助，最后别忘给 `Star`。

[Gitee链接：https://gitee.com/rothschil/pippin](https://gitee.com/rothschil/pippin)

[Github链接：https://github.com/rothschil/pippin.git](https://github.com/rothschil/pippin.git)

## 3. 目录结构

~~~
|---.gradle
|---Doc                 ------------------文档
|---gradle              ------------------Gradle配置文件
│  └─wrapper
│          gradle-wrapper.jar           --gradle-wrapper 主题功能
│          gradle-wrapper.properties    --Gradle当前配置的版本，以及从哪里获取
|---groovy-demo         ------------------Groovy基本语法
|   \---src
|       +---gvy01       ------------------
|       +---gvy03       ------------------
|---pippin-common       ------------------通用模块部分
|   \---common-utils    ------------------常用工具包，集成通用基类，供其他模块引入
│      │  build.gradle  ------------------工具包的构建文件
│      │  dependencies.gradle   ----------配置
|---pippin-service      ------------------样例服务
|   \---service-jwt     ------------------JWT服务示例
|       └─src
|   |       ├─main
|   |       │  └─resources
|   |       │     ├─application.yml
|   |       │       ├─config.properties
|   |       │  ├─config
|   |       │  │  ├─dev
|   |       │  │  │ ├─application.prperties ----开发环境配置
|   |       │  │  │      
|   |       │  │  └─prod
|   |       │  │    ├─application.prperties ----生产环境配置
|   |   │  build.gradle     ------------------构建文件
|   │   |  dependencies-dev.gradle  ----------开发环境配置
|   │   |  dependencies-prod.gradle ----------生产环境配置
|   │   |  dependencies-test.gradle ----------测试环境配置
│  .gitignore           ------------------配置git忽略索要文件
│  build.gradle         ------------------根目录的构建核心文件
│  gradle.properties    ------------------根目录的属性文件，这是默认命名
│  gradlew              
│  gradlew.bat          ------------------Gradle Wrapper
│  LICENSE              ------------------开源授权协议
│  README.md            ------------------项目描述
│  settings.gradle      ------------------Gradle模块 配置文件

~~~

## 4. Maven与Gradle比较

- 灵活性：二者都提供约定优于配置。但 `Maven` 显得非常僵化，没法定制，一切都是 `Maven`给定的，不适用于许多自动化问题。另一方面，`Gradle` 是在考虑到授权和负责任的用户的情况下构建的。
- 性能： `Gradle` 特有的增量机制，所以相对来说缩短构建所需的时间
- 用户体验：`Gradle` 提供了一个基于 `Web` 的交互式 `UI` 用于调试和优化构建：构建扫描。这些也可以在本地托管，以允许组织收集构建历史并进行趋势分析、比较构建以进行调试或优化构建时间。
- 依赖管理：`Gradle` 允许生产者声明 `api` 和 `implementation` 依赖项，以防止不需要的库泄漏到使用者的类路径中。`Maven` 允许发布者通过可选的依赖项提供元数据。

具体在性能上，`Gradle` 具备三个明显特征：

- 增量性：`Gradle` 通过跟踪任务的输入和输出并只运行必要的内容来避免工作，并且只在可能的情况下处理更改的文件。
- 构建缓存：使用相同的输入重用任何其他 `Gradle` 构建的构建输出，包括在机器之间。
- `Gradle` 守护进程：一个长期存在的进程，它使构建信息在内存中保持“热”状态

![Gradle与Maven性能比较](https://abram.oss-cn-shanghai.aliyuncs.com/blog/sctel/20210907161922.png)

综述，`Maven` 更加标准，`Gradle` 更加简洁灵活。

## 5. 环境构建

篇幅有限，请移步另一个文件 [环境构建快速入门教程](Doc/Build_Env.md)

## 6. Groovy

篇幅有限，请移步另一个文件 [Groovy快速入门教程](Doc/Groovy.md)

`Apache` 的`Groovy`是`Java`平台上设计的面向对象编程语言。运行于`Java`虚拟机上的`Java`字节码，并与其他`Java`代码和库进行互操作。由于其运行在`JVM`上的特性，`Groovy`可以使用其他`Java`语言编写的库。`Groovy`的语法与`Java`非常相似，大多数`Java`代码也符合`Groovy`的语法规则。

![20210908095332](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908095332.png)

## 7. Gradle基础

通过上面对`Groovy`语法的介绍，这一章节，通过以 `Groovy` 来编写 `Gradle` 任务。

### 7.1. 任务创建

`Gradle` 中任务创建有五种方式，日常使用中比较多的是采用闭包的方式。

~~~gradle
// 1、原型创建
def task1 = task(task1)
task1.doLast {
    println '【1、原型创建】最后执行'
}

// 2、任务属性创建
def task2 = task([description: ' Is My Task2', group: 'xyz.wongs'],task2)
task2.doLast {
    println '【2、任务属性创建】最后执行'
}

// 3、闭包机制
task task3 {
    description(' Is My Task3')
    group('xyz.wongs')
    doLast {
        println '【3、闭包机制】最后执行'
    }
}

// 4、任务容器 TaskContainer
tasks.create('task4') {
    description(' Is My Task4')
    group('xyz.wongs')

    doLast {
        println '【4、任务容器 TaskContainer】最后执行'
    }
}

// 5、任务容器 TaskContainer
tasks.register('task5') {
    description(' Is My Task5')
    group('xyz.wongs')

    doLast {
        println '【4、任务容器 TaskContainer】register 最后执行'
    }
}
~~~

### 7.2. 执行任务

#### 7.2.1. 追加任务动作

不论在开头或结尾添加一个操作，任务执行时，按顺序执行动作列表中的动作。

~~~gradle
task taskAaddAction {
    description('Adding behaviour Demo')
    group('xyz.wongs.gradle')
    doLast {
        println '【1、Adding behaviour】属性设置'
    }
}

tasks.named('taskAaddAction') {
    doFirst {
        println '【1、Adding behaviour】 doFirst 追加的动作'
    }
}

tasks.named('taskAaddAction') {
    doLast {
        println '【1、Adding behaviour】 doLast 追加的动作'
    }
}
~~~

![20210914094554](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210914094554.png)

#### 7.2.2. 执行方法

task funExcu {

    group('xyz.wongs.gradle')
    doLast {
        println '【1、Adding behaviour】属性设置'
    }
}

### 7.3. 属性

属性是`Task`在定义中说明的，在`Task`类中，我们可以看到提供这些属性，都可以通过外部访问。

![20210909160048](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909160048.png)

![20210909155934](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909155934.png)

~~~gradle
task taskPro {
    description('Properties Demo')
    group('xyz.wongs.gradle')
    doLast {
        println '【1、Propertie】属性设置'
    }
}
~~~


### 7.4. 继承

整个 `Gradle`中的任务都可以被继承，只需要定义一个父类的任务，在其中编写自己的业务，同时再继承  `DefaultTask` 即可。

~~~gradle
// 二、继承
// 定义Java程序类
class CustomerTask extends DefaultTask {

    // TaskAction 任务执行注解
    @TaskAction
    def doSelf() {
        // 实现的主体
        println '【3、Extends-doSelf】 Operation Body'
    }

    @TaskAction
    def doLast() {
        println '【3、Extends-doLast】 doLast Operation'
    }
}

task taskExtends(type: CustomerTask) {
    description('Extends Demo')
    doFirst {
        println '【3、Extends】继承'
    }

    doLast {
        println '【3、Extends】继承'
    }
}
~~~

![20210909161301](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909161301.png)

### 7.5. 任务依赖

任务之间的依赖关系，为了方便控制任务执行过程中的优先级，如同我们 `Maven` 中，在运行 `jar`任务之前，`complie`任务一定要执行过，也就是 `jar` 依赖于`compile`，在 `Gradle` 中，通过 `DependsOn`控制依赖关系，`DependsOn` 是 `Task` 类的一个方法，可以接受多个依赖的任务作为参数

![20210909161417](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909161417.png)

~~~gradle

// 任务依赖

task taskDependsOnC() {
    group("xyz.wongs.gradle")
    doFirst {
        println '【5、DependsOnC】 doFirst'
    }
}

task taskDependsOnD() {
    // 任务禁用
    enabled = true
    group("xyz.wongs.gradle")
    doFirst {
        println '【5、DependsOnD】 doFirst'
    }
}

task taskDependsOnB(dependsOn: [taskDependsOnD, taskDependsOnC]) {
    group("xyz.wongs.gradle")
    doFirst {
        println '【5、DependsOnB】 doFirst'
    }
}

task taskDependsOnA(dependsOn: [taskDependsOnB]) {
    group("xyz.wongs.gradle")
    doFirst {
        println '【5、DependsOnA】 doFirst'
    }
}
~~~

### 7.6. 文件处理

编写多任务处理过程中需要用到对各类资源文件的控制，涉及到 `Gradle`对文件操作，常用的对文件操作主要有：

- 本地文件：指定文件的相对路径或绝对路径来对文件的操作
- 文件集合：对一组文件的列表进行操作，但文件集合中的文件对象是延迟，任务调用才会创建
- 文件树：有层级结构的文件集合，一个文件树它可以代表一个目录结构或一 `ZIP` 压缩包中的内容结构。文件树是从文件集合继承过来的，所以文件树具有文件集合所有的功能。
- 文件拷贝：可以使用 `Copy`任务来拷贝文件，通过它可以过虑指定拷贝内容，还能对文件进行重命名操作等。Copy任务必须指定一组需要拷贝的文件和拷贝到的目录
- 归档文件：通常一个项目会有很多的 `Jar` 包，将项目打包成一个 `WAR`、`ZIP`、`TAR` 包进行发布，可以使用 `Zip`，`Tar`，`Jar`，`War`和 `Ear` 任务来实现

#### 7.6.1. 本地文件

~~~gradle
// 1、本地文件
def Task taskLocalFile = task(taskLocalFile) {
    doFirst {
        // 使用相对路径
        File configFile = file('src/main/resources/config.xml')
        configFile.createNewFile();

        def _path = 'D:/Code/98_Github/Gradle/grad/src/main/resources/config.xml'
        // 使用绝对路径
        configFile = file(_path)
        println('【1、本地文件  使用绝对路径】' + configFile.absolutePath)

        // 使用一个文件对象
        configFile = file(new File(_path))
        println('【1、本地文件  使用文件对象】' + configFile.exists())
    }
}
~~~

#### 7.6.2. 文件集合

~~~gradle
// 2、文件集合
def Task taskCollectionFile = task(taskCollectionFile) {
    doFirst {
        File _fileList = file('gvoovy-demo/src/gvy01')
        def FileCollection collection = files(_fileList.listFiles())    // 获取文件列表
        collection.each { println(it) }   // 遍历文件列表
        Set _set1 = collection.files        // 文件集合转为 Set
        Set _set2 = collection as Set
        List _list = collection as List     // 文件集合转为 List
        String _path = collection.asPath    // 文件集合转为 String
    }
}
~~~

#### 7.6.3. 文件树

~~~gradle
// 3、文件树
def Task taskFileTree = task(taskFileTree) {
    doFirst {
        FileTree _fileTree = fileTree('gvoovy-demo/src/gvy01')  // 1、指定目录创建文件树对象
        _fileTree.include '**/*.groovy'     // 添加包含指定文件
        _fileTree.exclude '**/del*'         // 添加排除指定文件
        // 2、使用路径创建文件树对象，同时指定包含的文件
        _fileTree = fileTree('gvy/src').include('**/*.groovy')

        _fileTree = fileTree('gvy/src') {   // 3、通过闭包创建文件树
            include '**/*.groovy'
        }

        _fileTree = fileTree(dir: 'gvoovy-demo/src/gvy01', include: '**/*.groovy')  // 通过map创建文件树
        _fileTree = fileTree(dir: 'gvoovy-demo/src/gvy01', includes: ['**/*.groovy', '**/*.xml'])
        _fileTree = fileTree(dir: 'gvoovy-demo/src/gvy01', include: '**/*.groovy', exclude: '**/*del*/**')

        _fileTree.each { File file ->        // 遍历文件树的所有文件
            println(file)
        }
    }
}
~~~

#### 7.6.4. 文件拷贝

~~~gradle
// 4、文件拷贝
task taskFileCopy(type: Copy) {
    from 'src/main/resources/config.log'
    // 从Zip压缩文件中拷贝内容
    from zipTree('src/main/resources/00.zip')
    into 'src/main/java/'
    // 添加过虑条件来指定包含或排除的文件
    include '**/*.png'
}
~~~

### 7.7. 依赖库范围

![20210909161903](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909161903.png)

### 7.8. 日志处理

项目开发过程中，所有重要数据内容都需要通过日志的形式输出，Gradle内部提供各种日志组件。

![20210909162056](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909162056.png)

### 7.9. 常用命令

![20210909162804](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909162804.png)

## 8. Gradle进阶

### 8.1. 构建JAVA项目


### 8.2. 多任务并行

`Gradle`  `Worker API` 提供将任务的操作分解为相互独立的工作单元，通过并发和异步执行来提供执行能力。

这里通过一个例子来说，这个例子对文件生成相应 `MD5` 校验值，在此过程中我们使用 `Worker API` ，阐述不通隔离级别下多任务并行。

#### 创建自定义任务类


### 8.3. 生命周期

`Gradle` 保证这些任务按照它们的依赖顺序执行，并且每个任务只执行一次，最终任务会形成了一个有向无环图。

#### 8.3.1. 构建阶段

- 初始化阶段： `Gradle` 支持单项目和多项目构建。在初始化阶段， `Gradle` 确定哪些项目将参与构建，并为每个项目创建一个 `Project` 实例
- 配置阶段：在此阶段配置项目对象。执行作为构建一部分的所有项目的构建脚本
- 执行阶段： `Gradle` 确定在配置阶段创建和配置的要执行的任务的子集，再将子集传递给 `gradle` 命令和当前目录的任务名称参数决定。 `Gradle` 然后执行每个选定的任务
  
#### 8.3.2. 配置文件

除了构建脚本文件之外， `Gradle` 还定义了一个设置文件。设置文件由 `Gradle` 通过命名约定确定此文件的默认名称是 `settings.gradle`。设置文件在初始化阶段执行。多项目构建必须在多项目层次结构的根项目中有一个 `settings.gradle` 文件，定义了哪些项目参与构建，但是对于单项目构建，设置文件是可选的。

~~~gradle

rootProject.name = 'pippin'

include('pippin-common')
include('pippin-common:common-utils')
include('pippin-service')

~~~

#### 8.3.3. 初始化

`Gradle` 会使用 `settings.gradle` 配置文件的触发多项目构建。在 `settings.gradle` 文件的项目中执行 `Gradle`，当在没有 `settings.gradle` 文件的项目中执行 `Gradle` ，`Gradle` 则通常按照以下规则查找 `settings.gradle` 文件。

- 在父目录中查找 `settings.gradle`
- 如果未找到，则构建作为单个项目构建执行
- 如果找到 `settings.gradle` 文件，`Gradle` 会检查当前项目是否是在找到的 `settings.gradle` 文件中定义的多项目层次结构的一部分，如果没有，构建将作为单个项目构建执行；否则执行多项目构建。

#### 8.3.4. 构建生命周期

整个构建过程的生命周期中可以定义接收通知，接收通知主要有两种形式：实现特定的侦听器接口，或者提供一个闭包以在通知被触发时执行。

### 8.4. 多环境

目开发过程中，有开发环境、测试环境、生产环境，每个环境的配置也相同，与`Maven`项目类似，`Gradle`配置多环境用在环境属性文件和依赖配置 两个地方，实现可分为以下步骤：

- 通过约定规则，编写多环境信息
- 在 `build.gradle`文件中添加获取

~~~gradle
def env=System.getProperty('env')?:'dev'
~~~

- 最后在执行过程中添加参数：

~~~cmd
-D{属性名称}={内容}
~~~

### 8.5. 插件

`Gradle` 中有两种通用类型的插件，二进制插件和脚本插件。

- 二进制插件可以通过实现插件接口以编程方式编写，也可以使用 `Gradle` 的一种 `DSL` 语言以声明方式编写。二进制插件可以驻留在构建脚本中、项目层次结构中或外部插件 `jar` 中。

- 脚本插件是额外的构建脚本，可进一步配置构建并通常实施声明性方法来操作构建，它们通常在构建中使用，尽管它们可以被外部化并从远程位置访问。

下表是 `Gradle` 发行版支持的插件。

| 名称 | 描述 |
|--|--|
| Java插件 | 为Java提供编辑、测试和打包的能力 |
| java-library插件 | 构建本地依赖的插件 |
| maven-publish插件 | 项目发布到仓库的插件 |
| Springboot插件 | 提供Spring Boot支持，允许打包可执行jar或war |
| War插件 | 继承Java插件，为Web应用提供War包的功能 |
| IDEA插件 | 生成IDEA的工程文件，使得工程快速导入IDEA工程中 |
| Jetty插件 | 继承War插件，为应用程序添加三个主要任务，把应用部署到Jetty Web容器 |
| Application插件 | 创建可执行JVM应用，直接打成包含启动脚本Tar和Zip包 |
| CheckStyle插件 | 为工程添加CheckStyle质量检测 |
| FindBugs插件 | 为工程添加FindBugs质量检测 |
| PMD插件 | 为工程添加PMD质量检测 |
| JaCoCo插件 | 分析单元测试覆盖率的工具 |
| Sonar插件 | 分析检查代码质量 |

#### 8.5.1. 二进制插件

`Gradle` 提供核心插件作为其发行版的一部分，它们会自动解析，即官方插件。非核心二进制插件需要先解决才能应用。

#### 8.5.2. 自定义插件

自定义 `Gradle` 插件，需要编写一个实现 `Plugin` 接口的类，当插件应用于项目时，`Gradle` 会创建插件类的实例并调用该实例的 `Plugin.apply()` 方法，项目对象作为参数传递，插件使用项目对象来配置项目。

~~~gradle
apply plugin: WorkPlugin
class WorkPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.task('sayWorld'){
            doLast {
                println 'Hello from the GreetingPlugin'
            }
        }
    }
}
~~~

此时执行 `gradle -q sayWorld`

~~~cmd

>gradle -q sayWorld

11:07:42: Executing task 'sayWorld --quiet'...

pippin-common: version = 1.0.0-SNAPSHOT
pippin-service: version = 1.0.0-SNAPSHOT
common-utils: version = 1.0.0-SNAPSHOT
Hello from the GreetingPlugin
11:07:46: Task execution finished 'sayWorld --quiet'.

~~~

插件使用过程中采用扩展对象来执行相应操作，`Gradle` 项目有一个关联的 `ExtensionContainer` 容器类，包含可应用于项目的插件的所有设置和属性，通过向  `ExtensionContainer` 容器添加扩展对象来为插件提供配置。


~~~gradle

apply plugin: WorkPlugin

abstract class WorkPluginExtension {
    abstract Property<String> getMessage()

    WorkPluginExtension() {
        message.convention('Hello from GreetingPlugin')
    }
}

class WorkPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        // 添加 属性扩展类
        def extension = project.extensions.create('work',WorkPluginExtension)

        project.task('sayWorld'){
            doLast {
                println extension.message.get()
            }
        }
    }
}

~~~

#### 8.5.3. 脚本插件

脚本插件会自动解析，可以从本地文件系统或远程位置的脚本中应用，文件的位置是相对于项目目录，如果是远程脚本位置使用 `HTTP URL` 指定。

#### 8.5.4. 插件社区

`Gradle` 拥有一个充满活力的插件开发者社区，他们为各种功能贡献了插件 ，[官方地址](https://plugins.gradle.org/)

#### 8.5.5. 项目打包

一个完整的项目打包过程中，会包含三个 `*.jar` 文件：主程序 `projectName-version.jar`、源代码 `projectName-version-sources.jar`、文档 `projectName-version-javadoc.jar`，所以要想在 `Gradle`中生成上述文档，需要在项目定义相关的任务处理来完成这三个文件的输出。


### 8.6. 项目发布

项目发布我们使用插件 `maven-publish`，将构建内容发布到`Apache Maven`存储库的功能。可以被 `Maven`、`Gradle` 和其他了解 `Maven` 存储库格式的工具使用

~~~gradle
apply plugin: 'maven-publish'
~~~

![20210909163335](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909163335.png)