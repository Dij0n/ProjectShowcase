package dijon.infuseduels.kits;

import dijon.infuseduels.InfuseDuels;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class KitManager {

    public static ItemStack[] SWORD;
    public static ItemStack[] AXE;
    public static ItemStack[] APPLE;
    public static ItemStack[] POTION;
    public static HashMap<String, ItemStack[]> kitMap = new HashMap<>();
    public static HashMap<UUID, ArrayList<CustomKit>> customKitMap =  new HashMap<>();

    public static File customKitFile;

    public KitManager(InfuseDuels plugin){
        SWORD = iSword();
        AXE = iAxe();
        APPLE = iApple();
        POTION = iPotion();

        kitMap.put("DIAMOND_SWORD", SWORD);
        kitMap.put("DIAMOND_AXE", AXE);
        kitMap.put("GOLDEN_APPLE", APPLE);
        kitMap.put("POTION", POTION);

        customKitFile = new File(plugin.getDataFolder(), "customkits.yml");

        if (!customKitFile.exists()) {
            try {
                customKitFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadCustomKits();

    }

    public static ItemStack[] getKit(String s){
        return kitMap.get(s);
    }

    //CUSTOM KITS
    public static void addCustomKit(Player p, ItemStack[] kit, String name, ItemStack icon){
        if(!customKitMap.containsKey(p.getUniqueId())){
            customKitMap.put(p.getUniqueId(), new ArrayList<>());
        }

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        icon.setItemMeta(meta);
        customKitMap.get(p.getUniqueId()).add(new CustomKit(kit, name, icon));
    }

    public static void removeCustomKit(Player p, CustomKit c){
        if(!customKitMap.containsKey(p.getUniqueId())){
            customKitMap.put(p.getUniqueId(), new ArrayList<>());
        }
        customKitMap.get(p.getUniqueId()).remove(c);
    }

    public static ArrayList<CustomKit> getCustomKitList(Player p){
        return customKitMap.get(p.getUniqueId());
    }

    public static CustomKit getCustomKit(Player p, int index){
        return customKitMap.get(p.getUniqueId()).get(index);
    }



    //KIT INITIALIZERS
    public ItemStack[] iSword(){
        ArrayList<ItemStack> itemList = new ArrayList<>();

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemStack legs = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);

        sword.addEnchantment(Enchantment.SHARPNESS, 4);
        sword.addEnchantment(Enchantment.SWEEPING_EDGE, 3);
        axe.addEnchantment(Enchantment.SHARPNESS, 3);
        boots.addEnchantment(Enchantment.PROTECTION, 4);
        legs.addEnchantment(Enchantment.PROTECTION, 4);
        chest.addEnchantment(Enchantment.PROTECTION, 4);
        helm.addEnchantment(Enchantment.PROTECTION, 4);

        itemList.add(sword);
        itemList.add(axe);

        itemList.add(boots);
        itemList.add(legs);
        itemList.add(chest);
        itemList.add(helm);

        itemList.add(new ItemStack(Material.GOLDEN_APPLE, 5));

        ItemStack[] finalList = new ItemStack[itemList.size()];
        finalList = itemList.toArray(finalList);

        return finalList;
    }
    public ItemStack[] iAxe(){
        ArrayList<ItemStack> itemList = new ArrayList<>();
        itemList.add(new ItemStack(Material.DIAMOND_AXE));
        itemList.add(new ItemStack(Material.BOW));
        itemList.add(new ItemStack(Material.CROSSBOW));
        itemList.add(new ItemStack(Material.ARROW, 6));
        itemList.add(new ItemStack(Material.DIAMOND_SWORD));

        itemList.add(new ItemStack(Material.DIAMOND_BOOTS));
        itemList.add(new ItemStack(Material.DIAMOND_LEGGINGS));
        itemList.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
        itemList.add(new ItemStack(Material.DIAMOND_HELMET));

        itemList.add(new ItemStack(Material.SHIELD));

        ItemStack[] finalList = new ItemStack[itemList.size()];
        finalList = itemList.toArray(finalList);

        return finalList;
    }
    public ItemStack[] iApple(){
        ArrayList<ItemStack> itemList = new ArrayList<>();
        itemList.add(new ItemStack(Material.BOW));
        itemList.add(new ItemStack(Material.ARROW, 16));
        itemList.add(new ItemStack(Material.DIAMOND_BOOTS));
        itemList.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        itemList.add(new ItemStack(Material.AIR));
        itemList.add(new ItemStack(Material.DIAMOND_HELMET));

        itemList.add(new ItemStack(Material.GOLDEN_CARROT, 16));

        ItemStack[] finalList = new ItemStack[itemList.size()];
        finalList = itemList.toArray(finalList);

        return finalList;
    }
    public ItemStack[] iPotion(){
        ArrayList<ItemStack> itemList = new ArrayList<>();
        itemList.add(new ItemStack(Material.TRIDENT));
        itemList.add(new ItemStack(Material.DIAMOND_AXE));

        itemList.add(new ItemStack(Material.DIAMOND_BOOTS));
        itemList.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        itemList.add(new ItemStack(Material.AIR));
        itemList.add(new ItemStack(Material.DIAMOND_HELMET));

        itemList.add(new ItemStack(Material.GOLDEN_CARROT, 16));

        ItemStack[] finalList = new ItemStack[itemList.size()];
        finalList = itemList.toArray(finalList);

        return finalList;
    }


    //SAVING CUSTOM KITS

    public static void saveCustomKits(){
        YamlConfiguration config = new YamlConfiguration();

        for(UUID uuid : customKitMap.keySet()){
            String uuidString = uuid.toString();
            ArrayList<CustomKit> kitList = customKitMap.get(uuid);
            for(CustomKit kit : kitList){
                config.set(uuidString + "." + kit.getName(), itemsToBase64(kit.getKit()));
            }
        }

        try {
            config.save(customKitFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadCustomKits(){
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(customKitFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for (String uuid : config.getKeys(false)) {

            ArrayList<CustomKit> customKitList = new ArrayList<>();

            for(String kitName : config.getConfigurationSection(uuid).getKeys(false)){

                ItemStack[] kit = itemsFromBase64((String) config.get(uuid + "." + kitName));

                ItemStack arrow = new ItemStack(Material.ARROW);
                ItemMeta meta = arrow.getItemMeta();
                meta.setDisplayName(kitName);
                arrow.setItemMeta(meta);

                CustomKit customKit = new CustomKit(kit, kitName, arrow);

                customKitList.add(customKit);

                Bukkit.getLogger().info("Loading " + kitName);
            }

            customKitMap.put(UUID.fromString(uuid), customKitList);
        }

    }



    public static String itemsToBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Could not convert inventory to base64.", e);
        }
    }

    public static ItemStack[] itemsFromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] itemList = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < itemList.length; i++){
                itemList[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return itemList;
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException("Unable to decode the class type.", e);
        }
        catch(IOException e) {
            throw new RuntimeException("Unable to convert Inventory to Base64.", e);
        }
    }

}
