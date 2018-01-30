package com.fdmgroup.issuetracker.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static final Logger log = Logger.getLogger(LoggingAspect.class);

	static {
		PropertyConfigurator.configure("./src/main/resources/log4j.properties");
	}

	
	@Around("execution(* com.fdmgroup.issuetracker.controller.*.*(..))")
	public Object trace(ProceedingJoinPoint pjp ) throws Throwable {
		String methodInfo = pjp.getStaticPart().getSignature().toString();
		log.trace("Entering " + methodInfo);
		try {
			return pjp.proceed();
		} catch (Throwable t) {
			log.error("Exception in " + methodInfo, t);
			throw t;
		} finally {
			log.trace("Exiting " + methodInfo);
		}
	}

}
