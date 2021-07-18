package me.rhys.alerts.redis.message;

import lombok.Getter;
import me.rhys.alerts.Plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class RedisPublisher {

    private final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void publishMessage(String message) {
        if (Plugin.getInstance().getRedisManager().isConnected()) {
            this.executorService.execute(() -> Plugin.getInstance()
                    .getRedisManager().getJedis().publish(Plugin.getInstance()
                    .getConfigValues().getRedisChannel(), message));
        }
    }

    public void shutdown() {
        this.executorService.shutdownNow();
    }
}
