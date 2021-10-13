package me.rhys.alerts;

import lombok.Getter;
import me.rhys.alerts.config.ConfigLoader;
import me.rhys.alerts.config.ConfigValues;
import me.rhys.alerts.hook.PlayerListener;
import me.rhys.alerts.hook.SparkyListener;
import me.rhys.alerts.redis.RedisManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Plugin extends JavaPlugin {
    @Getter private static Plugin instance;

    private final ConfigValues configValues = new ConfigValues();
    private final ConfigLoader configLoader = new ConfigLoader();
    private final RedisManager redisManager = new RedisManager();
    private final List<Player> cachedAlerts = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        this.configLoader.loadConfig();
        this.redisManager.connect();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if (Bukkit.getPluginManager().getPlugin("Sparky") != null) {
            getServer().getPluginManager().registerEvents(new SparkyListener(), this);
        }
    }

    @Override
    public void onDisable() {
        this.redisManager.disconnect();
        this.cachedAlerts.clear();
        instance = null;
    }
}
