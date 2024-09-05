package dijon.strengthsmp.crafting;

import dijon.strengthsmp.StrengthSMP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class StrengthItem {

    public static ShapedRecipe recipe;
    public static ItemStack strengthItem;

    public StrengthItem(StrengthSMP plugin){
        strengthItem = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = strengthItem.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "Strength");
            meta.setCustomModelData(5);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.BINDING_CURSE,1 , true);
            strengthItem.setItemMeta(meta);
        }
        NamespacedKey key = new NamespacedKey(plugin, "strengthitem");
        recipe = new ShapedRecipe(key, strengthItem);

        recipe.shape("ENE", "SCS", "ENE");
        recipe.setIngredient('E', Material.END_CRYSTAL);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.NETHER_STAR);
        recipe.setIngredient('C', Material.CONDUIT);

        getServer().addRecipe(recipe);
    }

}
