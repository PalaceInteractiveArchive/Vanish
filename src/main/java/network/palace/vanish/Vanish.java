package network.palace.vanish;

import lombok.Getter;
import network.palace.core.plugin.Plugin;
import network.palace.core.plugin.PluginInfo;
import network.palace.vanish.commands.Commandvanish;
import network.palace.vanish.listeners.PlayerJoinAndLeave;
import network.palace.vanish.utils.VanishUtil;

@PluginInfo(name = "Vanish")
public class Vanish extends Plugin {
    private static Vanish instance;
    @Getter
    private VanishUtil vanishUtil;

    @Override
    protected void onPluginEnable() throws Exception {
        instance = this;
        vanishUtil = new VanishUtil();
        registerListener(new PlayerJoinAndLeave());
        registerCommand(new Commandvanish());
    }

    @Override
    protected void onPluginDisable() throws Exception {
    }

    public static Vanish getInstance() {
        return instance;
    }
}
