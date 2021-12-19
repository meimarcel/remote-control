package com.fractal.RemoteControl.enums;

public enum LinuxCommand {
    VOLUME_UP("amixer -D pulse sset Master 5%+"),
    VOLUME_DOWN("amixer -D pulse sset Master 5%-"),
    VOLUME_MUTE_UNMUTE("amixer -D pulse sset Master toggle"),
    VOLUME_SET("amixer -D pulse sset Master {0}%"),
    VOLUME_GET("amixer -D pulse sget Master"),
    MEDIA_PLAY_PAUSE("playerctl play-pause smplayer"),
    MEDIA_NEXT("playerctl next smplayer"),
    MEDIA_PREVIOUS("playerctl previous smplayer")
    ;

    private String command;

    private LinuxCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    public String getCommand(String... param) {
        String newComand = this.command;

        for(int i = 0; i < param.length; ++i) {
            newComand = newComand.replace("{"+i+"}", param[i]);
        }
 
        return newComand;
    }
}
