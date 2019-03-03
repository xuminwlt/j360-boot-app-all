package me.j360.disboot.common.configuration;

import com.google.common.collect.Lists;
import me.j360.disboot.common.aop.BizServiceAspect;
import me.j360.disboot.common.rocketmq.MQProducter;
import me.j360.framework.common.pool.DefaultAsyncEventBus;
import org.rocketmq.starter.annotation.EnableRocketMQ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: min_xu
 * @date: 2019/1/11 5:53 PM
 * 说明：
 */
@Import({DefaultRocketMQConfiguration.class, MQProducter.class, BizServiceAspect.class})
@Configuration
@EnableRocketMQ
public class CommonConfig {

    @Bean
    public DefaultAsyncEventBus defaultAsyncEventBus() {
        return new DefaultAsyncEventBus(2, 4);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();

        // 将响应体转换为字符串
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        // 如果有实体,可使用转换器自动将响应体中的JSON转换为实体对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        messageConverters.add(converter);
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG));
        messageConverters.add(byteArrayHttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
