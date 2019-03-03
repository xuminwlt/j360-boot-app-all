package me.j360.disboot.common.aop;

import lombok.extern.slf4j.Slf4j;
import me.j360.framework.boot.aop.AbstractServiceAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * service层aop切面
 * (切点匹配根据阿里java开发规范，读：get/list/find/count
 * 写:insert/save/add/remove/delete/update)
 *
 * @description
 **/
@Slf4j
@Aspect
@Component
public class BizServiceAspect extends AbstractServiceAspect {

    // 方法切点,只切本地的方法,不切外部调用的方法
    @Override
    @Pointcut("execution(* me.j360.*.service.*Impl.*(..))")
    protected void allPointcut() {

    }
}
