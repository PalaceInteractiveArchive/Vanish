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
        for (CPlayer tp : Core.getPlayerManager().getOnlinePlayers()) {
            if (tp.getRank().getRankId() < Rank.SPECIALGUEST.getRankId()) {
                tp.hidePlayer(player);
            } else if (!tp.getUniqueId().equals(player.getUniqueId()) && !silent) {
                tp.sendMessage(ChatColor.YELLOW + player.getName() + " has vanished. Poof.");
                tp.showPlayer(player);
            }
        }
    }

    public void show(CPlayer player) {
        hidden.remove(player.getUniqueId());
        player.sendMessage(ChatColor.DARK_AQUA + "You have become visible.");
        for (CPlayer tp : Core.getPlayerManager().getOnlinePlayers()) {
            tp.showPlayer(player);
            if (tp.getRank().getRankId() >= Rank.SPECIALGUEST.getRankId() &&
                    !tp.getUniqueId().equals(player.getUniqueId())) {
                tp.sendMessage(ChatColor.YELLOW + player.getName() + " has become visible.");
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

    public void logout(UUID uuid) {
        hidden.remove(uuid);
    }
}
