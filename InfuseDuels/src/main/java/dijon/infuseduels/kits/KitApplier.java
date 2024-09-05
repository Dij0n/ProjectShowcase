package dijon.infuseduels.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitApplier {

    public static void applyKit(Player p, ItemStack[] kit){
        p.getInventory().clear();

        ItemStack[] items = Arrays.copyOfRange(kit, 0, kit.length - 5);
        ItemStack[] armor = Arrays.copyOfRange(kit, kit.length - 5, kit.length - 1);
        ItemStack offHand = kit[kit.length - 1];

        p.getInventory().setContents(items);
        p.getInventory().setArmorContents(armor);
        p.getInventory().setItemInOffHand(offHand);
    }

}
