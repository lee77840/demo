package com.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopLoggingConfig {
	
	Logger logger =  LoggerFactory.getLogger(AopLoggingConfig.class);
	
	static String name = "";
	
	static String type = "";

	/**
     *   AspectJ is applied only to a specific class/method with package.
     */
	//@Around("execution(* com.demo.customer.service.impl.CustomerServiceImpl.*(..))")
	@Around("execution(* com.demo.customer.CustomerController.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		
		if (type.indexOf("Controller") > -1) {
			name = "Controller  \t:  ";
		}
		else if (type.indexOf("Service") > -1) {
			name = "ServiceImpl  \t:  ";
		}
		else if (type.indexOf("DAO") > -1) {
			name = "DAO  \t\t:  ";
		}
		logger.debug(name + type + ".@@@@@@@@@@@@@@@@@@@@@@@@@ " + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}

	
}
