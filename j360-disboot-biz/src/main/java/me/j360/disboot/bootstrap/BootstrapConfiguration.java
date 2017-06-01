package me.j360.disboot.bootstrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;

/**
 * Package: me.j360.disboot.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午6:15
 * 说明：
 */


@Configuration
@ImportResource("classpath:/spring-dubbo.xml")
public class BootstrapConfiguration {


    @Bean
    public DataSource dataSource() {
        return null;
    }




}
