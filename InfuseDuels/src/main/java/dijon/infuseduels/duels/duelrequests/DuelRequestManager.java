package dijon.infuseduels.duels.duelrequests;

import dijon.infuseduels.InfuseDuels;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DuelRequestManager {

    public static ArrayList<DuelRequest> masterRequestList = new ArrayList<>();
    public static HashMap<DuelRequest, DuelRequestCanceller> duelCancelMap = new HashMap<>();

    public static InfuseDuels plugin;

    public DuelRequestManager(InfuseDuels plugin){
        DuelRequestManager.plugin = plugin;
    }

    public static void makeRequest(DuelRequest duelRequest){
        masterRequestList.add(duelRequest);
    }

    public static void sendRequest(DuelRequest duelRequest){
        Player sender = duelRequest.getSender();
        Player receiver = duelRequest.getReceiver();

        sender.sendMessage(ChatColor.GREEN + "Duel request sent to " + receiver.getName());

        TextComponent component = Component.text("Click to accept!")
                .color(TextColor.color(0x15AB00)).clickEvent(ClickEvent.runCommand("/duelaccept")).hoverEvent(HoverEvent.showText(Component.text("Click to accept!")));

        receiver.sendMessage(ChatColor.GREEN + "Duel request from " + sender.getName());
        receiver.sendMessage(component);

        duelCancelMap.put(duelRequest, new DuelRequestCanceller(duelRequest));
        duelCancelMap.get(duelRequest).runTaskLater(plugin, 100);
    }

    public static void removeRequest(DuelRequest duelRequest){
        masterRequestList.remove(duelRequest);
    }
    public static void cancelCanceller(DuelRequest duelRequest){
        duelCancelMap.get(duelRequest).cancel();
        duelCancelMap.remove(duelRequest);
    }



    public static DuelRequest getDuelRequest(Player receiver){
        for(DuelRequest duelRequest : masterRequestList){
            if(duelRequest.getReceiver().equals(receiver)) return duelRequest;
        }
        return null;
    }

    public static DuelRequest getSentDuelRequest(Player sender){
        for(DuelRequest duelRequest : masterRequestList){
            if(duelRequest.getSender().equals(sender)) return duelRequest;
        }
        return null;
    }

    public static boolean hasSentRequest(Player sender){
        for(DuelRequest duelRequest : masterRequestList){
            if(duelRequest.getSender().equals(sender)) return true;
        }
        return false;
    }


}
