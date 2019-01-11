package me.j360.framework.boot.shiro;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.boot.shiro.filter.TokenAuthcFilter;
import me.j360.framework.boot.shiro.filter.TokenContextFilter;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: min_xu
 * @date: 2019/1/11 10:31 AM
 * 说明：
 */

@Slf4j
@Profile("token")
public abstract class AbstractTokenShiroConfiguration {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String handleException(AuthorizationException e, Model model) {
        log.info("AuthorizationException was thrown", e);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "No message available");
        model.addAttribute("errors", map);
        return "error";
    }

    @Autowired
    protected SecurityManager securityManager;
    @Autowired
    public Algorithm algorithm;
    @Autowired
    public JwtSignature jwtSignature;

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String, String> pathFilterMap = getFilterPathFilterMap();
        chainDefinition.addPathDefinitions(pathFilterMap);
        return chainDefinition;
    }

    public abstract Map<String, String> getFilterPathFilterMap();

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/success");
        filterFactoryBean.setUnauthorizedUrl("/unauthenticated");

        filterFactoryBean.setFilters(getCustomFilters());
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        return filterFactoryBean;
    }


    public abstract Map<String, Filter> getCustomFilters();


    @Bean
    public DefaultWebSecurityManager securityManager(@Autowired Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }


    @Bean
    public Algorithm algorithm() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return algorithm;
    }

    @Bean
    public JwtSignature jwtSignature() {
        JwtSignature jwtSignature = new JwtSignature(algorithm);
        return jwtSignature;
    }

    public TokenContextFilter tokenContextFilter() {
        TokenContextFilter context = new TokenContextFilter(jwtSignature);
        return context;
    }

    public TokenAuthcFilter tokenAuthcFilter() {
        TokenAuthcFilter authc = new TokenAuthcFilter(jwtSignature(), sessionStorageDAO());
        return authc;
    }

    public abstract SessionStorageDAO sessionStorageDAO();
}
