package dijon.zombiesbase.playerdata;

import dijon.zombiesbase.ZombiesBase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinInitializer implements Listener {

    public JoinInitializer(ZombiesBase plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        PlayerDataManager.initialize(e.getPlayer());
    }

}
