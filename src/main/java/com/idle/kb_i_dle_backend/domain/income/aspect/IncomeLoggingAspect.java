package com.idle.kb_i_dle_backend.domain.income.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class IncomeLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(IncomeLoggingAspect.class);

    // IncomeService의 모든 메서드 실행 전에 로깅
    @Before("execution(* com.idle.kb_i_dle_backend.domain.income.service.IncomeService.*(..))")
    public void logBeforeIncomeMethods(JoinPoint joinPoint) {
        logger.info("Executing method: " + joinPoint.getSignature().getName());
        System.out.println("AOP 로그: " + joinPoint.getSignature().getName() + " 메서드 호출 전");
    }


    // IncomeService의 모든 메서드가 정상적으로 종료된 후 로깅
    @AfterReturning("execution(* com.idle.kb_i_dle_backend.domain.income.service.IncomeService.*(..))")
    public void logAfterIncomeMethods(JoinPoint joinPoint) {
        logger.info("Successfully executed method: " + joinPoint.getSignature().getName());
    }

    // IncomeService의 메서드 실행 중 예외 발생 시 로깅
    @AfterThrowing(pointcut = "execution(* com.idle.kb_i_dle_backend.domain.income.service.IncomeService.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in method: " + joinPoint.getSignature().getName(), error);
    }

}
