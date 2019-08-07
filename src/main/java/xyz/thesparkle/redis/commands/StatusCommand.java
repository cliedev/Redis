package xyz.thesparkle.redis.commands;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.thesparkle.redis.managers.RedisManager;
import xyz.thesparkle.redis.utils.Color;

/**
 * Created by Clie on 03/08/2019
 */
@CommandMeta(label = {"status"}, permission = "rank.owner")
public class StatusCommand {

    private RedisManager redisManager = RedisManager.getInstance();

    public void execute(CommandSender sender, String server) {
        Player player = (Player) sender;
        if (server == null) {
            player.sendMessage("&cUsage: /status <serverName>");
            return;
        }

        player.sendMessage(Color.translate("&7&m------------------------------"));
        if (redisManager.getData(server) != null) {
            player.sendMessage(Color.translate("&a" + redisManager.getData(server).getServerName()));
            player.sendMessage(Color.translate(" &7* &eTPS: &6" + redisManager.getData(server).getTPS()));
            player.sendMessage(Color.translate(" &7* &eMOTD: &6" + redisManager.getData(server).getMOTD()));
            player.sendMessage(Color.translate(" &7* &eConnected players: &6" + redisManager.getData(server).getOnlinePlayers()));
            player.sendMessage(Color.translate(" &7* &eThe whitelist is currently " + (redisManager.getData(server).isWhitelist() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled")));
            player.sendMessage(Color.translate(" &7* &eThis server can hold up to &6" + redisManager.getData(server).getMaxOnlinePlayers() + " &eplayers"));
        } else {
            player.sendMessage(Color.translate("&c&m" + server));
            player.sendMessage(Color.translate("&7&oThis server is offline..."));
        }
        player.sendMessage(Color.translate("&7&m------------------------------"));

    }
}
