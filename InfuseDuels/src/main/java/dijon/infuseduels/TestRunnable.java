package dijon.infuseduels;

import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.guis.DuelMenuManager;
import dijon.infuseduels.guis.SettingsMenuHandler;
import dijon.infuseduels.kits.KitManager;
import dijon.infuseduels.parties.PartyManager;
import dijon.infuseduels.parties.PartyRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

public class TestRunnable extends BukkitRunnable {

    public static boolean debugOn = false;

    @Override
    public void run() {

        if(!debugOn) return;

        Bukkit.getLogger().info("----------------DIJON'S DEBUG MENU------------------");
        Bukkit.getLogger().info("Duel Requests: " + DuelRequestManager.masterRequestList.toString());
        Bukkit.getLogger().info("Duels: " + DuelManager.masterDuelList.toString());
        Bukkit.getLogger().info("Party Requests: " + PartyRequestManager.masterPartyRequestList.toString());
        Bukkit.getLogger().info("Parties: " + PartyManager.masterPartyList.toString());
        Bukkit.getLogger().info("Custom Kits: " + KitManager.customKitMap.toString());
        Bukkit.getLogger().info("Custom Kit Menus: " + DuelMenuManager.customKitMenuMap.toString());
        Bukkit.getLogger().info("Setting Menus: " + DuelMenuManager.settingsMenuMap.toString());
        for(UUID key : SettingsMenuHandler.settingsMap.keySet()){
            Bukkit.getLogger().info("Settings: " + Arrays.toString(SettingsMenuHandler.settingsMap.get(key)));
        }
    }
}
