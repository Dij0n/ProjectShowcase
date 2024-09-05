package dijon.infuseduels.commands.duels;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.guis.DuelMenuManager;
import dijon.infuseduels.duels.duelrequests.DuelRequest;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.parties.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class duel implements CommandExecutor {

    InfuseDuels plugin;

    public duel(InfuseDuels plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p1){
            if(strings.length != 1){
                p1.sendMessage(ChatColor.RED + "Incorrect Usage: /duel <PLAYER>");
                return true;
            }

            Player p2 = Bukkit.getPlayer(strings[0]);
            if(p2 == null){
                p1.sendMessage(ChatColor.RED + "Player is not online/does not exist");
                return true;
            }

//            if(p2.equals(p1)){
//                p1.sendMessage(ChatColor.RED + "You cannot duel yourself!");
//                return true;
//            }

            if(DuelManager.isInDuel(p2)){
                p1.sendMessage(ChatColor.RED + "This person is currently duelling!");
                return true;
            }

            if(DuelManager.isInDuel(p1)){
                p1.sendMessage(ChatColor.RED + "You cannot request a duel while duelling!");
                return true;
            }

            if(DuelRequestManager.hasSentRequest(p1)){
                p1.sendMessage(ChatColor.RED + "You have already sent this person a duel request!");
                return true;
            }

            if(DuelRequestManager.hasSentRequest(p2)){
                p1.sendMessage(ChatColor.RED + "This person has sent a duel request to someone else. Please wait until they're free");
                return true;
            }

            if(PartyManager.getPlayerPartyStatus(p1)){
                if(PartyManager.getParty(p1).contains(p2)){
                    p1.sendMessage(ChatColor.RED + "You cannot duel someone in your party!");
                    return true;
                }
            }

            if(p1.getWorld().equals(Bukkit.getWorld("ckitworld"))){
                p1.sendMessage(ChatColor.RED + "You cannot use this here!");
                return true;
            }



            DuelMenuManager.openKitMenu(p1);
            DuelRequest request = new DuelRequest(p1, p2, null);
            DuelRequestManager.makeRequest(request);


        }
        return true;
    }
}
