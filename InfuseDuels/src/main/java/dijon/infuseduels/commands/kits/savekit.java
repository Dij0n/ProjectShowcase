package dijon.infuseduels.commands.kits;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.kits.CustomKit;
import dijon.infuseduels.kits.KitManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class savekit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p1 = (Player) commandSender;

        if(!p1.hasPermission("duels.vip")){
            p1.sendMessage(ChatColor.RED + "This feature is VIP-Only");
            return true;
        }

        if(!p1.getWorld().equals(Bukkit.getWorld("ckitworld"))){
            p1.sendMessage(ChatColor.RED + "You cannot use this here!");
            return true;
        }

        if(strings.length != 1){
            p1.sendMessage(ChatColor.RED + "Incorrect usage: /savekit <Kit Name>");
            return true;
        }

        String kitName = strings[0];

        ItemStack[] kit = p1.getInventory().getContents();

        for(int i=0;i<kit.length;i++){
            if(kit[i] == null){
                kit[i] = new ItemStack(Material.AIR);
            }
        }

        p1.sendMessage(String.valueOf(kit.length));

        KitManager.addCustomKit(p1, kit, kitName, new ItemStack(Material.ARROW));
        p1.sendMessage(ChatColor.GREEN + "♠ Kit " + kitName + " added!");

        return true;
    }
}
