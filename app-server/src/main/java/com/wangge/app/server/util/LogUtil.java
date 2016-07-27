package com.wangge.app.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志打印类,重新封装便于以后扩展
 * Created by barton on 16-6-7.
 */
public class LogUtil {

  static Logger logger = LoggerFactory.getLogger(LogUtil.class);

  public static void info(String message) {
    logger.info(message);
  }

  public static void info(String format, Object... args) {
    logger.info(format, args);
  }

  public static void debug(String message) {
    logger.debug(message);
  }

  public static void debug(String format, Object... args) {
    logger.debug(format, args);
  }

  public static void error(String message, Throwable throwable) {
    logger.error(message, throwable);
  }

  public static void error(String format, Object... args) {
    logger.error(format, args);
  }
}
