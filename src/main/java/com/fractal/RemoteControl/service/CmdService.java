package com.fractal.RemoteControl.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fractal.RemoteControl.enums.Error;
import com.fractal.RemoteControl.error.CommandNotExecutedException;

import org.springframework.stereotype.Component;

@Component
public class CmdService {

    public void execute(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "cd && "+command).start();
        } catch(Exception e) {
            throw new CommandNotExecutedException(Error.COMMAND_COULD_NOT_BE_EXECUTED.getMessage());
        }
    }

    public List<String> executeAndResponse(String command) {
        try {
            List<String> response = new ArrayList<>();

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "cd && "+command);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";

            while((line = reader.readLine()) != null) {
                response.add(line);
            }

            return response;

        } catch (Exception e) {
            throw new CommandNotExecutedException(Error.COMMAND_COULD_NOT_BE_EXECUTED.getMessage());
        }
    }

}
