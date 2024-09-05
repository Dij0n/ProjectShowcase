package dijon.zombiesbase.shooting.listeners;

import dijon.zombiesbase.ZombiesBase;
import dijon.zombiesbase.shooting.recoil.Recoiler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class ADSHandler implements Listener {

    public ADSHandler(ZombiesBase plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void adsActivate(PlayerToggleSneakEvent e){
        Recoiler r = new Recoiler(e.getPlayer());

        if(e.isSneaking()){
            r.normalZoomADS();
        }else{
            r.normalZoom();
        }
    }


}
