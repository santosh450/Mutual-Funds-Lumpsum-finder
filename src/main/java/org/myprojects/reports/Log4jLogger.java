package org.myprojects.reports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Log4jLogger {

    private Log4jLogger() {
        // This constructor is intentionally empty to prevent instantiation
    }

    public static final Logger logger = LogManager.getLogger(Log4jLogger.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }
}
