package dijon.zombiesbase.perks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class PerkRunnable extends BukkitRunnable {

    public abstract void runP(Player p);

}
