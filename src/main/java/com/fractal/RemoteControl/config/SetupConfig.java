package com.fractal.RemoteControl.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.fractal.RemoteControl.enums.Error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class SetupConfig {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SetupConfig.class);

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            String ip = "";
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            
            init:
            while(networks.hasMoreElements()) {
                NetworkInterface network = (NetworkInterface) networks.nextElement();
                Enumeration<InetAddress> inet = network.getInetAddresses();
                while (inet.hasMoreElements()){
                    InetAddress i = (InetAddress) inet.nextElement();
                    if(i.getHostAddress().contains("192.168.")) {
                        ip = i.getHostAddress();
                        break init;
                    }
                }
            }

            if(ip == "") {
                throw new RuntimeException();
            }

            System.out.println("##################################################");
            System.out.println("#  Your current IP Address : " + ip + "       #");
            System.out.println("##################################################");

        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error(Error.COULD_NOT_GET_IP.getTitle());
            LOGGER.error(Error.COULD_NOT_GET_IP.getMessage());
        }

    }

}
