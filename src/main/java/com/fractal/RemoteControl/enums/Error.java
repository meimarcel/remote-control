package com.fractal.RemoteControl.enums;

public enum Error {
    
    COMMAND_COULD_NOT_BE_EXECUTED("EXECUTE COMMAND ERROR","Command could not be executed."),
    INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR", "Something went wrong, try again later."),
    COULD_NOT_GET_IP("COULD NOT GET THE HOST CURRENT IP", "Could not get the current host IP, try it with the \"ifconfig\" command"),
    ;

    private String title;
    private String message;

    private Error(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMessage() {
        return this.message;
    }

}
