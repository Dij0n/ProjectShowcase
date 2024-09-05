package dijon.strengthsmp.handlers.item;

import dijon.strengthsmp.StrengthSMP;
import dijon.strengthsmp.data.PlayerDataManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHandler implements Listener {

    StrengthSMP plugin;

    public ItemHandler(StrengthSMP plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if(!e.getAction().isRightClick()) return;

        if (item != null && item.getType() == Material.GHAST_TEAR && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if(meta == null) return;
            if(meta.getCustomModelData() == 5) {

                if(PlayerDataManager.getStrength(player) >= 3){
                    player.sendMessage(ChatColor.RED + "You cannot go above +3 strength with this item");
                    return;
                }
                PlayerDataManager.incStrength(player);

                item.setAmount(item.getAmount() - 1);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                player.getWorld().spawnParticle(Particle.DUST, player.getLocation(), 100, 0.5D, 0.5D, 0.5D, new Particle.DustOptions(Color.GREEN, 1.0F));
            }
        }
    }

}
