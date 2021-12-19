package com.fractal.RemoteControl.controller;

import java.util.List;

import com.fractal.RemoteControl.dto.ResponseDTO;
import com.fractal.RemoteControl.enums.LinuxCommand;
import com.fractal.RemoteControl.service.CmdService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("remote-control")
public class RemoteControlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteControlController.class);
    
    @Autowired
    private CmdService cmdService;

    @GetMapping("/volume-up")
    public ResponseEntity<?> volumeUp() {
        Integer volume = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_UP.getCommand()));

        ResponseDTO response = new ResponseDTO(volume);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume-down")
    public ResponseEntity<?> volumeDown() {
        Integer volume = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_DOWN.getCommand()));

        ResponseDTO response = new ResponseDTO(volume);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume-mute-unmute")
    public ResponseEntity<?> volumeMuteUnmute() {
        Integer volume = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_MUTE_UNMUTE.getCommand()));

        ResponseDTO response = new ResponseDTO(volume);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume-get")
    public ResponseEntity<?> volumeGet() {
        Integer volume = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_GET.getCommand()));

        ResponseDTO response = new ResponseDTO(volume);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume-set/{volume}")
    public ResponseEntity<?> volumeSet(@PathVariable("volume") int volume) {
        volume = Math.max(0, volume);
        volume = Math.min(100, volume);

        Integer volumeAtual = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_SET.getCommand(Integer.toString(volume))));

        ResponseDTO response = new ResponseDTO(volumeAtual);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/media-play-pause")
    public ResponseEntity<?> mediaPlayPause() {

        cmdService.execute(LinuxCommand.MEDIA_PLAY_PAUSE.getCommand());
        Integer volumeAtual = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_GET.getCommand()));

        ResponseDTO response = new ResponseDTO(volumeAtual);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/media-next")
    public ResponseEntity<?> mediaNext() {

        cmdService.execute(LinuxCommand.MEDIA_NEXT.getCommand());
        Integer volumeAtual = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_GET.getCommand()));

        ResponseDTO response = new ResponseDTO(volumeAtual);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/media-previous")
    public ResponseEntity<?> mediaPrevious() {

        cmdService.execute(LinuxCommand.MEDIA_PREVIOUS.getCommand());
        Integer volumeAtual = getVolume(cmdService.executeAndResponse(LinuxCommand.VOLUME_GET.getCommand()));

        ResponseDTO response = new ResponseDTO(volumeAtual);

        return ResponseEntity.ok(response);
    }

    private Integer getVolume(List<String> response) {
        for(String line : response) {
            if(line.contains("%")) {
                line = line.replace("[", "#")
                        .replace("]","#")
                        .replace("##", "#");
                
                String[] lineSplitted = line.split("#");
                for(String value : lineSplitted) {
                    if(value.contains("%")) {
                        try {
                            int volume = Integer.parseInt(value.replace("%", ""));
                            return volume;
                        } catch(Exception e) {
                            LOGGER.error("Erro ao tentar converter \""+value+"\" para Inteiro.");
                        }
                    }
                }
            }
        }

        return null;
    }

}
