package dijon.infuseduels.commands.party;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.parties.PartyManager;
import dijon.infuseduels.parties.PartyRequest;
import dijon.infuseduels.parties.PartyRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class party implements CommandExecutor {


    InfuseDuels plugin;

    public party(InfuseDuels plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p1 = (Player) commandSender;

        if(strings.length == 2){
            if(!(strings[0].equals("add") || strings[0].equals("remove")|| strings[0].equals("join"))){
                p1.sendMessage(ChatColor.RED + "Incorrect usage: \"/party help\" for commands");
            }
        }

        switch (strings[0]) {
            case "remove" -> {
                removePlayer(p1, strings[1]);
                return true;
            }
            case "leave" -> {
                leavePlayer(p1);
                return true;
            }
            case "list" ->{
                listParty(p1);
                return true;
            }
            case "help" ->{
                helpParty(p1);
                return true;
            }
            case "open" ->{
                openParty(p1);
                return true;
            }
            case "join" ->{
                joinParty(p1, strings[1]);
                return true;
            }
            case "add" ->{
                if(strings.length == 1){
                    p1.sendMessage(ChatColor.RED + "Incorrect usage: \"/party help\" for commands");
                    return true;
                }
                addPlayer(p1, strings[1]);
                return true;
            }
            default -> {
                addPlayer(p1, strings[0]);
                return true;
            }
        }
    }


    public void addPlayer(Player p1, String s){

        Player p2 = Bukkit.getPlayer(s);
        if(p2 == null){
            p1.sendMessage(ChatColor.RED + "Player is not online/does not exist");
            return;
        }

        if(p2.equals(p1)){
            p1.sendMessage(ChatColor.RED + "You cannot party yourself!");
            return;
        }

        if(PartyManager.getPlayerPartyStatus(p2)){
            p1.sendMessage(ChatColor.RED + "Player is already in a party!");
            return;
        }

        if(PartyRequestManager.hasSentRequest(p1)){
            p1.sendMessage(ChatColor.RED + "You have already sent this person a party request!");
            return;
        }

        int partyMax = 0;
        if(p1.hasPermission("duels.vip")){
            partyMax = 16;
        }else{
            partyMax = 4;
        }

        if(PartyManager.getPlayerPartyStatus(p1)){
            if(PartyManager.getParty(p1).size() >= partyMax){
                p1.sendMessage(ChatColor.RED + "Your party is full! Your party maximum is " + partyMax);
                return;
            }
        }

        PartyRequest request = new PartyRequest(p1, p2);
        PartyRequestManager.makeRequest(request);
    }

    public void removePlayer(Player p1, String s){
        Player p2 = Bukkit.getPlayer(s);
        if(p2 == null){
            p1.sendMessage(ChatColor.RED + "Player is not online/does not exist");
            return;
        }

        if(!PartyManager.inSameParty(p1, p2)){
            p1.sendMessage(ChatColor.RED + "This player is not in your party!");
            return;
        }

        p1.sendMessage(ChatColor.RED + "You have been removed from the party");
        PartyManager.leaveParty(p2);

    }

    public void leavePlayer(Player p1){

        if(!PartyManager.getPlayerPartyStatus(p1)){
            p1.sendMessage(ChatColor.RED + "You're not in a party!");
            return;
        }

        PartyManager.leaveParty(p1);
    }

    public void listParty(Player p1){
        if(!PartyManager.getPlayerPartyStatus(p1)){
            p1.sendMessage(ChatColor.RED + "You're not in a party!");
            return;
        }
        ArrayList<Player> party = PartyManager.getParty(p1);
        p1.sendMessage(ChatColor.GREEN + "Party members:");
        for(Player p : party){
            p1.sendMessage(ChatColor.GREEN + "- " + p.getName());
        }
    }

    public void helpParty(Player p1){
        p1.sendMessage(ChatColor.GREEN + "Party commands:");
        p1.sendMessage(ChatColor.GREEN + "- add (Invites player to party)");
        p1.sendMessage(ChatColor.GREEN + "- remove (Removes player from party)");
        p1.sendMessage(ChatColor.GREEN + "- list (Lists players in party)");
        p1.sendMessage(ChatColor.GREEN + "- leave (Leaves party)");
        p1.sendMessage(ChatColor.GREEN + "- play (Starts party duel)");
        p1.sendMessage(ChatColor.GREEN + "- help (Lists this page)");

    }

    public void openParty(Player p1){
        if(!PartyManager.getPlayerPartyStatus(p1)){
            p1.sendMessage(ChatColor.RED + "You are not in a party");
            return;
        }
        p1.sendMessage(ChatColor.GREEN + "Party opened!");
        PartyManager.setPartyOpenStatus(p1, true);
    }

    public void joinParty(Player p1, String s){
        Player p2 = Bukkit.getPlayer(s);
        if(p2 == null){
            p1.sendMessage(ChatColor.RED + "Player is not online/does not exist");
            return;
        }

        if(PartyManager.getPlayerPartyStatus(p1)){
            p1.sendMessage(ChatColor.RED + "You are already in a party!");
            return;
        }

        if(!PartyManager.getPlayerPartyStatus(p2)){
            p1.sendMessage(ChatColor.RED + "This person is not in a party");
            return;
        }

        if(!PartyManager.getPartyOpenStatus(p2)){
            p1.sendMessage(ChatColor.RED + "This party is not open");
            return;
        }

        p1.sendMessage(ChatColor.GREEN + "You joined " + p2.getName() + "'s party!");


        PartyManager.addToParty(p2, p1);

    }

}
