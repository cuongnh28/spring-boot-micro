package com.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAop {
    @Before("execution(* com.demo.controller.ProductController.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Aop Before Controller");
    }

    @After("execution(* com.demo.controller.ProductController.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("Aop After Controller");
    }

}
