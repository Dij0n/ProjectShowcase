package dijon.infuseduels.guis;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.kits.KitManager;
import dijon.infuseduels.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DuelMenuHandler implements Listener {

    public static InfuseDuels plugin;
    public DuelMenuHandler(InfuseDuels plugin) {
        DuelMenuHandler.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void kitSelection(InventoryClickEvent e){

        if(!e.getInventory().equals(DuelMenuManager.kitMenu)) return;

        e.setCancelled(true);

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.MAGENTA_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.YELLOW_STAINED_GLASS_PANE)){
            return;
        }

        if(e.getCurrentItem().getType().equals(Material.CRAFTING_TABLE)){
            DuelMenuManager.openCustomKitMenu((Player) e.getWhoClicked());
            return;
        }

        ItemStack[] kit = KitManager.getKit(String.valueOf(e.getCurrentItem().getType()));
        DuelRequestManager.getSentDuelRequest((Player) e.getWhoClicked()).setKit(kit);

        e.getWhoClicked().closeInventory();

        DuelMenuManager.openWorldMenu((Player) e.getWhoClicked());
    }

    @EventHandler
    public void worldSelection(InventoryClickEvent e){

        Player p = (Player) e.getWhoClicked();

        if(!e.getInventory().equals(DuelMenuManager.worldMenu)) return;

        e.setCancelled(true);

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.MAGENTA_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.YELLOW_STAINED_GLASS_PANE)){
            return;
        }

        String arenaName = WorldManager.convertItemToArena(String.valueOf(e.getCurrentItem().getType()));

        if(!WorldManager.getArenaAvailability(arenaName)){
            p.sendMessage(ChatColor.RED + "This arena is currently full. Try another arena");
            e.setCancelled(true);
            return;
        }

        World arena = WorldManager.getWorldByArena(arenaName);
        DuelRequestManager.getSentDuelRequest((Player) e.getWhoClicked()).setWorld(arena);
        DuelRequestManager.getSentDuelRequest((Player) e.getWhoClicked()).setArenaName(arenaName);
        e.getWhoClicked().closeInventory();
        DuelMenuManager.openSettingsMenu(p);

    }

    @EventHandler
    public void cancelRequestInventory(InventoryCloseEvent e){
        if(e.getInventory().equals(DuelMenuManager.kitMenu)){

            Bukkit.getScheduler().runTaskLater(InfuseDuels.plugin, ()->{

                if(DuelMenuManager.customKitMenuMap.get(e.getPlayer().getUniqueId()) == null){
                    if(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()).getKit() == null){
                        DuelRequestManager.removeRequest(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()));
                    }
                }else{
                    Inventory inv = DuelMenuManager.customKitMenuMap.get(e.getPlayer().getUniqueId());
                    if(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()).getKit() == null && !inv.getViewers().contains(e.getPlayer())){
                        DuelRequestManager.removeRequest(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()));
                    }
                }

            }, 2);
        }
        if(e.getInventory().equals(DuelMenuManager.worldMenu)){
            if(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()).getWorld() == null){
                DuelRequestManager.removeRequest(DuelRequestManager.getSentDuelRequest((Player) e.getPlayer()));
            }
        }

    }




}
