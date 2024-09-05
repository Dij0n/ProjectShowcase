package dijon.strengthsmp.commands;

import dijon.strengthsmp.data.PlayerData;
import dijon.strengthsmp.data.PlayerDataManager;
import dijon.strengthsmp.handlers.UltimateHandler;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class attack implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                UltimateHandler.ultimateAttack(player);
            }
            return true;
        }

        return false;
    }

}
