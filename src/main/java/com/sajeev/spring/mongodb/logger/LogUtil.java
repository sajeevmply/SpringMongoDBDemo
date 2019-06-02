package com.sajeev.spring.mongodb.logger;

import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;

public class LogUtil {

	public static void log(Logger logger, String message, LogLevel logLevel) {
	
		switch (logLevel) {
		case INFO:
			logger.info(message);
			break;
		case WARN:
			logger.warn(message);
			break;
		case ERROR:
			logger.error(message);
			break;
		case DEBUG:
			logger.debug(message);
			break;
		case TRACE:
			logger.trace(message);
			break;
		default:
			break;
		}
	}
	
	public static void log(Logger logger, String message, LogLevel logLevel, Throwable t) {
		
		switch (logLevel) {
		case INFO:
			logger.info(message,t);
			break;
		case WARN:
			logger.warn(message,t);
			break;
		case ERROR:
			logger.error(message,t);
			break;
		case DEBUG:
			logger.debug(message,t);
			break;
		case TRACE:
			logger.trace(message,t);
			break;
		default:
			break;
		}
	}
}
