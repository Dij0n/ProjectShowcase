package dijon.infuseduels.kits;

import org.bukkit.inventory.ItemStack;

public class CustomKit {

    ItemStack[] kit;
    String name;
    ItemStack icon;

    public CustomKit(ItemStack[] kit, String name, ItemStack icon) {
        this.kit = kit;
        this.name = name;
        this.icon = icon;
    }

    public ItemStack[] getKit() {
        return kit;
    }

    public void setKit(ItemStack[] kit) {
        this.kit = kit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }
}
