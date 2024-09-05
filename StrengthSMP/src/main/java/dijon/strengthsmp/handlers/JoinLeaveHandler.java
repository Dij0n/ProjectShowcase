package dijon.strengthsmp.handlers;

import dijon.strengthsmp.StrengthSMP;
import dijon.strengthsmp.data.PlayerData;
import dijon.strengthsmp.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class JoinLeaveHandler implements Listener {

    StrengthSMP plugin;

    public JoinLeaveHandler(StrengthSMP plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Bukkit.getLogger().info("Adding player");

        if(!PlayerDataManager.masterPlayerData.containsKey(e.getPlayer().getUniqueId())){
            Bukkit.getLogger().info("Adding new player " + e.getPlayer().getName());
            PlayerDataManager.addPlayer(e.getPlayer());
            PlayerDataManager.setPlayer(e.getPlayer());
            Bukkit.getLogger().info("With strength " + PlayerDataManager.getStrength(e.getPlayer()));
        }else{
            PlayerDataManager.setPlayer(e.getPlayer());
        }

        PlayerDataManager.savePlayers();
        e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1 + PlayerDataManager.getStrength(e.getPlayer()));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        PlayerDataManager.savePlayers();
    }

    @EventHandler
    public void maceBreaking(EntityDamageEvent e){
        if(e.getEntity().getType().equals(EntityType.ITEM)){
            Item item = (Item) e.getEntity();
            if(item.getItemStack().getType() == Material.MACE){
                e.setCancelled(true);
            }
        }
    }
}
