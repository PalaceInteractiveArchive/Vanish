package network.palace.vanish.listeners;

import network.palace.core.events.PlayerToggleAllowFlightEvent;
import network.palace.core.player.CPlayer;
import network.palace.core.player.Rank;
import network.palace.vanish.Vanish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FlightListener implements Listener {

    @EventHandler
    public void onPlayerToggleAllowFlight(PlayerToggleAllowFlightEvent event) {
        CPlayer player = event.getPlayer();
        if (!player.getRank().equals(Rank.SHAREHOLDER)) return;
        if (event.isFlightState()) {
            Vanish.getVanishUtil().hideShareholder(player);
        } else {
            Vanish.getVanishUtil().showShareholder(player);
        }
    }
}
