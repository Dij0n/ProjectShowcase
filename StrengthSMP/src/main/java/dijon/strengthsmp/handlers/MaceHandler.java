package dijon.strengthsmp.handlers;

import dijon.strengthsmp.StrengthSMP;
import dijon.strengthsmp.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

public class MaceHandler implements Listener {

    StrengthSMP plugin;
    public static int maceCount = 0;
    public static File maceFile;

    public MaceHandler(StrengthSMP plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);

        maceFile = new File(plugin.getDataFolder(), "maces.yml");

        if (!maceFile.exists()) {
            try {
                maceFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveMaceCount();
        }
        loadMaceCount();
    }

    @EventHandler
    public void onMaceCraft(CraftItemEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType().equals(Material.MACE)){
            if(maceCount >= 3){
                e.getWhoClicked().sendMessage(ChatColor.RED + "No more maces can be crafted in this world");
                e.setCancelled(true);
            }else{
                Player p = (Player) e.getWhoClicked();
                if(maceCount == 0){
                    p.sendMessage(ChatColor.GREEN + "⚒ You have crafted the first mace! This mace can be enchanted");
                    p.playSound(p, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 10, 1);
                    ItemMeta meta = e.getCurrentItem().getItemMeta();
                    meta.setCustomModelData(121);
                    e.getCurrentItem().setItemMeta(meta);
                    maceCount = 1;
                    return;
                }
                if(maceCount == 1){
                    p.sendMessage(ChatColor.GREEN + "⚒ You have crafted the second mace! This mace cannot be enchanted");
                    p.playSound(p, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 10, 1);
                    maceCount = 2;
                    return;
                }
                if(maceCount == 2){
                    p.sendMessage(ChatColor.GREEN + "⚒ You have crafted the third mace! This mace cannot be enchanted");
                    p.playSound(p, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 10, 1);
                    for(Player other : Bukkit.getOnlinePlayers()){
                        other.sendMessage(ChatColor.RED + "⚒ All maces have been crafted! Good luck!");
                        other.playSound(p, Sound.ITEM_TRIDENT_THUNDER, 10, 1);
                    }
                    maceCount = 3;
                }
            }
        }
    }

    @EventHandler
    public void onMaceEnchant(EnchantItemEvent e){
        if(!e.getItem().getType().equals(Material.MACE)) return;
        if(!e.getItem().getItemMeta().hasCustomModelData()){
            e.getEnchanter().sendMessage(ChatColor.RED + "⚒ This mace cannot be enchanted");
            e.getEnchanter().playSound(e.getEnchanter(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 10, 1);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMaceAnvil(InventoryClickEvent e){

        if(!e.getInventory().getType().equals(InventoryType.ANVIL)) return;
        AnvilInventory anv = (AnvilInventory) e.getInventory();
        if(e.getRawSlot() == 0 || e.getRawSlot() == 1) return;
        if(anv.getResult() == null) return;
        if(anv.getResult().getType().equals(Material.MACE)){
            if(!anv.getResult().getItemMeta().hasCustomModelData()){
                for(HumanEntity he : e.getViewers()){
                    Player p = (Player) he;
                    p.sendMessage(ChatColor.RED + "⚒ This mace cannot be enchanted");
                    p.playSound(p, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 10, 1);
                }
                e.setCancelled(true);
            }
        }
    }

    public static void loadMaceCount(){
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(maceFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        maceCount = (int) config.get("Maces");

        Bukkit.getLogger().info("Loading mace count " + maceCount);
    }

    public static void saveMaceCount(){
        YamlConfiguration config = new YamlConfiguration();

        config.set("Maces", maceCount);

        try {
            config.save(maceFile);
            Bukkit.getLogger().info("Saved Mace count " + maceCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
