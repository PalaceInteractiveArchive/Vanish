package network.palace.vanish.utils;

import network.palace.core.Core;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marc on 12/16/16.
 */
public class VanishUtil {
    private List<UUID> hidden = new ArrayList<>();
    private List<UUID> hiddenShareholders = new ArrayList<>();

    public void hide(CPlayer player) {
        hide(player, false);
    }

    public void hide(CPlayer player, boolean silent) {
        hidden.add(player.getUniqueId());
        if (!silent) player.sendMessage(ChatColor.DARK_AQUA + "You have vanished. Poof.");
        for (CPlayer onlinePlayer : Core.getPlayerManager().getOnlinePlayers()) {
            if (onlinePlayer.getRank().getRankId() < Rank.SPECIALGUEST.getRankId()) {
                onlinePlayer.hidePlayer(Vanish.getInstance(), player);
            } else if (!onlinePlayer.getUniqueId().equals(player.getUniqueId()) && !silent) {
                onlinePlayer.sendMessage(ChatColor.YELLOW + player.getName() + " has vanished. Poof.");
            }
        }
    }

    public void hideShareholder(CPlayer player) {
        hiddenShareholders.add(player.getUniqueId());
        player.sendMessage(ChatColor.DARK_AQUA + "You are now invisible. Poof.");
        for (CPlayer onlinePlayer : Core.getPlayerManager().getOnlinePlayers()) {
            if (onlinePlayer.getRank().getRankId() < Rank.SHAREHOLDER.getRankId()) {
                onlinePlayer.hidePlayer(Vanish.getInstance(), player);
            } else if (!onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                onlinePlayer.sendMessage(ChatColor.YELLOW + player.getName() + " is flying invisible.");
            }
        }
    }

    public void showShareholder(CPlayer player) {
        player.sendMessage(ChatColor.DARK_AQUA + "You have become visible.");
        hiddenShareholders.remove(player.getUniqueId());
        for (CPlayer onlinePlayer : Core.getPlayerManager().getOnlinePlayers()) {
            if (onlinePlayer.getRank().getRankId() < Rank.SHAREHOLDER.getRankId()) {
                onlinePlayer.showPlayer(Vanish.getInstance(), player);
            } else if (!onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                onlinePlayer.sendMessage(ChatColor.YELLOW + player.getName() + " is no longer flying invisible.");
            }
        }
    }

    public void show(CPlayer player) {
        hidden.remove(player.getUniqueId());
        player.sendMessage(ChatColor.DARK_AQUA + "You have become visible.");
        for (CPlayer onlinePlayer : Core.getPlayerManager().getOnlinePlayers()) {
            if (onlinePlayer.getRank().getRankId() < Rank.SPECIALGUEST.getRankId()) {
                onlinePlayer.showPlayer(Vanish.getInstance(), player);
            } else if (!onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
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
        List<UUID> list = new ArrayList<>(hidden);
        list.addAll(hiddenShareholders);
        return list;
    }

    public void login(CPlayer player) {
        boolean shareholder = player.getRank().equals(Rank.SHAREHOLDER);
        for (UUID uuid : getVanished()) {
            CPlayer tp = Core.getPlayerManager().getPlayer(uuid);
            if (tp != null) {
                if (shareholder && tp.getRank().equals(Rank.SHAREHOLDER)) continue;
                player.hidePlayer(Vanish.getInstance(), tp);
            }
        }
    }

    public void logout(CPlayer player) {
        hidden.remove(player.getUniqueId());
    }
}
