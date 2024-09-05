package dijon.infuseduels.parties;

import dijon.infuseduels.duels.duelrequests.DuelRequest;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import org.bukkit.scheduler.BukkitRunnable;

public class PartyRequestCanceller extends BukkitRunnable {

    PartyRequest party;

    public PartyRequestCanceller(PartyRequest party){
        this.party = party;
    }

    @Override
    public void run() {
        PartyRequestManager.removeRequest(party);
        party.getSender().sendMessage("Your party request has expired");
        party.getReceiver().sendMessage("The party request has expired");
    }
}
