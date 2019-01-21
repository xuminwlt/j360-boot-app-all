# 使用spring-boot脚手架重写分布式业务

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/xuminwlt/j360-disboot-all.svg?branch=master)](https://travis-ci.org/xuminwlt/j360-disboot-all)


## 快速上手

微信小程序服务端接口快速上手案例: j360-disboot-miniprogram


## 功能点

### DevOps流程:
1. CI配套
    
    - 开发
    - 单元测试
    - 功能测试
    - 集成测试
    
2. JVM配置
    参见 java shell
    
3. 多环境支持及参数配置
    
    多环境多个配置文件,多个module使用同一个配置文件,[推荐]使用外部地址配置文件,指定/etc/.myproject.yml
    local:
        mvn springboot:run
        
```
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!--mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"-->
                    <jvmArguments>
                        -Xdebug -Xss8m -Dspring.config.location=file:.././yml/application-dev.yml
                    </jvmArguments>
                </configuration>
            </plugin>
```  
        
    test:
    
        java -Xss8m -jar  ***.jar --spring.config.location=file:///etc/.myproject.yml    
    product:
    
        java -Xss8m -jar  ***.jar  --spring.config.location=file:///etc/.myproject.yml
            
        
4. VM监控
    
    启动server.sh shell中开启JMX
    env中定义环境和额外的vm参数
    
5. Log支持
    slf4j+logback
    
6. 启动集成
    
    多环境、多配置支持
    
7. RPC集成

    dubbo2.6 + boot2
    
8. Web集成
    
    - j360-disboot-web
        http://localhost:8081/index.html#/login
    
9. Shiro集成
    
    - shiro + jwt + boot2 (j360-disboot-miniprogram)
       
10. 分表分库读写分离
    sharding-sphere v3.0+ boot2

11. 全链路跟踪
    Skywalking

12. 分布式事务
    Fescar 忽略Pack(Saga)
    
13. RocketMQ support 事务
    事务消息

14. 限流
    Sentinel
    
15. 分布式配置
    Apollo/Nacos
    
16. Drools
    
## Trace Data

Kamon

### How to Enable Kamon Metrics
[Kamon](http://kamon.io/documentation/get-started/) is used to track saga performance and reports trace data to both log
and [StatsD](https://github.com/etsy/statsd/). The easiest way to visualize tracing reports is to use a [docker image](http://kamon.io/documentation/kamon-statsd/0.6.6/overview/)
composed with StatsD, [Graphite](http://graphite.wikidot.com/), and [Grafana](http://grafana.org/)

- Run with aspectj and Kamon enabled. 

a. By default, tracing data will be reported to StatsD running on `localhost` and port `8125`. To change the default StatsD
host configuration, pass `-Dkamon.statsd.hostname=<hostname>` and `-Dkamon.statsd.port=<port>` as vm options when running
saga.
```
java -javaagent:/path/to/aspectj-weaver.jar -Dkamon.modules.kamon-annotation.auto-start=yes -Dkamon.modules.kamon-log-reporter.auto-start=yes -Dkamon.modules.kamon-statsd.auto-start=yes -jar saga.jar
```

b. Tracing data reported to  prometheus

grafana + prometheus

### Skywalking full route trace



 
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