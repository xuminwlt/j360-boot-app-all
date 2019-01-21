package me.j360.disboot.web.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * Package: me.j360.disboot.web.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午10:54
 * 说明：
 */

@Configuration
public class BootstrapConfiguration {


//    @PostConstruct
//    public void init() {
//        registerFallback();
//    }
//
//
//    @Bean
//    public SentinelResourceAspect sentinelResourceAspect() {
//        return new SentinelResourceAspect();
//    }
//
//
//    private static void registerFallback() {
//        // Register fallback handler for consumer.
//        // If you only want to handle degrading, you need to
//        // check the type of BlockException.
//        DubboFallbackRegistry.setConsumerFallback((a, b, ex) ->
//                new RpcResult("Error: " + ex.getClass().getTypeName()));
//    }
}
