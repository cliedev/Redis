package xyz.thesparkle.redis.managers;

import com.google.inject.internal.cglib.core.$HashCodeCustomizer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.thesparkle.redis.sRedis;
import xyz.thesparkle.redis.utils.GameServer;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RedisManager {

    @Getter
    public static RedisManager instance;

    private final JedisPool pool;
    private final JavaPlugin plugin;
    private final GameServer server;

    public RedisManager(JavaPlugin plugin, String currentServer, JedisPool pool) {
        instance = this;
        this.pool = pool;


        this.server = new GameServer(currentServer);
        this.plugin = plugin;

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            server.setOnline(true);
            server.setMaxOnlinePlayers(Bukkit.getMaxPlayers());
            server.setOnlinePlayers(Bukkit.getServer().getOnlinePlayers().size());
            server.setWhitelist(Bukkit.getServer().hasWhitelist());
            server.setMOTD(Bukkit.getMotd());
            server.setTPS(Bukkit.spigot().getTPS()[0]);
            updatePeriodically(server);
        }, 0L, 20L * 5);
    }

    public void updatePeriodically(GameServer server) {
        try(Jedis jedis = pool.getResource()) {
            jedis.set("arcaniumstatus:"+server.getServerName(), sRedis.getInstance().PLAIN_GSON.toJson(server));
        }
    }


    public JedisPool getPool() {
        return pool;
    }

    @EventHandler
    public void disable(PluginDisableEvent e) {
        if(e.getPlugin() == plugin) {
            server.setOnline(false);
            updatePeriodically(server);
        }
    }

    public GameServer getData(String server) {
        try(Jedis jedis = pool.getResource()) {
            return sRedis.getInstance().PLAIN_GSON.fromJson(jedis.get("arcaniumstatus:"+server), GameServer.class);
        }
    }
}