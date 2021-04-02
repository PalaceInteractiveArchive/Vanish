package network.palace.vanish.commands;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import network.palace.vanish.commands.vanish.CheckCommand;
import network.palace.vanish.commands.vanish.ListCommand;

/**
 * Created by Marc on 12/16/16.
 */
@CommandMeta(description = "Vanish from the view of players", aliases = "v", rank = Rank.VIP)
public class VanishCommand extends CoreCommand {

    public VanishCommand() {
        super("vanish");
        registerSubCommand(new ListCommand());
        registerSubCommand(new CheckCommand());
    }

    @Override
    protected void handleCommand(CPlayer player, String[] args) throws CommandException {
        Vanish.getVanishUtil().toggle(player);
    }
}
