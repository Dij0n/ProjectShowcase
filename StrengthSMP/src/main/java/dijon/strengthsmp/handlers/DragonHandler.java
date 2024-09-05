package dijon.strengthsmp.handlers;

import dijon.strengthsmp.StrengthSMP;
import dijon.strengthsmp.data.PlayerData;
import dijon.strengthsmp.data.PlayerDataManager;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class DragonHandler implements Listener {

    StrengthSMP plugin;

    public DragonHandler(StrengthSMP plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //GETTING IT

    @EventHandler
    public void itemPickUp(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player p){
            if(e.getItem().getItemStack().getType().equals(Material.DRAGON_EGG)){
                PlayerDataManager.dragonActivation(p);
            }
        }
    }

    @EventHandler
    public void itemInvMove(InventoryCloseEvent e){
        PlayerInventory inven = e.getPlayer().getInventory();
        Player p = (Player) e.getPlayer();
        if(inven.contains(Material.DRAGON_EGG) && !PlayerDataManager.getEgg(p)){
            PlayerDataManager.dragonActivation(p);
        }
        if(!inven.contains(Material.DRAGON_EGG) && PlayerDataManager.getEgg(p)){
            PlayerDataManager.dragonLoss((Player) e.getPlayer());
        }
    }

    //LOSING IT

    @EventHandler
    public void itemDrop(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().getType().equals(Material.DRAGON_EGG)){
            PlayerDataManager.dragonLoss(e.getPlayer());
        }
    }

    @EventHandler
    public void itemDie(PlayerDeathEvent e){
        if(PlayerDataManager.getEgg(e.getPlayer())){
            PlayerDataManager.dragonLoss(e.getPlayer());
        }
    }

    @EventHandler
    public void itemJoin(PlayerJoinEvent e){
        Inventory inv = e.getPlayer().getInventory();
        if(!inv.contains(Material.DRAGON_EGG) && PlayerDataManager.getEgg(e.getPlayer())){
            PlayerDataManager.dragonLoss(e.getPlayer());
        }
    }

}
