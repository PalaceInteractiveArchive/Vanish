package network.palace.vanish;

import lombok.Getter;
import network.palace.core.Core;
import network.palace.core.message.FormattedMessage;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.core.plugin.Plugin;
import network.palace.core.plugin.PluginInfo;
import network.palace.vanish.commands.VanishCommand;
import network.palace.vanish.listeners.PlayerJoinAndLeave;
import network.palace.vanish.utils.VanishUtil;
import org.bukkit.ChatColor;

import java.util.UUID;

@PluginInfo(name = "Vanish", version = "1.1.3", depend = "Core", canReload = true, apiversion = "1.13")
public class Vanish extends Plugin {
    @Getter private static Vanish instance;
    @Getter private static VanishUtil vanishUtil;

    @Override
    protected void onPluginEnable() throws Exception {
        instance = this;
        vanishUtil = new VanishUtil();
        registerListener(new PlayerJoinAndLeave());
        registerCommand(new VanishCommand());
        FormattedMessage msg = new FormattedMessage("The Vanish plugin has been enabled. " +
                "If you would like to vanish, click this message or type /vanish.").color(ChatColor.GREEN)
                .command("/vanish");
        for (CPlayer p : Core.getPlayerManager().getOnlinePlayers()) {
            if (p.getRank().getRankId() >= Rank.SPECIALGUEST.getRankId()) {
                msg.send(p);
            }
        }
    }

    @Override
    protected void onPluginDisable() throws Exception {
        for (UUID uuid : vanishUtil.getVanished()) {
            vanishUtil.show(Core.getPlayerManager().getPlayer(uuid));
        }
    }
}
