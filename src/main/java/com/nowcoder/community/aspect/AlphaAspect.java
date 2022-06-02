package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")//组件，方法，值
    public void pointcut(){

    }

    @Before("pointcut()") //针对这个切点有效
    public void before(){
        System.out.println("before");
    }

    @After("pointcut()") //针对这个切点有效
    public void after(){
        System.out.println("after");
    }

    @AfterReturning("pointcut()") //针对这个切点有效
    public void afterReturning(){
        System.out.println("afterReturning");
    }
    @AfterThrowing("pointcut()") //针对这个切点有效
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }

    @Around("pointcut()") //针对这个切点有效
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }

}
