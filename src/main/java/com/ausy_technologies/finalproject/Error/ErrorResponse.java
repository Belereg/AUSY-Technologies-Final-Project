package com.ausy_technologies.finalproject.Error;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorResponse extends RuntimeException {
    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().contains("jdwp");

    private String errorMessage;
    private int errorId;

    public ErrorResponse() {
        System.out.println("ErrorResponse() called.");
    }

    public ErrorResponse(String errorMessage, int errorId) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorId = errorId;
    }

    public ErrorResponse(Throwable cause, String errorMessage, int errorId) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.errorId = errorId;
    }

    public static void setupLogger() {
        logger.setLevel(Level.FINE);

        try {

            FileHandler fh = new FileHandler("fileLogger");
            logger.addHandler(fh);
            fh.setLevel(Level.FINE);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "The logger file was not created");
        }
    }


    public static void LogError(ErrorResponse errorResponse) {
        if (isDebug)
            errorResponse.printStackTrace();
        else
            logger.log(Level.SEVERE, errorResponse.getErrorMessage());
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getErrorId() {
        return this.errorId;
    }
}
