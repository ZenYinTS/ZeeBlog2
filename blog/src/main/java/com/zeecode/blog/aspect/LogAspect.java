package com.zeecode.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    //使用logger进行记录
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //切面用一个方法定义，可以是空方法，也叫切入点
    // execution中的内容表示拦截哪些类，格式：[访问修饰符（public.private） 返回值 包名.包名.类名.方法名（参数列表）]
    //访问修饰符可以省略，下面的第一个*是指方法返回值
    //切面是切割请求，web包下的所有类发送请求进行切面
    @Pointcut("execution(* com.zeecode.blog.web.*.*(..))")
    public void log(){}

    //在切入点前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //url和ip可以从request中获取，所以先获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //类名和方法名通过切面获取，添加参数JoinPoint
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        
        logger.info("Request:{}",requestLog);
    }

    @After("log()")
    public void doAfter(){
//        logger.info("------------ doAfter -----------");
    }

    //log()方法执行完返回时进行拦截
    //返回参数用object
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class RequestLog{
        private String url;
        private String ip;
        private String classMethod;    //类名.方法名
        private Object[] args;    //请求的参数
    }
}
