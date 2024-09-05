package dijon.infuseduels.commands.kits;

import dijon.infuseduels.kits.CustomKit;
import dijon.infuseduels.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class deletekit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p1 = (Player) commandSender;

        if(!p1.hasPermission("duels.vip")){
            p1.sendMessage(ChatColor.RED + "This feature is VIP-Only");
            return true;
        }

        if(strings.length != 1){
            p1.sendMessage(ChatColor.RED + "Incorrect usage: /savekit <Kit Name>");
            return true;
        }

        if(KitManager.getCustomKitList(p1) == null){
            p1.sendMessage(ChatColor.RED + "You have no custom kits!");
            return true;
        }

        CustomKit ckit = getCustomKit(strings[0], p1);

        if(ckit == null){
            p1.sendMessage(ChatColor.RED + "You do not have a kit by this name");
            return true;
        }


        KitManager.removeCustomKit(p1, ckit);
        p1.sendMessage(ChatColor.GREEN + "♠ Kit " + ckit.getName() + " removed!");

        return true;
    }


    public CustomKit getCustomKit(String kitName, Player p){
        for(CustomKit ckit : KitManager.getCustomKitList(p)){
            if(ckit.getName().equals(kitName)){
                return ckit;
            }
        }
        return null;
    }
}
