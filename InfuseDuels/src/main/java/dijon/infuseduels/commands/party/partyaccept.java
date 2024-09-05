package dijon.infuseduels.commands.party;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.duelrequests.DuelRequest;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.parties.PartyManager;
import dijon.infuseduels.parties.PartyRequest;
import dijon.infuseduels.parties.PartyRequestManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class partyaccept implements CommandExecutor {

    InfuseDuels plugin;

    public partyaccept(InfuseDuels plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p2){

            PartyRequest partyRequest = PartyRequestManager.getPartyRequest(p2);

            if(partyRequest == null){
                p2.sendMessage(ChatColor.RED + "You have no party requests");
                return true;
            }
            if(!partyRequest.getSender().isOnline()){
                p2.sendMessage(ChatColor.RED + "The player is no longer online");
                PartyRequestManager.removeRequest(partyRequest);
                return true;
            }

            partyRequest.getSender().sendMessage(ChatColor.GREEN + p2.getName() + " Has been added to the party");
            partyRequest.getReceiver().sendMessage(ChatColor.GREEN + "You were added to the party");

            PartyRequestManager.removeRequest(partyRequest);
            PartyManager.addToParty(partyRequest.getSender(), partyRequest.getReceiver());
            PartyRequestManager.cancelCanceller(partyRequest);

            return true;


        }
        return false;
    }
}
