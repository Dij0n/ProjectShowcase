package dijon.strengthsmp.handlers;

import dijon.strengthsmp.data.PlayerData;
import dijon.strengthsmp.data.PlayerDataManager;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class UltimateHandler {

    public static void ultimateAttack(Player p){
        PlayerData save = PlayerDataManager.getPlayerData(p);
        if(save.strength < 3){
            p.sendMessage(ChatColor.RED + "You do not have enough strength to use an ultimate attack");
            return;
        }
        if(System.currentTimeMillis() - save.lastUltActivation < 60000){
            int timeLeft = (int) ((60000 - (System.currentTimeMillis() - save.lastUltActivation)) / 1000);
            p.sendMessage(ChatColor.RED + "Cooling down... " + ChatColor.BOLD + timeLeft + "s");
            return;
        }

        PlayerDataManager.resetUltTime(p);
        MythicHandler.mythicUltimateAttack(p, save.ultimateAttack);


    }


}
