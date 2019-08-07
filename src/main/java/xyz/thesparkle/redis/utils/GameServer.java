package xyz.thesparkle.redis.utils;


import lombok.Getter;
import xyz.thesparkle.redis.sRedis;

/**
 * Created by Clie on 03/08/2019
 */

public class GameServer {

    private final String serverName;
    private String motd;
    private int onlinePlayers, maxOnlinePlayers;
    private boolean online, whitelist;
    private double tps;

    public GameServer(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaxOnlinePlayers() {
        return maxOnlinePlayers;
    }

    public void setMaxOnlinePlayers(int maxOnlinePlayers) {
        this.maxOnlinePlayers = maxOnlinePlayers;
    }

    public String getMOTD() {
        return motd;
    }

    public void setMOTD(String motd) {
        this.motd = motd;
    }

    public double getTPS() {
        return tps;
    }

    public void setTPS(double tps) {
        this.tps = tps;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isWhitelist() {
        return whitelist;
    }

    public void setWhitelist(boolean whitelist) {
        this.whitelist = whitelist;
    }
}
