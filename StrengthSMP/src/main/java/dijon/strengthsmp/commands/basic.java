package dijon.strengthsmp.commands;

import dijon.strengthsmp.data.PlayerDataManager;
import dijon.strengthsmp.handlers.MythicHandler;
import dijon.strengthsmp.handlers.UltimateHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class basic implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                MythicHandler.mythicBasicAttack(player, PlayerDataManager.getPlayerData(player).basicAttack);
            }
            return true;
        }

        return false;
    }

}
