package com.yp.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
    public static Logger logger = Logger.getLogger("CONSOLE");
    public Logger fileLogger = Logger.getLogger("FILE");
    public Logger fileLogger2 = Logger.getLogger("FILE2");

    public LogUtil() {
        PropertyConfigurator.configure("../qa-common/src/main/resources/log4j.properties");
        if (System.getProperty("level") != null) {
            if (System.getProperty("level").equalsIgnoreCase("DEBUG")) {
                logger.setLevel(Level.DEBUG);
            } else if (System.getProperty("level").equalsIgnoreCase("ERROR")) {
                logger.setLevel(Level.ERROR);
            } else if (System.getProperty("level").equalsIgnoreCase("ALL")) {
                logger.setLevel(Level.ALL);
            } else if (System.getProperty("level").equalsIgnoreCase("WARN")) {
                logger.setLevel(Level.WARN);
            } else if (System.getProperty("level").equalsIgnoreCase("FATAL")) {
                logger.setLevel((Level.FATAL));
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public Logger getFileLogger() {
        return fileLogger;
    }
}