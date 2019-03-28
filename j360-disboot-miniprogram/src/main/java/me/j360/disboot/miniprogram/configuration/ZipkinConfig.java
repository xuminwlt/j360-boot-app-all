package me.j360.disboot.miniprogram.configuration;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.servlet.TracingFilter;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: min_xu
 */
@Configuration
public class ZipkinConfig {

    /** Controls aspects of tracing such as the service name that shows up in the UI */
    @Bean
    Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName("j360-miniprogram")
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "web"))
                .build();
    }

    /** Decides how to name and tag spans. By default they are named the same as the http method */
    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    /** Creates server spans for http requests */
    @Bean
    Filter tracingFilter(HttpTracing httpTracing) {
        return TracingFilter.create(httpTracing);
    }

    @Bean
    public RestTemplate restTemplate(HttpTracing httpTracing) {
        RestTemplate restTemplate =  new RestTemplate();

        MediaType[] mediaTypes = new MediaType[]{
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM,

                MediaType.APPLICATION_JSON_UTF8,
                MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_XML,
                MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_PDF,
        };


        // 将响应体转换为字符串
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        // 如果有实体,可使用转换器自动将响应体中的JSON转换为实体对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(mediaTypes));
        messageConverters.add(converter);

        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG));
        messageConverters.add(byteArrayHttpMessageConverter);

        messageConverters.add(new SourceHttpMessageConverter());
        //
        restTemplate.setMessageConverters(messageConverters);

        restTemplate.setInterceptors(Collections.singletonList(TracingClientHttpRequestInterceptor.create(httpTracing)));
        return restTemplate;
    }

}
