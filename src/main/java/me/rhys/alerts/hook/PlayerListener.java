package me.rhys.alerts.hook;

import me.rhys.alerts.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    public PlayerListener() {
        Bukkit.getServer().getOnlinePlayers().stream().filter(player ->
                player.hasPermission(Plugin.getInstance().getConfigValues().getAlertPermission()))
                .forEach(player -> Plugin.getInstance().getCachedAlerts().add(player));
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(Plugin.getInstance().getConfigValues().getAlertPermission())) {
            Plugin.getInstance().getCachedAlerts().add(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Plugin.getInstance().getCachedAlerts().remove(event.getPlayer());
    }
}
