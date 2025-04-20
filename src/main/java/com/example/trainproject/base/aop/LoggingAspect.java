package com.example.trainproject.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
// @Component
public class LoggingAspect {

  @Pointcut("execution(* com.example.trainproject.base.Service.*.*(..))")
  public void consumeMessage() {

  }

  @Before("execution(* com.example.trainproject.base.Service.*.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    System.out.println("executing Method : " + joinPoint.getSignature().getName());
  }

  @After("execution(* com.example.trainproject.base.Service.*.*(..))")
  public void logAfter(JoinPoint joinPoint) {
    System.out.println("executed Method : " + joinPoint.getSignature().getName());
  }

  // execute after annotated class
  @Before("@annotation(com.example.trainproject.base.annotatio.Loggable)")
  public void logAfterAfterAnnotatedMethod(JoinPoint joinPoint) {
    System.out.println("📌 Method with @Loggable called: " + joinPoint.getSignature().getName());
    System.out.println(
        "joinPoint.getTarget().getClass().getSimpleName() " + joinPoint.getTarget().getClass()
            .getSimpleName());
    System.out.println("joinPoint.getArgs().toString() " + joinPoint.getArgs().toString());
    System.out.println("joinPoint.getThis() " + joinPoint.getThis());
    System.out.println(
        "joinPoint.getSignature().toLongString() " + joinPoint.getSignature().toLongString());
    System.out.println("joinPoint.getTarget() " + joinPoint.getTarget());
  }

  @Around("consumeMessage()")
  public void aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("around method executing: " + proceedingJoinPoint.getSignature().getName());
    Object object = proceedingJoinPoint.proceed();
    System.out.println("around method executed: " + proceedingJoinPoint.getSignature().getName());
  }

//  ✅ 6. Real-World Use Cases
//
//  Use Case	Advice Type
//  Logging	@Before / @After / @Around
//  Security Check	@Before
//  Transaction Management	@Around
//  Exception Alerting	@AfterThrowing
//  Performance Monitoring	@Around
//  Audit Trail	@AfterReturning


}
