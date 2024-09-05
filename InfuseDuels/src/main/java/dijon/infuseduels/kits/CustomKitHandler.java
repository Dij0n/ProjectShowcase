package dijon.infuseduels.kits;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.guis.DuelMenuHandler;
import dijon.infuseduels.guis.DuelMenuManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.net.http.WebSocket;

public class CustomKitHandler implements Listener {

    public CustomKitHandler(InfuseDuels plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onChestClick (InventoryClickEvent e){
        if(!e.getWhoClicked().getWorld().equals(Bukkit.getWorld("ckitworld"))){
            return;
        }

        if(e.getWhoClicked().isOp()){
            return;
        }

        if(e.getClickedInventory().getType().equals(InventoryType.CHEST)){
            e.setCancelled(true);
            if(!e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)){
                e.setCursor(e.getCurrentItem());
            }
        }


    }



}
