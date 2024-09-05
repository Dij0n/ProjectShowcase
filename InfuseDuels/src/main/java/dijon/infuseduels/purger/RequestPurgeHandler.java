package dijon.infuseduels.purger;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.parties.PartyRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RequestPurgeHandler implements Listener {

    public static InfuseDuels plugin;
    public RequestPurgeHandler(InfuseDuels plugin) {
        RequestPurgeHandler.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(PartyRequestManager.getSentPartyRequest(p) != null){
            PartyRequestManager.removeRequest(PartyRequestManager.getSentPartyRequest(p));
        }
        if(DuelRequestManager.getSentDuelRequest(p) != null){
            DuelRequestManager.removeRequest(DuelRequestManager.getSentDuelRequest(p));
        }
    }

}
