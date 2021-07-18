package me.rhys.alerts.config;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfigValues {
    private String serverName, redisIP, redisPassword, redisChannel, alertMessage, alertPermission;
    private int redisPort;
}
