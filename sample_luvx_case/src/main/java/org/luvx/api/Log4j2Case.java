package org.luvx.api;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ALL")
public class Log4j2Case {
    // http://blog.csdn.net/lu8000/article/details/25754415
    // http://blog.csdn.net/autfish/article/details/51203709
    // trace<debug<info<warn<error<fatal

    // 指定log配置的位置
    // -Dlog4j.configurationFile=file:///C:\logtext.xml（路径必须带有协议file:///，不然会报错）

    private static Logger logger = LogManager.getLogger(Log4j2Case.class.getName());

    public static boolean hello() {
        // trace级别的信息，单独列出来是希望你在某个方法或者程序逻辑开始的时候调用，和logger.trace("entry")基本一个意思
        logger.entry();

        // 错误级别依次上升
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");

        // 这个就是制定Level类型的调用
        logger.log(Level.DEBUG, "debug level2");

        // 和entry()对应的结束方法，和logger.trace("exit");一个意思
        logger.exit();
        return false;
    }
}