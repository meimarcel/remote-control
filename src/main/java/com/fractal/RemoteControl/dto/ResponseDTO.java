package com.fractal.RemoteControl.dto;

public class ResponseDTO {

    private Integer volume;

    public ResponseDTO(){

    }

    public ResponseDTO(Integer volume) {
        this.volume = volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getVolume() {
        return this.volume;
    }
}
