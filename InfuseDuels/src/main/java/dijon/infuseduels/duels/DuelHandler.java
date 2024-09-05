package dijon.infuseduels.duels;

import dijon.infuseduels.InfuseDuels;
import dijon.infuserevamp.tick.AbilityManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.kyori.adventure.text.Component;


public class DuelHandler implements Listener {

    public static InfuseDuels plugin;
    public DuelHandler(InfuseDuels plugin) {
        DuelHandler.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    public void onDuelDeath(Player p){

        if(!DuelManager.isInDuel(p)) return;

        Duel duel = DuelManager.getDuel(p);
        DuelManager.updateDeath(duel, p);
        AbilityManager.abilityResetter(p);
    }

    @EventHandler
    public void duelDeathSpec(EntityDamageEvent e){
        if(e.getEntity() instanceof Player v){
            if(DuelManager.isInDuel(v)){
                if(v.getHealth() - e.getDamage() <= 0.5){
                    v.setGameMode(GameMode.SPECTATOR);
                    onDuelDeath(v);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDuelLeave(PlayerQuitEvent e){
        if(!DuelManager.isInDuel(e.getPlayer())) return;

        Duel duel = DuelManager.getDuel(e.getPlayer());
        DuelManager.updateDeath(duel, e.getPlayer());
        e.getPlayer().getInventory().clear();
        e.getPlayer().teleport(DuelManager.home);
        e.getPlayer().setGameMode(GameMode.SURVIVAL);
        e.getPlayer().displayName(Component.text(e.getPlayer().getName()));
        AbilityManager.abilityResetter(e.getPlayer());
    }

    //SETTING-SPECIFIC

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!DuelManager.isInDuel(p)) return;
        Duel duel = DuelManager.getDuel(p);
        if(duel == null) return;


        if(!duel.settings[0]){
            if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPearlThrow(ProjectileLaunchEvent e){

        if (!e.getEntity().getType().equals(EntityType.ENDER_PEARL)) return;
        if (!(e.getEntity().getShooter() instanceof Player p)) return;
        if(!DuelManager.isInDuel(p)) return;
        Duel duel = DuelManager.getDuel(p);
        if(duel == null) return;

        if(duel.settings[1]){
            Bukkit.getScheduler().runTaskLater(plugin, () -> p.setCooldown(Material.ENDER_PEARL, 200), 1);
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(!DuelManager.isInDuel(p)) return;
        Duel duel = DuelManager.getDuel(p);
        if(duel == null) return;


        if(!duel.settings[2]){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(!DuelManager.isInDuel(p)) return;
        Duel duel = DuelManager.getDuel(p);
        if(duel == null) return;
        if(!e.getBlock().getType().equals(Material.TNT)) return;


        if(duel.settings[4]){
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.TNT);
        }
    }

    @EventHandler
    public void creeperSpawnEgg(PlayerInteractEvent e){
        if(e.getItem() == null) return;
        if(e.getItem().getType() != Material.CREEPER_SPAWN_EGG) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player p = e.getPlayer();
        if(!DuelManager.isInDuel(p)) return;
        Duel duel = DuelManager.getDuel(p);
        if(duel == null) return;
        if(!duel.settings[4]) return;

        e.setCancelled(true);

        Creeper creeper = (Creeper) e.getPlayer().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0, 1, 0), EntityType.CREEPER);

        creeper.ignite();
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        e.setCancelled(true);
        e.getLocation().getWorld().createExplosion(e.getLocation(), 4, false, false);
    }

    @EventHandler
    public void onFriendlyFire(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player victim && e.getDamager() instanceof Player attacker){
            if(!DuelManager.isInDuel(victim)) return;

            Duel duel = DuelManager.getDuel(victim);
            if(duel == null) return;
            if(duel.settings[6]) return;

            if(DuelManager.onSameTeam(victim, attacker)){
                e.setCancelled(true);
            }
        }
    }

}
