package dijon.infuseduels.commands;

import dijon.infuseduels.TestRunnable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class debug implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p){
            if(!p.isOp()) return true;
            TestRunnable.debugOn = !TestRunnable.debugOn;
            p.sendMessage(ChatColor.GREEN + "Dijon Debug Menu is active in the console");
        }
        return true;
    }
}
