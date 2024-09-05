package dijon.zombiesbase.shooting.listeners;

import dijon.zombiesbase.ZombiesBase;
import dijon.zombiesbase.playerdata.PlayerDataManager;
import dijon.zombiesbase.shooting.GunType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ReloadHandler implements Listener {

    public ReloadHandler(ZombiesBase plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void reloadKeyPress(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();

        if(e.getOffHandItem() == null) return;

        if(GunType.isGun(e.getOffHandItem())){

            PlayerDataManager.reloadAttempt(p);

            e.setCancelled(true);

        }
    }

}
