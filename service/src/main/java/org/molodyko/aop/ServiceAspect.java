package org.molodyko.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Aspect
@Slf4j
@Component
public class ServiceAspect {

    @Pointcut("within(org.molodyko.service.*Service)")
    public void isService() {

    }

    @Around("isService()")
    public Object loggingService(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("Входящие параметры метода {}: {}", joinPoint.getSignature(), joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.info("результат метода {}: {}", joinPoint.getSignature(),  result);
            return result;
        } catch (Throwable e) {
            log.info("залогировали ошибку метода {} : {}", joinPoint.getSignature(), e);
            throw e;
        }

    }
}
