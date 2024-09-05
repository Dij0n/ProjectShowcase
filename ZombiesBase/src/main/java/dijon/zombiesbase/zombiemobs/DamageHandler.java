package dijon.zombiesbase.zombiemobs;

import dijon.zombiesbase.ZombiesBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageHandler implements Listener {

    public DamageHandler(ZombiesBase plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void iFrameClear(EntityDamageEvent e){
        if(e.getEntity() instanceof LivingEntity le){
            le = (LivingEntity) e.getEntity();
            le.setNoDamageTicks(1);
            //le.setMaximumNoDamageTicks(0); //Maybe
        }
    }

}
