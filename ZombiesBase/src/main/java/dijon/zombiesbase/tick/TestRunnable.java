package dijon.zombiesbase.tick;

import dijon.zombiesbase.playerdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TestRunnable extends BukkitRunnable {


    public TestRunnable() {

    }

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){

        }

    }
}
