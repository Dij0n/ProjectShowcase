package dijon.infuseduels.duels.duelrequests;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DuelRequest {

    ItemStack[] kit;
    Player sender;
    Player receiver;
    World world = null;
    String arenaName;
    boolean[] settings;

    public DuelRequest(Player sender, Player receiver, ItemStack[] kit) {
        this.kit = kit;
        this.sender = sender;
        this.receiver = receiver;
    }

    public ItemStack[] getKit() {
        return kit;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public World getWorld() {
        return world;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public void setKit(ItemStack[] kit) {
        this.kit = kit;
    }

    public void setWorld(World world){
        this.world = world;
    }

    public boolean[] getSettings() {
        return settings;
    }

    public void setSettings(boolean[] settings) {
        this.settings = settings;
    }
}
