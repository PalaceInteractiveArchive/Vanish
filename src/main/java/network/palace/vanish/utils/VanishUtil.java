package network.palace.vanish.utils;

import network.palace.core.Core;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marc on 12/16/16.
 */
public class VanishUtil {

    private List<UUID> hidden = new ArrayList<>();

    public void hide(CPlayer player) {
        hide(player, false);
    }

    public void hide(CPlayer player, boolean silent) {
        hidden.remove(player.getUniqueId());
        hidden.add(player.getUniqueId());
        if (!silent) {
            player.sendMessage(ChatColor.DARK_AQUA + "You have vanished. Poof.");
        }
        for (CPlayer onlinePlayer : Core.getPlayerManager().getOnlinePlayers()) {
            if (onlinePlayer.getRank().getRankId() < Rank.SPECIALGUEST.getRankId()) {
//                onlinePlayer.hidePlayer(Vanish.getInstance(), player);
                onlinePlayer.hidePlayer(player);
            } else if (!onlinePlayer.getUniqueId().equals(player.getUniqueId()) && !silent) {
                onlinePlayer.sendMessage(ChatColor.YELLOW + player.getName() + " has vanished. Poof.");
//                onlinePlayer.showPlayer(Vanish.getInstance(), player);
                onlinePlayer.showPlayer(player);
            }
        }
    }

    public void show(CPlayer player) {
        hidden.remove(player.getUniqueId());
        player.sendMessage(ChatColor.DARK_AQUA + "You have become visible.");
        for (CPlayer onlinePlayer : Core.getPlayerManager().getOnlinePlayers()) {
//            onlinePlayer.showPlayer(Vanish.getInstance(), player);
            onlinePlayer.showPlayer(player);
            if (onlinePlayer.getRank().getRankId() >= Rank.SPECIALGUEST.getRankId() &&
                    !onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                onlinePlayer.sendMessage(ChatColor.YELLOW + player.getName() + " has become visible.");
            }
        }
    }

    public void toggle(CPlayer player) {
        if (hidden.contains(player.getUniqueId())) {
            show(player);
        } else {
            hide(player);
        }
    }

    public boolean isVanished(UUID uuid) {
        return hidden.contains(uuid);
    }

    public List<UUID> getVanished() {
        return new ArrayList<>(hidden);
    }

    public void logout(CPlayer player) {
        hidden.removeIf(entry -> entry.equals(player.getUuid()));
    }
}
