package dijon.strengthsmp.handlers.item;

import dijon.strengthsmp.StrengthSMP;
import dijon.strengthsmp.crafting.StrengthItem;
import dijon.strengthsmp.data.PlayerData;
import dijon.strengthsmp.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.net.http.WebSocket;

public class CraftingHandler implements Listener {

    StrengthSMP plugin;

    public CraftingHandler(StrengthSMP plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType().equals(Material.GHAST_TEAR)){
            int strength = PlayerDataManager.getStrength((Player) e.getWhoClicked());
            if(strength != 1){
                e.getWhoClicked().sendMessage(ChatColor.RED + "You can only craft Strength on +1");
                e.setCancelled(true);
            }
        }



    }

}
