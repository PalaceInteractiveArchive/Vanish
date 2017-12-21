package network.palace.vanish.commands.vanish;

import network.palace.core.Core;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import network.palace.core.player.CPlayer;
import network.palace.vanish.Vanish;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.UUID;

@CommandMeta(description = "List all vanished players")
public class ListCommand extends CoreCommand {

    public ListCommand() {
        super("list");
    }

    @Override
    protected void handleCommand(CPlayer player, String[] args) throws CommandException {
        List<UUID> vanished = Vanish.getInstance().getVanishUtil().getVanished();
        StringBuilder list = new StringBuilder();
        Core.getPlayerManager().getOnlinePlayers().stream().filter(tp -> vanished.contains(tp.getUniqueId())).forEach(tp -> {
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
    }
}
