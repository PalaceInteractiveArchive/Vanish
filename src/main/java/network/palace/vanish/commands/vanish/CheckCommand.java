package network.palace.vanish.commands.vanish;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import network.palace.core.player.CPlayer;
import network.palace.vanish.Vanish;
import org.bukkit.ChatColor;

@CommandMeta(description = "Check if you are vanished")
public class CheckCommand extends CoreCommand {

    public CheckCommand() {
        super("check");
    }

    @Override
    protected void handleCommand(CPlayer player, String[] args) throws CommandException {
        if (Vanish.getInstance().getVanishUtil().isVanished(player.getUniqueId())) {
            player.sendMessage(ChatColor.DARK_AQUA + "You are vanished.");
        } else {
            player.sendMessage(ChatColor.DARK_AQUA + "You are visible.");
        }
    }
}
