package dijon.zombiesbase.shooting.listeners;

import dijon.zombiesbase.ZombiesBase;
import dijon.zombiesbase.playerdata.PlayerDataManager;
import dijon.zombiesbase.playerdata.Status;
import dijon.zombiesbase.shooting.GunType;
import dijon.zombiesbase.shooting.Shooter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class ShootHandler implements Listener {

    public static HashMap<UUID, Shooter> holdMap = new HashMap<>();
    public ShootHandler(ZombiesBase plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void detectShoot(PlayerInteractEvent e){

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){

            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            if (!GunType.isGun(item) && /*----TEMP*/ !item.getType().equals(Material.PRISMARINE_CRYSTALS)/*TEMP----*/) return;

            if(PlayerDataManager.getStatus(e.getPlayer()).equals(Status.RELOADING)) return;

            PlayerDataManager.setStatus(e.getPlayer(), Status.SHOOTING);
            refreshHolder(e.getPlayer());
            addHolder(e.getPlayer());

        }
    }

    public void refreshHolder(Player p){
        if(!holdMap.containsKey(p.getUniqueId())) return;
        holdMap.get(p.getUniqueId()).refresh();
    }

    public void addHolder(Player p){
        if(holdMap.containsKey(p.getUniqueId())) return;
        holdMap.put(p.getUniqueId(), new Shooter(p));
    }


}
