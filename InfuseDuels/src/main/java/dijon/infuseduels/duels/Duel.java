package dijon.infuseduels.duels;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Duel {

    ArrayList<Player> p1;
    ArrayList<Player> p2;

    int p1Deaths = 0;
    int p2Deaths = 0;

    ItemStack[] kit;
    World world;
    String arenaName;
    boolean[] settings;

    public Duel(ArrayList<Player> p1, ArrayList<Player> p2, ItemStack[] kit, World world, String arenaName, boolean[] settings) {
        this.p1 = p1;
        this.p2 = p2;
        this.kit = kit;
        this.world = world;
        this.arenaName = arenaName;
        this.settings = settings;
    }

    public void updateDeath(Player dead){

        if(p1.contains(dead)){
            p1Deaths++;
            return;
        }
        if(p2.contains(dead)){
            p2Deaths++;
            return;
        }
    }
}
