package network.palace.vanish.listeners;

import network.palace.core.Core;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Marc on 12/23/16.
 */
public class PlayerJoinAndLeave implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CPlayer player = Core.getPlayerManager().getPlayer(event.getPlayer());
        if (player.getRank().getRankId() >= Rank.VIP.getRankId()) {
            if (player.getRank().getRankId() >= Rank.CHARACTER.getRankId()) {
                Vanish.getVanishUtil().hide(player, true);
            }
            return;
        }
        Vanish.getVanishUtil().login(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        quit(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        quit(event.getPlayer());
    }

    private void quit(Player bukkitPlayer) {
        CPlayer player = Core.getPlayerManager().getPlayer(bukkitPlayer);
        if (player == null) return;
        Vanish.getVanishUtil().logout(player);
    }
}
