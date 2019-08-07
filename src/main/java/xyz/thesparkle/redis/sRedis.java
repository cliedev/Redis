package xyz.thesparkle.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.qrakn.honcho.Honcho;
import lombok.Getter;

import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import xyz.thesparkle.redis.commands.StatusCommand;
import xyz.thesparkle.redis.managers.RedisManager;
import xyz.thesparkle.redis.utils.config.BukkitConfigHelper;
import xyz.thesparkle.redis.utils.config.ConfigCursor;

/**
 * Created by Clie on 03/08/2019
 */
@Getter
public class sRedis extends JavaPlugin {

    @Getter
    public static sRedis instance;
    private RedisManager redisManager;
    private BukkitConfigHelper rootConfig;
    private ConfigCursor cursor;
    private ConfigCursor otherCursor;

    private Honcho honcho;

    public static final Gson PLAIN_GSON = new GsonBuilder().create();

    public void onEnable() {
        instance = this;

        this.rootConfig = new BukkitConfigHelper(this, "config", this.getDataFolder().getAbsolutePath());
        this.cursor = new ConfigCursor(this.getRootConfig(), "redis");
        this.otherCursor = new ConfigCursor(this.rootConfig, "server");

        this.redisManager = new RedisManager(this, otherCursor.getString("name"), new JedisPool(new JedisPoolConfig(),
                this.cursor.getString("address"),
                cursor.getInt("port"), 1000,
                cursor.getString("password")));
        honcho = new Honcho(this);

        registerCommands();
    }


    public void onDisable() {
        instance = null;
        if (redisManager != null) {
            RedisManager.getInstance().getPool().close();
        }
    }

    public void registerCommands() {
        honcho.registerCommand(new StatusCommand());
    }

}
