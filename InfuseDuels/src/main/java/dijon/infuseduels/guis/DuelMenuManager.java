package dijon.infuseduels.guis;

import dijon.infuseduels.kits.CustomKit;
import dijon.infuseduels.kits.KitManager;
import dijon.infuserevamp.itemsparticles.EffectItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class DuelMenuManager {

    public static Inventory kitMenu = Bukkit.getServer().createInventory(null, 27, "KitMenu");
    public static Inventory worldMenu = Bukkit.getServer().createInventory(null, 27, "WorldMenu");
    public static Inventory primaryEffectMenu = Bukkit.getServer().createInventory(null, 27, "PrimaryEffect");
    public static Inventory secondaryEffectMenu = Bukkit.getServer().createInventory(null, 27, "SecondaryEffect");
    public static HashMap<UUID, Inventory> customKitMenuMap = new HashMap<>();
    public static HashMap<UUID, Inventory> settingsMenuMap = new HashMap<>();

    public DuelMenuManager(){
        initializeMenus();
    }

    public static void openKitMenu(Player p){
        p.openInventory(kitMenu);
    }

    public static void openWorldMenu(Player p){
        p.openInventory(worldMenu);
    }

    public static void openCustomKitMenu(Player p){
        customKitMenuMap.put(p.getUniqueId(), Bukkit.getServer().createInventory(null, 27, "CustomKitMenu"));

        if(KitManager.getCustomKitList(p) == null) return;

        for(int i=0;i<KitManager.getCustomKitList(p).size();i++){
            ItemStack icon = new ItemStack(Material.ARROW);
            ItemMeta meta = icon.getItemMeta();
            meta.setCustomModelData(i);
            meta.setDisplayName(KitManager.getCustomKitList(p).get(i).getName());
            icon.setItemMeta(meta);

            customKitMenuMap.get(p.getUniqueId()).addItem(icon);
        }

        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta meta = backArrow.getItemMeta();
        meta.setDisplayName("Back");
        backArrow.setItemMeta(meta);
        customKitMenuMap.get(p.getUniqueId()).setItem(18, backArrow);
        p.openInventory(customKitMenuMap.get(p.getUniqueId()));
    }

    public static void openSettingsMenu(Player p){
        settingsMenuMap.put(p.getUniqueId(), Bukkit.getServer().createInventory(null, 27, "SettingsMenu"));

        for(int i=0;i<10;i++){
            settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        ItemStack[] items = getInitialSettings(p);

        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[5]);
        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[0]);
        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[1]);
        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[2]);
        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[3]);
        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[4]);
        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),items[6]);

        for(int i=0;i<5;i++){
            settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        ItemStack startDuel = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta meta = startDuel.getItemMeta();
        meta.setDisplayName("Start Duel");
        startDuel.setItemMeta(meta);

        settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),startDuel);

        for(int i=0;i<4;i++){
            settingsMenuMap.get(p.getUniqueId()).setItem(settingsMenuMap.get(p.getUniqueId()).firstEmpty(),new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }

        p.openInventory(settingsMenuMap.get(p.getUniqueId()));
    }

    public static ItemStack[] getInitialSettings(Player p){
        ItemStack[] items = {new ItemStack(Material.GLISTERING_MELON_SLICE), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.GHAST_TEAR), new ItemStack(Material.TNT), new ItemStack(Material.BREWING_STAND), new ItemStack(Material.SHIELD)};

        ItemMeta meta = items[0].getItemMeta();
        meta.setDisplayName("§cHealth Regen | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[0].setItemMeta(meta);

        meta = items[1].getItemMeta();
        meta.setDisplayName("§cPearl Cooldown | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[1].setItemMeta(meta);

        meta = items[2].getItemMeta();
        meta.setDisplayName("§cBlock Breaking | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[2].setItemMeta(meta);

        meta = items[3].getItemMeta();
        meta.setDisplayName("§cItems Drop | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[3].setItemMeta(meta);

        meta = items[4].getItemMeta();
        meta.setDisplayName("§cAuto-Ignition | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[4].setItemMeta(meta);

        meta = items[5].getItemMeta();
        meta.setDisplayName("§cInfuse Effects | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[5].setItemMeta(meta);

        meta = items[6].getItemMeta();
        meta.setDisplayName("§cFriendly Fire | OFF");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        items[6].setItemMeta(meta);


        if(SettingsMenuHandler.settingsMap.get(p.getUniqueId()) == null){
            boolean[] settingEntry = {false, false, false, false, false, false, false};
            SettingsMenuHandler.settingsMap.put(p.getUniqueId(), settingEntry);
        }

        boolean[] initialSettings = SettingsMenuHandler.settingsMap.get(p.getUniqueId());

        for(int i=0;i<7;i++){
            if(initialSettings[i]){
                items[i].addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);

                meta = items[i].getItemMeta();
                String ogName = meta.getDisplayName();
                ogName = ogName.substring(0, ogName.length()-3) + "ON";
                ogName = "§a" + ogName.substring(2);
                meta.setDisplayName(ogName);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
                items[i].setItemMeta(meta);
            }
        }

        return items;

    }


    public void initializeMenus(){

        //KITS MENU

        ItemStack forwardArrow = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta meta = forwardArrow.getItemMeta();
        meta.setDisplayName("§b§l§oCustom Kits");
        forwardArrow.setItemMeta(meta);

        ItemStack swordItem = new ItemStack(Material.DIAMOND_SWORD);
        meta = swordItem.getItemMeta();
        meta.setDisplayName("§b§l§oSword Kit");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        swordItem.setItemMeta(meta);

        ItemStack axeItem = new ItemStack(Material.DIAMOND_AXE);
        meta = axeItem.getItemMeta();
        meta.setDisplayName("§b§l§oAxe Kit");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        axeItem.setItemMeta(meta);

        ItemStack uhcItem = new ItemStack(Material.GOLDEN_APPLE);
        meta = uhcItem.getItemMeta();
        meta.setDisplayName("§b§l§oUHC Kit");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        uhcItem.setItemMeta(meta);

        ItemStack potionItem = new ItemStack(Material.POTION);
        meta = potionItem.getItemMeta();
        meta.setDisplayName("§b§l§oInfuse Kit");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        potionItem.setItemMeta(meta);

        ItemStack arena1 = new ItemStack(Material.PRISMARINE);
        meta = arena1.getItemMeta();
        meta.setDisplayName("§b\uD83E\uDE9E Mirror Cavern §r§b\uD83E\uDE9E");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        arena1.setItemMeta(meta);

        ItemStack arena2 = new ItemStack(Material.STRIPPED_CRIMSON_STEM);
        meta = arena2.getItemMeta();
        meta.setDisplayName("§7\uD83C\uDFDB Colosseum §r§7\uD83C\uDFDB");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        arena2.setItemMeta(meta);

        ItemStack arena3 = new ItemStack(Material.MAGMA_BLOCK);
        meta = arena3.getItemMeta();
        meta.setDisplayName("§c\uD83C\uDF0B Magma Pillars §r§c\uD83C\uDF0B");
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        arena3.setItemMeta(meta);

        ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        meta = blackPane.getItemMeta();
        meta.setDisplayName("");
        blackPane.setItemMeta(meta);

        ItemStack magPane = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        meta = magPane.getItemMeta();
        meta.setDisplayName("");
        magPane.setItemMeta(meta);

        ItemStack yelPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        meta = yelPane.getItemMeta();
        meta.setDisplayName("");
        yelPane.setItemMeta(meta);

        for(int i=0;i<9;i++){
            kitMenu.setItem(kitMenu.firstEmpty(), blackPane);
        }

        kitMenu.setItem(kitMenu.firstEmpty(), swordItem);
        kitMenu.setItem(kitMenu.firstEmpty(),blackPane);
        kitMenu.setItem(kitMenu.firstEmpty(),axeItem);
        kitMenu.setItem(kitMenu.firstEmpty(),blackPane);
        kitMenu.setItem(kitMenu.firstEmpty(),uhcItem);
        kitMenu.setItem(kitMenu.firstEmpty(),blackPane);
        kitMenu.setItem(kitMenu.firstEmpty(),potionItem);
        kitMenu.setItem(kitMenu.firstEmpty(),blackPane);
        kitMenu.setItem(kitMenu.firstEmpty(),forwardArrow);

        for(int i=0;i<9;i++){
            kitMenu.setItem(kitMenu.firstEmpty(),blackPane);
        }

        //WORLD MENU

        for(int i=0;i<12;i++){
            worldMenu.setItem(worldMenu.firstEmpty(),blackPane);
        }

        worldMenu.setItem(worldMenu.firstEmpty(),arena1);
        worldMenu.setItem(worldMenu.firstEmpty(),arena2);
        worldMenu.setItem(worldMenu.firstEmpty(),arena3);

        for(int i=0;i<12;i++){
            worldMenu.setItem(worldMenu.firstEmpty(),blackPane);
        }

        //PRIMARY MENU

        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),blackPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.HEART);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.HASTE);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.REGEN);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),blackPane);

        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),blackPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.INVIS);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.FROST);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),blackPane);

        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),blackPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.THUNDER);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.STRENGTH);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),EffectItemStacks.FEATHER);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),magPane);
        primaryEffectMenu.setItem(primaryEffectMenu.firstEmpty(),blackPane);

        //SECONDARY MENU

        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),blackPane);
        for(int i=0;i<7;i++){
            secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),yelPane);
        }
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),blackPane);

        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),blackPane);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),EffectItemStacks.EMERALD);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),yelPane);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),EffectItemStacks.FIRE);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),yelPane);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),EffectItemStacks.SPEED);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),yelPane);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),EffectItemStacks.OCEAN);
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),blackPane);

        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),blackPane);
        for(int i=0;i<7;i++){
            secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),yelPane);
        }
        secondaryEffectMenu.setItem(secondaryEffectMenu.firstEmpty(),blackPane);
    }




}
