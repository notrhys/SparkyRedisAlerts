package me.rhys.alerts.redis;

import lombok.Getter;
import me.rhys.alerts.Plugin;
import me.rhys.alerts.redis.message.RedisPublisher;
import me.rhys.alerts.redis.message.RedisSubscriber;
import redis.clients.jedis.Jedis;

@Getter
public class RedisManager {
    private Jedis jedis;

    private final RedisPublisher redisPublisher = new RedisPublisher();
    private final RedisSubscriber redisSubscriber = new RedisSubscriber();

    public void connect() {
        Plugin.getInstance().getLogger().info("Connecting to Redis server...");

        try {
            this.jedis = new Jedis(
                    Plugin.getInstance().getConfigValues().getRedisIP(),
                    Plugin.getInstance().getConfigValues().getRedisPort()
            );

            this.jedis.auth(Plugin.getInstance().getConfigValues().getRedisPassword());

            this.redisSubscriber.startSubscriber();
        } catch (Exception exception) {
            Plugin.getInstance().getLogger().warning("Unable to connect to the Redis server hosted on" +
                    " (" + Plugin.getInstance().getConfigValues().getRedisIP()
                    + ":" + Plugin.getInstance().getConfigValues().getRedisPort()
                    + ") check the connection information. [" + exception.getMessage() + "]");
        }
    }

    public void disconnect() {
        Plugin.getInstance().getLogger().info("Disconnecting from Redis server...");

        if (this.jedis != null && this.jedis.isConnected()) {
            this.jedis.disconnect();
            this.jedis.close();
            this.redisSubscriber.shutdown();
            this.redisPublisher.shutdown();
            Plugin.getInstance().getLogger().info("Disconnected from Redis server!");
        }
    }

    public boolean isConnected() {
        return this.jedis != null && this.jedis.isConnected();
    }
}
