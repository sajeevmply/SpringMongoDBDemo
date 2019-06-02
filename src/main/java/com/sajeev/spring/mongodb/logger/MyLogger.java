package com.sajeev.spring.mongodb.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.logging.LogLevel;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLogger {

	LogLevel logLevel() default LogLevel.INFO;
	
	LogLevel exceptionLogLevel() default LogLevel.ERROR;
	
	boolean isCalculateTime() default false;
	
	boolean isLogMethodParams() default false;
	
	boolean isLogReturnValue() default false;
}
