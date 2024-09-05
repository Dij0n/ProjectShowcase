package dijon.infuseduels.parties;

import org.bukkit.entity.Player;

public class PartyRequest {

    Player sender;
    Player receiver;

    public PartyRequest(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }
}
