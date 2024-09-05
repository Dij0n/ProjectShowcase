package dijon.infuseduels.guis;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CustomKitMenuHandler implements Listener {

    public static InfuseDuels plugin;
    public CustomKitMenuHandler(InfuseDuels plugin) {
        CustomKitMenuHandler.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void customKitSelection(InventoryClickEvent e){

        if(DuelMenuManager.customKitMenuMap.get(e.getWhoClicked().getUniqueId()) == null) return;
        if(!e.getInventory().equals(DuelMenuManager.customKitMenuMap.get(e.getWhoClicked().getUniqueId()))) return;

        e.setCancelled(true);

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getCurrentItem().getType().equals(Material.ARROW)){
            if(!e.getCurrentItem().hasItemMeta()) return;
            if(!e.getCurrentItem().getItemMeta().hasCustomModelData()){
                backToKitMenu((Player) e.getWhoClicked());
                return;
            }

            int index = e.getCurrentItem().getItemMeta().getCustomModelData();
            ItemStack[] rawKit = KitManager.getCustomKit((Player) e.getWhoClicked(), index).getKit();

            Player p = (Player) e.getWhoClicked();

            DuelRequestManager.getSentDuelRequest(p).setKit(rawKit);
            DuelMenuManager.openWorldMenu((Player) e.getWhoClicked());
        }

    }


    public void backToKitMenu(Player p){
        DuelMenuManager.openKitMenu(p);
    }




    @EventHandler
    public void cancelRequestInventory(InventoryCloseEvent e){
        if(DuelMenuManager.customKitMenuMap.get(e.getPlayer().getUniqueId()) == null) return;
        if(!e.getInventory().equals(DuelMenuManager.customKitMenuMap.get(e.getPlayer().getUniqueId()))) return;

        Bukkit.getScheduler().runTaskLater(InfuseDuels.plugin, ()->{

            if(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()) == null){
                return;
            }

            if(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()).getKit() == null && !DuelMenuManager.kitMenu.getViewers().contains(e.getPlayer())){
                DuelRequestManager.removeRequest(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()));
            }

        }, 2);



    }

}
