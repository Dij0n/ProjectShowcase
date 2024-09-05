package dijon.strengthsmp.handlers;

import dijon.strengthsmp.data.PlayerDataManager;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MythicHandler {


    public static void mythicBasicAttack(Player p, String s){

        if(Arrays.stream(PlayerDataManager.BASIC_ATTACKS).toList().contains(s)){
            MythicBukkit.inst().getAPIHelper().castSkill(p, s, p, p.getLocation(), null, null, 1.0F);
        }else {
            p.sendMessage(ChatColor.RED + "[Debug] Attack not found. Please reroll");
        }

    }

    public static void mythicUltimateAttack(Player p, String s){

        if(Arrays.stream(PlayerDataManager.ULTIMATE_ATTACKS).toList().contains(s) || s.equals("Dragonborn")){
            MythicBukkit.inst().getAPIHelper().castSkill(p, s, p, p.getLocation(), null, null, 1.0F);
        }else {
            p.sendMessage(ChatColor.RED + "[Debug] Attack not found. Please reroll");
        }

    }


}
