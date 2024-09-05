package dijon.strengthsmp.commands;

import dijon.strengthsmp.data.PlayerData;
import dijon.strengthsmp.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class strength implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                PlayerData playerData = PlayerDataManager.getPlayerData(player);


                player.sendMessage(ChatColor.GREEN + "Your current strength is: " + ChatColor.DARK_GREEN + "+" + playerData.getStrength());
                player.sendMessage(ChatColor.GREEN + "Your current basic attack is: " + ChatColor.DARK_GREEN + playerData.getBasicAttack());
                if(playerData.getUltimateAttack() == null){
                    player.sendMessage(ChatColor.RED + "You do not have an Ultimate attack");
                }else{
                    player.sendMessage(ChatColor.GREEN + "Your current ultimate attack is: " + ChatColor.DARK_GREEN + playerData.getUltimateAttack());
                }

            } else {
                sender.sendMessage("Only players can use this command without arguments.");
            }
            return true;
        }

        if(args.length == 2){
            Player player = (Player)sender;
            int strength = Integer.parseInt(args[1]);

            if(!args[0].equals("set")) return true;
            if(player.isOp()){
                PlayerDataManager.setStrength(player, strength);
                player.sendMessage(ChatColor.GREEN + "Your strength is now" + ChatColor.DARK_GREEN + "+" + strength);
            }else{
                sender.sendMessage(ChatColor.RED + "Only Opped players can set strength");
            }
        }

        return false;
    }

}
