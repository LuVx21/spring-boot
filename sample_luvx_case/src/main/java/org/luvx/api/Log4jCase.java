package org.luvx.api;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jCase {
    private static Logger logger = LogManager.getLogger(Log4jCase.class.getName());

    public static boolean hello() {

        // 错误级别依次上升
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
        return false;
    }
}