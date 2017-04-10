package network.palace.vanish.commands;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CommandPermission;
import network.palace.core.command.CoreCommand;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.UUID;

/**
 * Created by Marc on 12/16/16.
 */
@CommandMeta(description = "Vanish from the view of settlers", aliases = "v")
@CommandPermission(rank = Rank.SPECIALGUEST)
public class CommandVanish extends CoreCommand {

    public CommandVanish() {
        super("vanish");
    }

    @Override
    protected void handleCommand(CPlayer player, String[] args) throws CommandException {
        if (args.length == 0) {
            Vanish.getInstance().getVanishUtil().toggle(player);
            return;
        }
        switch (args[0].toLowerCase()) {
            case "list":
                List<UUID> vanished = Vanish.getInstance().getVanishUtil().getVanished();
                StringBuilder list = new StringBuilder();
                Bukkit.getOnlinePlayers().stream().filter(tp -> vanished.contains(tp.getUniqueId())).forEach(tp -> {
                    if (list.length() > 0) {
                        list.append(ChatColor.DARK_AQUA);
                        list.append(", ");
                    }
                    list.append(ChatColor.AQUA);
                    list.append(tp.getName());
                });
                list.insert(0, "Vanished: ");
                list.insert(0, ChatColor.DARK_AQUA);
                player.sendMessage(list.toString());
                break;
            case "check":
                if (Vanish.getInstance().getVanishUtil().isVanished(player.getUniqueId())) {
                    player.sendMessage(ChatColor.DARK_AQUA + "You are vanished.");
                } else {
                    player.sendMessage(ChatColor.DARK_AQUA + "You are visible.");
                }
                break;
            default:
                player.sendMessage(ChatColor.GREEN + "Vanish Commands");
                player.sendMessage(ChatColor.AQUA + "- /vanish " + ChatColor.GREEN + "- Vanish or unvanish yourself");
                player.sendMessage(ChatColor.AQUA + "- /vanish check " + ChatColor.GREEN + "- Check if you are vanished");
                player.sendMessage(ChatColor.AQUA + "- /vanish list " + ChatColor.GREEN + "- List all vanished players");
                break;
        }
    }
}
