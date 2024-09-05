package dijon.infuseduels.parties;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PartyManager {

    public static ArrayList<ArrayList<Player>> masterPartyList = new ArrayList<>();
    public static HashMap<Player, Boolean> partied = new HashMap<>();
    public static HashMap<Player, Boolean> openPartyMap = new HashMap<>();


    public static void addToParty(Player sender, Player receiver){
        if(addParty(sender, receiver)){
            partied.put(sender, true);
            partied.put(receiver, true);
            openPartyMap.put(sender, false);
            openPartyMap.put(receiver, false);
        }
    }

    private static boolean addParty(Player sender, Player receiver){
        if(getPlayerPartyStatus(sender)){
            int partyIndex = getPartyByPlayer(sender);

            int partyMax = 0;
            if(sender.hasPermission("duels.vip")){
                partyMax = 16;
            }else{
                partyMax = 4;
            }

            if(masterPartyList.get(partyIndex).size() >= partyMax){
                receiver.sendMessage(ChatColor.RED + "This party is full");
                return false;
            }
            masterPartyList.get(partyIndex).add(receiver);
        }else{
            ArrayList<Player> newParty = new ArrayList<>();
            newParty.add(sender);
            newParty.add(receiver);
            masterPartyList.add(newParty);
        }
        return true;
    }

    public static void leaveParty(Player sender){
        int partyIndex = getPartyByPlayer(sender);
        masterPartyList.get(partyIndex).remove(sender);
        partied.put(sender, false);
        sender.sendMessage(ChatColor.RED + "Party left");

        if(masterPartyList.get(partyIndex).size() == 1){
            Player finalMember = masterPartyList.get(partyIndex).get(0);
            masterPartyList.remove(partyIndex);
            partied.put(finalMember, false);
        }

    }


    public static boolean getPlayerPartyStatus(Player p){
        if(partied.get(p) == null) return false;
        return partied.get(p);
    }

    public static ArrayList<Player> getParty(Player p){
        int partyIndex = getPartyByPlayer(p);
        return masterPartyList.get(partyIndex);
    }

    private static int getPartyByPlayer(Player p){
        for(ArrayList<Player> party : masterPartyList){
            if(party.contains(p)){
                return masterPartyList.indexOf(party);
            }
        }
        return -1;
    }

    public static boolean inSameParty(Player p1, Player p2){
        for(ArrayList<Player> party : masterPartyList){
            if(party.contains(p1) && party.contains(p2)){
                return true;
            }
        }
        return false;
    }

    public static boolean getPartyOpenStatus(Player p){
        if(openPartyMap.get(p) == null) return false;
        return openPartyMap.get(p);
    }

    public static void setPartyOpenStatus(Player p, boolean b){
        openPartyMap.put(p, b);
    }


}
