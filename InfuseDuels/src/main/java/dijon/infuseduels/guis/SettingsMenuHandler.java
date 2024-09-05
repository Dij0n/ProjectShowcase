package dijon.infuseduels.guis;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.Duel;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.kits.KitManager;
import dijon.infuseduels.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SettingsMenuHandler implements Listener {

    public static HashMap<UUID, boolean[]> settingsMap = new HashMap<>();

    public static InfuseDuels plugin;
    public SettingsMenuHandler(InfuseDuels plugin) {
        SettingsMenuHandler.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void settingSelection(InventoryClickEvent e){

        if(DuelMenuManager.settingsMenuMap.get(e.getWhoClicked().getUniqueId()) == null) return;
        if(!e.getInventory().equals(DuelMenuManager.settingsMenuMap.get(e.getWhoClicked().getUniqueId()))) return;

        e.setCancelled(true);

        if(e.getCurrentItem() == null){
            return;
        }

        Player p = (Player) e.getWhoClicked();

        flipSetting(p, e.getCurrentItem().getType());


        if(e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)){

            DuelRequestManager.getSentDuelRequest((Player) e.getWhoClicked()).setSettings(settingsMap.get(p.getUniqueId()));
            DuelRequestManager.sendRequest(DuelRequestManager.getSentDuelRequest((Player) e.getWhoClicked()));
            e.getWhoClicked().closeInventory();

        }

    }


    public void flipSetting(Player p, Material item){
        if(!settingsMap.containsKey(p.getUniqueId())){
            boolean[] settingEntry = {false, false, false, false, false, false, false};
            settingsMap.put(p.getUniqueId(), settingEntry);
        }

        switch (item.toString()){
            case "GLISTERING_MELON_SLICE":
                settingsMap.get(p.getUniqueId())[0] = !settingsMap.get(p.getUniqueId())[0];
                flipEnchantment(p, 11);
                break;
            case "ENDER_PEARL":
                settingsMap.get(p.getUniqueId())[1] = !settingsMap.get(p.getUniqueId())[1];
                flipEnchantment(p, 12);
                break;
            case "DIAMOND_PICKAXE":
                settingsMap.get(p.getUniqueId())[2] = !settingsMap.get(p.getUniqueId())[2];
                flipEnchantment(p, 13);
                break;
            case "GHAST_TEAR":
                settingsMap.get(p.getUniqueId())[3] = !settingsMap.get(p.getUniqueId())[3];
                flipEnchantment(p, 14);
                break;
            case "TNT":
                settingsMap.get(p.getUniqueId())[4] = !settingsMap.get(p.getUniqueId())[4];
                flipEnchantment(p, 15);
                break;
            case "BREWING_STAND":
                settingsMap.get(p.getUniqueId())[5] = !settingsMap.get(p.getUniqueId())[5];
                flipEnchantment(p, 10);
                break;
            case "SHIELD":
                settingsMap.get(p.getUniqueId())[6] = !settingsMap.get(p.getUniqueId())[6];
                flipEnchantment(p, 16);
                break;
        }
    }

    public void flipEnchantment(Player p, int slot){
        ItemStack item = DuelMenuManager.settingsMenuMap.get(p.getUniqueId()).getItem(slot);

        if(item.getEnchantments().isEmpty()){
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
            ItemMeta meta = item.getItemMeta();
            String ogName = meta.getDisplayName();
            ogName = ogName.substring(0, ogName.length()-3) + "ON";
            ogName = "§a" + ogName.substring(2);
            meta.setDisplayName(ogName);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            item.setItemMeta(meta);
        }else{
            item.removeEnchantment(Enchantment.BINDING_CURSE);
            ItemMeta meta = item.getItemMeta();
            String ogName = meta.getDisplayName();
            ogName = ogName.substring(0, ogName.length()-2) + "OFF";
            ogName = "§c" + ogName.substring(2);
            meta.setDisplayName(ogName);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            item.setItemMeta(meta);
        }

        DuelMenuManager.settingsMenuMap.get(p.getUniqueId()).setItem(slot, item);
    }





    @EventHandler
    public void cancelRequestInventory(InventoryCloseEvent e){
        if(DuelMenuManager.settingsMenuMap.get(e.getPlayer().getUniqueId()) == null) return;
        if(!e.getInventory().equals(DuelMenuManager.settingsMenuMap.get(e.getPlayer().getUniqueId()))) return;

        Bukkit.getScheduler().runTaskLater(InfuseDuels.plugin, ()->{

            Player p = (Player) e.getPlayer();

            if(DuelRequestManager.getSentDuelRequest(p) == null){
                return;
            }

            if(DuelRequestManager.getSentDuelRequest(p).getSettings() == null){
                WorldManager.freeUpWorld(DuelRequestManager.getSentDuelRequest(p).getWorld().getName());
                DuelRequestManager.removeRequest(DuelRequestManager.getSentDuelRequest(p));
            }



        }, 2);



    }

}
