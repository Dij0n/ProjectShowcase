package dijon.infuseduels.commands.duels;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.Duel;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.duels.duelrequests.DuelRequest;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.parties.PartyManager;
import dijon.infuseduels.parties.PartyRequestManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class duelaccept implements CommandExecutor {

    InfuseDuels plugin;

    public duelaccept(InfuseDuels plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p2){

            DuelRequest duelRequest = DuelRequestManager.getDuelRequest(p2);

            if(duelRequest == null){
                p2.sendMessage(ChatColor.RED + "You have no duel requests");
                return true;
            }
            if(!duelRequest.getSender().isOnline()){
                p2.sendMessage(ChatColor.RED + "The player is no longer online");
                DuelRequestManager.removeRequest(duelRequest);
                return true;
            }

            duelRequest.getSender().sendMessage(ChatColor.GREEN + "Entering duel arena!");
            duelRequest.getReceiver().sendMessage(ChatColor.GREEN + "Entering duel arena!");

            ArrayList<Player> party1 = new ArrayList<>();
            party1.add(duelRequest.getSender());
            ArrayList<Player> party2 = new ArrayList<>();
            party2.add(duelRequest.getReceiver());

            if(PartyManager.getPlayerPartyStatus(duelRequest.getSender())){
                ArrayList<Player> restOfParty = PartyManager.getParty(duelRequest.getSender());
                for(Player rest : restOfParty){
                    if(!party1.contains(rest)) party1.add(rest);
                }
            }

            if(PartyManager.getPlayerPartyStatus(duelRequest.getReceiver())){
                ArrayList<Player> restOfParty = PartyManager.getParty(duelRequest.getReceiver());
                for(Player rest : restOfParty){
                    if(!party2.contains(rest)) party2.add(rest);
                }
            }


            Duel duel = new Duel(party1, party2, duelRequest.getKit(), duelRequest.getWorld(), duelRequest.getArenaName(), duelRequest.getSettings());
            DuelManager.startDuel(duel);
            DuelRequestManager.removeRequest(duelRequest);
            DuelRequestManager.cancelCanceller(duelRequest);


            return true;


        }
        return false;
    }
}
