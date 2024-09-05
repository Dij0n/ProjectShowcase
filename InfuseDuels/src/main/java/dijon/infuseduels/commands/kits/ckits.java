package dijon.infuseduels.commands.kits;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.DuelHandler;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuserevamp.filemanagers.EffectFileManager;
import dijon.infuserevamp.filemanagers.InfuseEffects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ckits implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p1 = (Player) commandSender;

        if(!p1.hasPermission("duels.vip")){
            p1.sendMessage(ChatColor.RED + "This feature is VIP-Only");
            return true;
        }

        if(DuelManager.isInDuel(p1)){
            p1.sendMessage(ChatColor.RED + "You cannot do this while duelling!");
            return true;
        }

        if(DuelRequestManager.hasSentRequest(p1)){
            p1.sendMessage(ChatColor.RED + "You have a pending duel request! Wait until you are free to make a custom kit");
            return true;
        }

        if(!p1.getWorld().equals(Bukkit.getWorld("world"))){
            p1.sendMessage(ChatColor.RED + "You can only make custom kits in the lobby");
            return true;
        }

        p1.teleport(new Location(Bukkit.getWorld("ckitworld"), 0, -60, 0));

        p1.sendMessage(ChatColor.GREEN + "♠ Welcome to the Custom Kit creator! Build your kit and use /savekit <Name> to save it to your custom kit menu!");
        return true;
    }
}
