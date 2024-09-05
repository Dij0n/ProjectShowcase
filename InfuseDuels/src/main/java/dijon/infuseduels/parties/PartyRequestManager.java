package dijon.infuseduels.parties;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.duelrequests.DuelRequest;
import dijon.infuseduels.duels.duelrequests.DuelRequestCanceller;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PartyRequestManager {

    public static ArrayList<PartyRequest> masterPartyRequestList = new ArrayList<>();
    public static HashMap<PartyRequest, PartyRequestCanceller> partyCancelMap = new HashMap<>();

    public static InfuseDuels plugin;

    public PartyRequestManager(InfuseDuels plugin){
        PartyRequestManager.plugin = plugin;
    }

    public static void makeRequest(PartyRequest partyRequest){
        masterPartyRequestList.add(partyRequest);
        Player sender = partyRequest.getSender();
        Player receiver = partyRequest.getReceiver();

        sender.sendMessage(ChatColor.GREEN + "Sent request to " + receiver.getName());

        TextComponent component = Component.text("Click to accept!")
                .color(TextColor.color(0x15AB00)).clickEvent(ClickEvent.runCommand("/partyaccept")).hoverEvent(HoverEvent.showText(Component.text("Click to accept!")));

        receiver.sendMessage(ChatColor.GREEN + "Party request from " + sender.getName() + "!");
        receiver.sendMessage(component);

        partyCancelMap.put(partyRequest, new PartyRequestCanceller(partyRequest));
        partyCancelMap.get(partyRequest).runTaskLater(plugin, 100);
    }

    public static void removeRequest(PartyRequest partyRequest){
        masterPartyRequestList.remove(partyRequest);
    }

    public static void cancelCanceller(PartyRequest partyRequest){
        partyCancelMap.get(partyRequest).cancel();
        partyCancelMap.remove(partyRequest);
    }

    public static PartyRequest getPartyRequest(Player receiver){
        for(PartyRequest partyRequest : masterPartyRequestList){
            if(partyRequest.getReceiver().equals(receiver)) return partyRequest;
        }
        return null;
    }

    public static PartyRequest getSentPartyRequest(Player sender){
        for(PartyRequest partyRequest : masterPartyRequestList){
            if(partyRequest.getSender().equals(sender)) return partyRequest;
        }
        return null;
    }

    public static boolean hasSentRequest(Player sender){
        for(PartyRequest getPartyRequest : masterPartyRequestList){
            if(getPartyRequest.getSender().equals(sender)) return true;
        }
        return false;
    }


}
