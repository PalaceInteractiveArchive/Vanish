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
        hidden.remove(player.getUuid());
        hidden.add(player.getUuid());
        player.sendMessage(ChatColor.DARK_AQUA + "You have vanished. Poof.");
        for (CPlayer tp : Core.getPlayerManager().getOnlinePlayers()) {
            if (tp.getRank().getRankId() < Rank.SPECIALGUEST.getRankId()) {
                tp.hidePlayer(player);
            } else if (!tp.getUuid().equals(player.getUuid())) {
                tp.sendMessage(ChatColor.YELLOW + player.getName() + " has vanished. Poof.");
            }
        }
    }

    public void show(CPlayer player) {
        hidden.remove(player.getUuid());
        player.sendMessage(ChatColor.DARK_AQUA + "You have become visible.");
        for (CPlayer tp : Core.getPlayerManager().getOnlinePlayers()) {
            tp.showPlayer(player);
            if (player.getRank().getRankId() >= Rank.SPECIALGUEST.getRankId() &&
                    !tp.getUuid().equals(player.getUuid())) {
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
