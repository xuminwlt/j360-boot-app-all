# 使用spring-boot脚手架重写分布式业务

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/xuminwlt/j360-boot-app-all.svg?branch=master)](https://travis-ci.org/xuminwlt/j360-boot-app-all)

## 模块说明
 - disboot-base: 工程二分库基础包, 设置该系统相关的定义参数及硬配置
 - disboot-api: RPC模块之二分库
 - disboot-common: RPC模块实现通用配置及代码
 - disboot-biz-sjdbc: RPC模块(disboot-api)实现工程, 集成了Sharding-phere,RocketMQ等分布式框架及最佳实践用法
 - disboot-web: API端,使用Dubbo RPC连接biz模块
 - disboot-miniprogram: 小程序服务端快速上手指南,包含jwt授权鉴权, 微信小程序支付、小程序客服、小程序消息等内容
 - disboot-websocket: 应用中使用netty连接实现分布式集群中的点对点,点对多,广播消息收发,业务功能集成实践(Web Game/PUSH)

## 快速上手
 - 微信小程序服务端接口快速上手案例: <a href="./j360-disboot-miniprogram">j360-disboot-miniprogram</a>
 
 使用一个简单的小程序后台快速上手案例包含以下内容:
 1. 打包发布 maven打包成zip发布到linux,集成shell命令 start、restart、stop、console、dump等命令
 2. 集成nginx代理web https服务,快速配置小程序
 3. 集成无状态授权鉴权过程,客户端无需参与任何加解密过程、服务端自动实现会话身份识别
 4. 集成zipkin链路性能分析日志, 快速定位接口性能数据
 5. 集成小程序微信支付, 快速完成小程序支付过程和回调配置,(小程序不支持虚拟物品交易!)


## 功能点

### DevOps流程:
1. CI配套
    
    - 开发环境
    - 单元测试
    - 功能测试
    - 集成测试
    
2. JVM配置
    - env: 本地环境配置化
    
3. 多环境支持及参数配置

  参考aws服务本地化配置方式, 多环境多个配置文件,多个module可分别制定外部配置文件,[推荐]使用外部地址配置文件,指定/etc/.myproject.yml
  
  - 需要支持的环境列表,基本上可以满足前期不上配置系统开发及上线需求
    1. IDE单元测试
    2. IDE运行
    3. 独立环境运营
   
IDE环境maven配置:    
```
<plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <jvmArguments>
                            -Xdebug -Dspring.config.location=file:../.././yml/application-local.yml,classpath:application.yml
                        </jvmArguments>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <argLine>-Xdebug -Dspring.config.location=file:../.././yml/application-local.yml,file:../.././yml/application-sharding.yml,classpath:application.yml</argLine>
                    </configuration>
                </plugin>
            </plugins>
```
 
独立环境:
 
```
shell env中增加以下参数
java -jar xxxx.jar -Dspring.config.location=file:.././yml/application-local.yml,classpath:application.yml,classpath:application-sharding.yml

```
 
4. VM监控
    
    启动server.sh shell中开启JMX
    env中定义环境和额外的vm参数
    
5. Log支持

    - 默认使用: slf4j+logback
    - 可配置log4j2进行异步日志收集,异步日志收集需要控制异步线程池的停止和启动过程参数, 同时异步收集注意线程池队列打满时的拒绝策略默认为当前线程执行会导致当前线程影响。
        
    
6. 启动集成
    
    多环境、多配置支持:配置和源码分离可以通过vm参数进行多环境的配置支持
    
7. RPC集成

    dubbo2.7 + boot start
    
    
8. Web集成
    
    - j360-disboot-web
        http://localhost:8081/index.html#/login
    
9. Shiro集成
    
    - shiro + jwt + boot2 (j360-disboot-miniprogram)
       
10. 分表分库读写分离

    sharding-sphere v3.1 boot2

11. 全链路跟踪
    Skywalking/Zipkin
    
    - j360-miniprogram - 示例小程序服务端使用zipkin v5.0+ 进行servlet和http client request两种埋点方式进行收集
    
    
12. 分布式事务
    - Fescar
    - Saga
    - RocketMQ
    
    
13. RocketMQ
    事务消息,异步可靠性消息事务实现最终一致性机制,在部分场景下实现资源最终一致性。
    
    
14. 限流
    Sentinel
    
    
15. 分布式配置
    Apollo/Nacos
    
16. 规则引擎: Drools

17. SocketIO with Netty

   - 基于SocketIO协议的Netty版本实现Websocket分布式集群案例

    
## 链路跟踪

### 本地Metrics: Kamon Metrics

> 
    [Kamon](http://kamon.io/documentation/get-started/) is used to track saga performance and reports trace data to both log
    and [StatsD](https://github.com/etsy/statsd/). The easiest way to visualize tracing reports is to use a [docker image](http://kamon.io/documentation/kamon-statsd/0.6.6/overview/)
    composed with StatsD, [Graphite](http://graphite.wikidot.com/), and [Grafana](http://grafana.org/)

- 使用aspectj 运行 Kamon enabled. 

```
java -javaagent:/path/to/aspectj-weaver.jar -Dkamon.modules.kamon-annotation.auto-start=yes -Dkamon.modules.kamon-log-reporter.auto-start=yes -Dkamon.modules.kamon-statsd.auto-start=yes -jar saga.jar
```

b. Tracing data reported to  prometheus

grafana + prometheus

### 全链路分析: Skywalking

 
## sharding-sphare

```
-Dspring.config.location=file:.././yml/application-local.yml,classpath:application.yml,classpath:application-sharding.yml

```

## Sentinel
限流监控在首次拦截时初始化并生成数据,Dashboard需要才能同步到对应的数据

```
Shell

-Djava.net.preferIPv4Stack=true -Dcsp.sentinel.api.port=8731 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=dubbo-web-demo
```
## java shell

reset vm args in script/bin/env

```
SERVER_OPTS_MEMORY="-Xms64M -Xmx1G"
SERVER_OPTS_VM="-server -XX:NewRatio=3 -XX:SurvivorRatio=4 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=6 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:ConcGCThreads=4 -XX:ParallelGCThreads=4 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:ReservedCodeCacheSize=240M  -XX:+ExplicitGCInvokesConcurrent -XX:PretenureSizeThreshold=64m -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 -XX:CMSMaxAbortablePrecleanTime=6000 -XX:+CMSParallelRemarkEnabled -XX:+ParallelRefProcEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=10    -verbose:gc -Xloggc:gc_%p.log -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintGCTimeStamps -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=.   -XX:-OmitStackTraceInFastThrow -XX:-UseBiasedLocking -XX:AutoBoxCacheMax=20000 -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"
SERVER_OPTS="$SERVER_OPTS_MEMORY $SERVER_OPTS_VM -Dspring.config.location=file:.././yml/application-local.yml,classpath:application.yml"
```

## Fescar

针对支持ACID SQL数据库提供无入侵的分布式事务读未提交隔离级别支持,对于其他类存储提供手动Commit和Rollback支持.

# CI 

## jenkins配置及步骤