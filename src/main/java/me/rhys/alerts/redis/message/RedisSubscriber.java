package me.rhys.alerts.redis.message;

import lombok.Getter;
import me.rhys.alerts.Plugin;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class RedisSubscriber {

    private final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private Jedis subscriber;

    public void startSubscriber() {
        if (Plugin.getInstance().getRedisManager().isConnected()) {
            this.subscriber = new Jedis(
                    Plugin.getInstance().getConfigValues().getRedisIP(),
                    Plugin.getInstance().getConfigValues().getRedisPort()
            );

            this.subscriber.auth(Plugin.getInstance().getConfigValues().getRedisPassword());

            this.executorService.execute(() -> this.subscriber.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    if (channel.equalsIgnoreCase(Plugin.getInstance().getConfigValues().getRedisChannel())) {
                        handle(message);
                    }
                }
            }, Plugin.getInstance().getConfigValues().getRedisChannel()));
        }
    }

    void handle(String message) {
        if (message.contains(":")) {
            String[] split = message.split(":");
            String server = split[0];

            if (!Plugin.getInstance().getConfigValues().getServerName().equalsIgnoreCase(server)) {
                String playerName = split[1];
                String checkName = split[2];
                String checkType = split[3];
                String violationLevel = split[5];

                String alertMessage = Plugin.getInstance().getConfigValues()
                        .getAlertMessage()
                        .replace("%PLAYER%", playerName)
                        .replace("%CHECK%", checkName)
                        .replace("%TYPE%", checkType)
                        .replace("%VL%", violationLevel)
                        .replace("%SERVER%", server);

                for (Player player : Plugin.getInstance().getCachedAlerts()) {
                    player.sendMessage(alertMessage);
                }
            }
        }
    }

    public void shutdown() {
        this.subscriber.disconnect();
        this.subscriber.close();
        this.executorService.shutdownNow();
    }
}
