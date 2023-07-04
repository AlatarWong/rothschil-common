<!-- TOC -->

- [1. 环境构建](#1-环境构建)
  - [1.1. groovysh](#11-groovysh)
  - [1.2. IDEA创建Groovy](#12-idea创建groovy)
- [2. 基础语法](#2-基础语法)
  - [2.1. 数据类型](#21-数据类型)
  - [2.2. 数组](#22-数组)
  - [2.3. 列表](#23-列表)
  - [2.4. 散列](#24-散列)
- [3. 多线程](#3-多线程)
  - [3.1. 定义](#31-定义)
  - [3.2. 线程同步](#32-线程同步)
  - [3.3. 定时任务](#33-定时任务)
  - [3.4. J.U.C操作](#34-juc操作)
  - [3.5. 线程池](#35-线程池)
- [4. 更改类型](#4-更改类型)
- [5. 数据溢出](#5-数据溢出)
- [6. 异常](#6-异常)

<!-- /TOC -->

## 1. 环境构建

官网下载 `Groovy`，当前最新版本 **3.0.7**， `Groovy`是一个压缩的工具包，对公工具包解压，并讲 `Groovy`下的bin路径，配置到本地操作系统环境 `path` 属性。

![20210908095718](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908095718.png)

![20210908095612](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908095612.png)

### 1.1. groovysh

`Groovy` 提供一个交互式编程，`groovysh`

![20210908095756](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908095756.png)

### 1.2. IDEA创建Groovy

这里在 `Gradle`项目中新建 `Groovy` 项目 模块， `Groovy libray` 选择 `Groovy` 的文件路径

![20210908100620](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908100620.png)

![20210908100716](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908100716.png)

- 创建包名为 `xyz.wongs.groovy`
- 编写脚本，这里要选择 `Groovy Script`

![20210908100908](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908100908.png)

编写我们第一个 `Groovy`代码，`Groovy` 与`Python`非常类似。

~~~groovy
package xyz.wongs.groovy

// python 3的语法结构
println('Hello Groovy')
~~~

![20210908101202](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908101202.png)

## 2. 基础语法

### 2.1. 数据类型

![20210908104458](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210908104458.png)

### 2.2. 数组

`Groovy` 数组由 `Java`中 `java.util.Arrays` 工具提供的，所有`Java`中对数组的操作，在Groov y中同样适用

![20210909152949](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909152949.png)

### 2.3. 列表

列表是用于存储数据项集合的结构。在 `Groovy` 中，`List` 保存了一系列对象引用。List 中的对象引用占据序列中的位置，并通过整数索引来区分。列表文字表示为一系列用逗号分隔并用方括号括起来的对象。groovy 列表使用索引操作符 [] 索引。列表索引从 0 开始，指第一个元素。groovy 中的一个列表中的数据可以是任意类型。这` java` 下集合列表有些不同，java 下的列表是同种类型的数据集合。

![常用方法](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909153004.png)

~~~groovy
package gvy02

// 列表

def list = [1,'2',23]
println('【列表输出】'+list)
println('【列表转字符】'+list.toString())
println('【列表长度】'+list.last())

~~~

### 2.4. 散列

也称为关联数组、映射、表，是对象引用的无序集合，类似Java中 Map，格式如： ['Key1'：'Sam'，'Key2'：'Abram'] 

![20210909153133](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909153133.png)

~~~groovy
package gvy02

// 字典 Map

def map = ['key1':'Grovy','key2':'Java']
println('【Map输出】'+map)
println('【Map转字符】'+map.toString())
println('【Map元素个数为】'+map.size())

for (String s:map.keySet()){
    println('【遍历】'+'Key='+s+' Value = '+map.get(s))
}

~~~

## 3. 多线程

`Java`开发过程中通过`J.U.C`工具定义多线程，`Groovy`参考`Python`脚本并且对`Java`的`java.lang.Thread`进行扩展，简化了多线程的定义。 `Groovy`线程利用闭包进行定义的居多，闭包的方式主要有 `Thread.start` 和 `Thread.startDaemon`。当然`Groovy`中也有定时任务，分别是`Timer` 和 `TimerTask`，两种方式。
综述， `Groovy`的多线程开发， 仅仅是进行`Thread`结构的变化，其他结构均按照`Java`程序的方式定义。

### 3.1. 定义

~~~groovy

package gvy05

/**
 * 1、多线程的定义
 */

println('【主线程】' + Thread.currentThread().getName())

// 1.1 start 模式
Thread.start {
    println('【子线程-start】' + Thread.currentThread().getName())
    'Groovy Project :https://gitee.com/rothschil/pippin'.each {
        print(it)
    }
    println('\n')
}

println('https://www.github.com/rothschil/pippin')

// 1.2 startDaemon 模式
Thread.startDaemon {
    println('【子线程-startDaemon】' + Thread.currentThread().getName())
    'Groovy Project :https://gitee.com/rothschil/'.each {
        print(it)
    }
    println('\n')
}

~~~

### 3.2. 线程同步

~~~groovy
def ticket =10

def sale ={->
    synchronized (this){
        sleep(100)
        println('[Buy tickets]'+Thread.currentThread().getName()+ ' The remaining votes ' +(ticket--))
    }
}

for(x in 1..10){
    def st = Thread.start(sale)
    st.name='TicketSeller -'+x
}
~~~

### 3.3. 定时任务

~~~groovy
package gvy05

// 定时任务
new Timer('[Timer]').runAfter(1000) {
    for (int i = 0; i < 10; i++) {
        println('【定时任务】' + Thread.currentThread().getName() + ' Groovy Project :https://gitee.com/rothschil/pippin')
    }
}
~~~

### 3.4. J.U.C操作

~~~groovy

package gvy05

import java.util.concurrent.CountDownLatch

// JUC 定义两个线程，让线程1 必须要等待线程2 执行完毕后 才能执行
CountDownLatch countDownLatch = new CountDownLatch(1)

def firstThread = Thread.start {
    countDownLatch.await()
    println('[The First Thread] ' + Thread.currentThread().getName())
}

def secondThread = Thread.start {
    sleep(1000)
    println('[The Second Thread] ' + Thread.currentThread().getName())
    countDownLatch.countDown()
}
// 第一个线程先启动
firstThread
secondThread

~~~

### 3.5. 线程池

~~~groovy
package gvy05

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

for (x in 1..20) {
    es.submit(() -> {
        println('[ThreadPool]' + Thread.currentThread().getName())
    })
}
~~~

## 4. 更改类型

使用 `def`关键字定义的所有变量类型都是可以动态更改，这和 `Python`类似，使用起来比较灵活。

~~~groovy
package gvy01

def msg = 'Groovy'
println("【1】 msg 对应的类型是 "+ msg.class)

msg = 3
println("【2】 msg 对应的类型是 "+ msg.class)

msg = 1.3
println("【3】 msg 对应的类型是 "+ msg.class)

~~~

![20210909155259](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909155259.png)

## 5. 数据溢出

~~~groovy
package gvy01


def ix1 = Integer.MAX_VALUE+1

println("【1】 ix1 对应的类型是 "+ ix1.class +" 变量内容："+ix1)

def ix2 =  21474836499

println("【2】 ix2 对应的类型是 "+ ix2.class +" 变量内容："+ix2)

def ix3 =  2342546543634532424324233

println("【3】 ix3 对应的类型是 "+ ix3.class +" 变量内容："+ix3)
~~~

![20210909155415](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909155415.png)

## 6. 异常

和 `Java`一样的异常处理方式

- 检测异常：在编译时检查异常
- 未经检查的异常：未检查的异常在编译期不检查，而是在运行时检查，如 `ArrayIndexOutOfBoundsException`
- 错误：程序永远不能恢复的错误，将导致程序崩溃，如 `OutOfMemoryError`，`VirtualMachineError`，`AssertionError`

![20210909155510](https://abram.oss-cn-shanghai.aliyuncs.com/blog/gradle/20210909155510.png)