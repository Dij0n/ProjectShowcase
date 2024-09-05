package dijon.infuseduels.guis;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.kits.KitManager;
import dijon.infuserevamp.filemanagers.EffectFileManager;
import dijon.infuserevamp.filemanagers.InfuseEffect;
import dijon.infuserevamp.filemanagers.InfuseEffects;
import dijon.infuserevamp.tick.AbilityManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EffectMenuHandler implements Listener {

    public static InfuseDuels plugin;
    public EffectMenuHandler(InfuseDuels plugin) {
        EffectMenuHandler.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void primarySelection(InventoryClickEvent e){

        if(!e.getInventory().equals(DuelMenuManager.primaryEffectMenu)) return;

        e.setCancelled(true);

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.MAGENTA_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.YELLOW_STAINED_GLASS_PANE)){
            return;
        }

        Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem().getType().equals(Material.POTION)){
            int id = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getCustomModelData();
            InfuseEffect effect = InfuseEffects.getEffectList().get(id);
            EffectFileManager.setPrimary(p.getUniqueId().toString(), effect);
            EffectFileManager.setPrimarySwapCooldown(EffectFileManager.getPlayerData().get(p.getUniqueId().toString()), System.currentTimeMillis());
            EffectFileManager.setPrimaryCooldown(EffectFileManager.getPlayerData().get(p.getUniqueId().toString()), 0L);
            AbilityManager.abilityResetter(p);
            p.openInventory(DuelMenuManager.secondaryEffectMenu);
        }
    }

    @EventHandler
    public void secondarySelection(InventoryClickEvent e){

        if(!e.getInventory().equals(DuelMenuManager.secondaryEffectMenu)) return;

        e.setCancelled(true);

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.MAGENTA_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.YELLOW_STAINED_GLASS_PANE)){
            return;
        }

        Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem().getType().equals(Material.POTION)){
            int id = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getCustomModelData();
            InfuseEffect effect = InfuseEffects.getEffectList().get(id);
            EffectFileManager.setSecondary(p.getUniqueId().toString(), effect);
            EffectFileManager.setSecondarySwapCooldown(EffectFileManager.getPlayerData().get(p.getUniqueId().toString()), System.currentTimeMillis());
            EffectFileManager.setSecondaryCooldown(EffectFileManager.getPlayerData().get(p.getUniqueId().toString()), 0L);
            AbilityManager.abilityResetter(p);
            p.closeInventory();
        }
    }

    @EventHandler
    public void closeMenu(InventoryCloseEvent e){

        if(!(e.getInventory().equals(DuelMenuManager.primaryEffectMenu) || e.getInventory().equals(DuelMenuManager.secondaryEffectMenu))) return;

        Bukkit.getScheduler().runTaskLater(plugin, () ->{
            if(e.getInventory().equals(DuelMenuManager.primaryEffectMenu)){
                if(EffectFileManager.getPrimary(e.getPlayer().getUniqueId().toString()).equals(InfuseEffects.NONE)){
                    e.getPlayer().openInventory(DuelMenuManager.primaryEffectMenu);
                }
            }
            if(e.getInventory().equals(DuelMenuManager.secondaryEffectMenu)){
                if(EffectFileManager.getSecondary(e.getPlayer().getUniqueId().toString()).equals(InfuseEffects.NONE)){
                    e.getPlayer().openInventory(DuelMenuManager.secondaryEffectMenu);
                }
            }
        }, 5);

    }


}
