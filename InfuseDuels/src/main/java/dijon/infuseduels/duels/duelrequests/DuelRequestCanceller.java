package dijon.infuseduels.duels.duelrequests;

import dijon.infuseduels.duels.Duel;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.worlds.WorldManager;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class DuelRequestCanceller extends BukkitRunnable {

    DuelRequest duel;

    public DuelRequestCanceller(DuelRequest duel){
        this.duel = duel;
    }

    @Override
    public void run() {
        DuelRequestManager.removeRequest(duel);
        WorldManager.freeUpWorld(duel.world.getName());
        duel.getSender().sendMessage("Your duel request has expired");
        duel.getReceiver().sendMessage("The duel request has expired");
    }
}
