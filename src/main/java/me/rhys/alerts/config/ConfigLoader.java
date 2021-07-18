package me.rhys.alerts.config;

import me.rhys.alerts.Plugin;

public class ConfigLoader {

    public void loadConfig() {
        this.setup();
        this.getValues();
    }

    void getValues() {
        Plugin.getInstance().getConfigValues().setServerName(Plugin.getInstance()
                .getConfig().getString("ServerName"));

        Plugin.getInstance().getConfigValues().setRedisIP(Plugin.getInstance()
                .getConfig().getString("Redis.IP"));

        Plugin.getInstance().getConfigValues().setRedisPort(Plugin.getInstance()
                .getConfig().getInt("Redis.port"));

        Plugin.getInstance().getConfigValues().setRedisPassword(Plugin.getInstance()
                .getConfig().getString("Redis.password"));

        Plugin.getInstance().getConfigValues().setRedisChannel(Plugin.getInstance()
                .getConfig().getString("Redis.channelName"));

        Plugin.getInstance().getConfigValues().setAlertMessage(
                this.convertColor(Plugin.getInstance().getConfig().getString("Message.AlertMessage"))
        );

        Plugin.getInstance().getConfigValues().setAlertPermission(Plugin.getInstance()
                .getConfig().getString("Permission.AlertPermission"));
    }

    void setup() {
        Plugin.getInstance().getConfig().options().copyDefaults(true);
        Plugin.getInstance().saveConfig();
    }

    String convertColor(String in) {
        return in.replace("&", "§");
    }
}
