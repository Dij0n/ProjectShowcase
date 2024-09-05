package dijon.infuseduels.commands.duels;

import dijon.infuseduels.duels.DuelHandler;
import dijon.infuseduels.duels.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class spectate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p1){
            if(strings.length != 1){
                p1.sendMessage(ChatColor.RED + "Incorrect Usage: /spectate <PLAYER>");
                return true;
            }

            Player p2 = Bukkit.getPlayer(strings[0]);
            if(p2 == null){
                p1.sendMessage(ChatColor.RED + "Player is not online/does not exist");
                return true;
            }

            if(!DuelManager.isInDuel(p2)) {
                p1.sendMessage(ChatColor.RED + "This person is not in a duel!");
                return true;
            }

            if(DuelManager.isInDuel(p1)) {
                p1.sendMessage(ChatColor.RED + "You can't spectate in a duel!");
                return true;
            }

            if(p1.getWorld().equals(p2.getWorld())){
                p1.sendMessage(ChatColor.RED + "You are already spectating this person!");
                return true;
            }


            p1.teleport(p2.getLocation());
            Bukkit.getScheduler().runTaskLater(DuelHandler.plugin, ()->{
                p1.setGameMode(GameMode.SPECTATOR);
            }, 2);



        }
        return true;
    }
}
