package com.fractal.RemoteControl.error;

public class CommandNotExecutedException extends RuntimeException{
    
    public CommandNotExecutedException(String message) {
        super(message);
    }

}
