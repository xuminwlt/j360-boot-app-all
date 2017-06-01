使用spring-boot脚手架重建线上工程

验证功能点:

1. CI配套
    
    - 开发
    - 单元测试
    - 本地
    - 提测
    - 
    
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
5. Log支持
6. 启动集成
7. RPC集成


jekins配置及步骤



