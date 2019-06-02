package com.sajeev.spring.mongodb.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggerAspect {

	@Around("@annotation(com.sajeev.spring.mongodb.logger.MyLogger)")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		signature.getReturnType();
		MyLogger loggerAnnotation = signature.getMethod().getAnnotation(MyLogger.class);
		final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

		LogLevel msgLogLevel = loggerAnnotation.logLevel();

		if (loggerAnnotation.isLogMethodParams() && signature.getParameterNames() != null) {
			for (int i = 0; i <= signature.getParameterNames().length; i++) {
				LogUtil.log(logger, signature.getParameterNames()[i] + ": " + joinPoint.getArgs()[i], msgLogLevel);
			}
		}
		return handleMethodCall(joinPoint, signature, logger, loggerAnnotation);
	}

	private Object handleMethodCall(ProceedingJoinPoint joinPoint, MethodSignature signature, Logger logger,
			MyLogger loggerAnnotation) throws Throwable {
		Object returnValue = null;
		StopWatch watch = new StopWatch();
		String methodName = signature.getMethod().getName();
		try {
			startTimer(watch, loggerAnnotation);
			if (signature.getReturnType() != Void.class) {
				returnValue = joinPoint.proceed();
				if (loggerAnnotation.isLogReturnValue()) {
					LogUtil.log(logger, "The Method Name " + methodName + " returns " + returnValue,
							loggerAnnotation.logLevel());
				}
			}
		} catch (Exception e) {
			handleCatch(methodName, logger, loggerAnnotation, e);
		} finally {
			handleFinally(watch, methodName, logger, loggerAnnotation);
		}
		return returnValue;
	}

	private void handleFinally(StopWatch watch, String methodName, Logger logger, MyLogger loggerAnnotation) {
		if (null != watch && loggerAnnotation.isCalculateTime() && watch.isRunning()) {
			watch.stop();
			long timeTaken = watch.getTotalTimeMillis();
			LogUtil.log(logger, "The method " + methodName + " executen in " + timeTaken + "ms",
					loggerAnnotation.logLevel());
		}

	}

	private void handleCatch(String methodName, Logger logger, MyLogger loggerAnnotation, Throwable t)
			throws Throwable {
		LogUtil.log(logger, "The following exception occured while executing the method " + methodName,
				loggerAnnotation.logLevel(), t);
		throw t;
	}

	private void startTimer(StopWatch watch, MyLogger loggerAnnotation) {
		if (null != watch && loggerAnnotation.isCalculateTime() && !watch.isRunning()) {
			watch.start();
		}

	}
}
