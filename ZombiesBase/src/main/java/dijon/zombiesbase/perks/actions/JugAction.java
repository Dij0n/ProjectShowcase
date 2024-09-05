package dijon.zombiesbase.perks.actions;

import dijon.zombiesbase.perks.PerkRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class JugAction extends PerkRunnable {

    @Override
    public void run() {

    }

    @Override
    public void runP(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, PotionEffect.INFINITE_DURATION, 4));
    }
}
