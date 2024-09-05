package dijon.strengthsmp.crafting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathMaceItem {

    public static ItemStack deathMaceItem;

    public DeathMaceItem(){
        deathMaceItem = new ItemStack(Material.MACE);
        Damageable meta = (Damageable) deathMaceItem.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "DeathMace");
        meta.addEnchant(Enchantment.WIND_BURST,2 , true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.setDamage(485);
        deathMaceItem.setItemMeta(meta);
    }

}
