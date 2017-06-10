package network.palace.vanish.listeners;

import network.palace.core.Core;
import network.palace.core.events.CorePlayerJoinedEvent;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Created by Marc on 12/23/16.
 */
public class PlayerJoinAndLeave implements Listener {

    @EventHandler
    public void onPlayerJoin(CorePlayerJoinedEvent event) {
        if (event.getPlayer().getRank().getRankId() >= Rank.SPECIALGUEST.getRankId()) {
            if (event.getPlayer().getRank().getRankId() >= Rank.CHARACTER.getRankId()) {
                Vanish.getInstance().getVanishUtil().hide(event.getPlayer(), true);
            }
            return;
        }
        for (UUID uuid : Vanish.getInstance().getVanishUtil().getVanished()) {
            event.getPlayer().getBukkitPlayer().hidePlayer(Bukkit.getPlayer(uuid));
        }
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
        Vanish.getInstance().getVanishUtil().logout(player);
    }
}
