package dijon.zombiesbase.perks.actions;

import dijon.zombiesbase.perks.PerkRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StamiAction extends PerkRunnable {
    @Override
    public void run() {

    }

    @Override
    public void runP(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1));
    }
}
