package dijon.zombiesbase.perks;

import dijon.zombiesbase.ZombiesBase;
import dijon.zombiesbase.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class PerkHandler implements Listener {

    public PerkHandler(ZombiesBase plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onFallDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getCause().equals(EntityDamageEvent.DamageCause.FALL))) return;

        Player p = (Player) e.getEntity();

        if(PlayerDataManager.hasPerk(p, PerkType.PHDFLOPPER)){
            e.setCancelled(true);
        }
    }

}
