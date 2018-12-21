# 使用spring-boot脚手架重写分布式业务


[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/xuminwlt/j360-disboot-all.svg?branch=master)](https://travis-ci.org/xuminwlt/j360-disboot-all)


## 验证功能点:

1. 更新至最新版本依赖

----------------------------------

1. CI配套
    
    - 开发
    - 单元测试
    - 功能测试
    - 集成测试
    
2. JVM配置
    
    java -

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
        java -Xss8m -jar  ***.war  --spring.config.location=file:///etc/.myproject.yml
        
4. JVM监控
    jps
    
5. Log支持
    slf4j+logback
6. 启动集成
    
    多环境、多配置支持
    
7. RPC集成

    dubbo2.6 + boot2
    
8. Web集成
9. Shiro集成
    
    shiro + jwt + boot2
    参考: https://github.com/xuminwlt/j360-shiro
    module: j360-shiro-boot
    
10. 分表分库读写分离

    sharding-sphare v3.0+ boot2


jenkins配置及步骤



