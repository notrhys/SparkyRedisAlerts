package me.rhys.alerts.hook;

import ac.sparky.api.events.SparkyViolationEvent;
import me.rhys.alerts.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SparkyListener implements Listener {

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onSparkyViolation(SparkyViolationEvent event) {
        Plugin.getInstance().getRedisManager().getRedisPublisher().publishMessage(
                Plugin.getInstance().getConfigValues().getServerName() + ":"
                        + event.getPlayer().getName() + ":" + event.getCheckName()
                        + ":" + event.getCheckType() + ":" + event.getCategory()
                        + ":" + event.getViolation() + ":" + event.isCancelled()
        );
    }
}
