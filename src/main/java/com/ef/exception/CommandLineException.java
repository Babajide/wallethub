package com.ef.exception;

/**
 * thrown when an cmd options are/is invalid
 */
public class CommandLineException extends RuntimeException {
    
    public CommandLineException(String message){
        super(message);
    }
    
}
