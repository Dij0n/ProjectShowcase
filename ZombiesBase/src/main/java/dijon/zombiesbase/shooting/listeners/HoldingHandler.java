package dijon.zombiesbase.shooting.listeners;

import dijon.zombiesbase.ZombiesBase;
import dijon.zombiesbase.playerdata.PlayerDataController;
import dijon.zombiesbase.playerdata.PlayerDataManager;
import dijon.zombiesbase.shooting.Gun;
import dijon.zombiesbase.shooting.GunType;
import dijon.zombiesbase.shooting.recoil.Recoiler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class HoldingHandler implements Listener {

    public HoldingHandler(ZombiesBase plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void detectHolding(PlayerItemHeldEvent e){

        //---------THIS IS TEMPORARY-----------------------
        //---------GUNS ARE SET BY HOLDING FOR NOW---------
        //---------WILL BE CHANGED TO WALL BUYS AND BOX----

        Player p = e.getPlayer();
        PlayerDataController pd = new PlayerDataController(p);

        if(p.getInventory().getItem(e.getNewSlot()) == null) return;
        ItemStack inHand = p.getInventory().getItem(e.getNewSlot());

        if(GunType.isGun(inHand)){
            int customMD = inHand.getItemMeta().getCustomModelData();
            Gun gun = new Gun(GunType.getGun(customMD));
            if(ShootHandler.holdMap.get(e.getPlayer().getUniqueId()) != null) ShootHandler.holdMap.get(p.getUniqueId()).fullCancel();
            pd.setMainGun(gun);
        }else{
            //PlayerDataManager.setMainGun(e.getPlayer(), GunType.getGun(0));
        }
    }
}
