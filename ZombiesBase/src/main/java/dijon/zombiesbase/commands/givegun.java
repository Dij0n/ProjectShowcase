package dijon.zombiesbase.commands;

import dijon.zombiesbase.shooting.GunType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class givegun implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.RED + "Incorrect Usage!");
            return true;
        }

        Player p = (Player) commandSender;

        switch (strings[0]){
            case "red":
                p.getInventory().addItem(GunType.RED.getItemStack());
                break;
            case "green":
                p.getInventory().addItem(GunType.GREEN.getItemStack());
                break;
            case "blue":
                p.getInventory().addItem(GunType.BLUE.getItemStack());
                break;

        }
        return false;
    }
}
