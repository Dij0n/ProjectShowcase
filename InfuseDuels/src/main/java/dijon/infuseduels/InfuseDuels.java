package dijon.infuseduels;
import dijon.infuseduels.commands.debug;
import dijon.infuseduels.commands.duels.duel;
import dijon.infuseduels.commands.duels.duelaccept;
import dijon.infuseduels.commands.duels.leave;
import dijon.infuseduels.commands.duels.spectate;
import dijon.infuseduels.commands.kits.ckits;
import dijon.infuseduels.commands.kits.deletekit;
import dijon.infuseduels.commands.kits.deletetab;
import dijon.infuseduels.commands.kits.savekit;
import dijon.infuseduels.commands.party.party;
import dijon.infuseduels.commands.party.partyaccept;
import dijon.infuseduels.commands.party.tabcompletes.partytab;
import dijon.infuseduels.duels.DuelHandler;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuseduels.guis.*;
import dijon.infuseduels.duels.duelrequests.DuelRequestManager;
import dijon.infuseduels.kits.CustomKitHandler;
import dijon.infuseduels.kits.KitManager;
import dijon.infuseduels.parties.PartyRequestManager;
import dijon.infuseduels.purger.RequestPurgeHandler;
import dijon.infuseduels.worlds.WorldManager;
import dijon.infuserevamp.InfuseRevamp;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class InfuseDuels extends JavaPlugin {

    public static InfuseDuels plugin;

    @Override
    public void onEnable() {

        plugin = this;

        //HANDLERS
        new DuelMenuHandler(this);
        new DuelHandler(this);
        new RequestPurgeHandler(this);
        new CustomKitMenuHandler(this);
        new EffectMenuHandler(this);
        new CustomKitHandler(this);
        new SettingsMenuHandler(this);

        //INITIALIZERS
        new TestRunnable().runTaskTimer(this, 0, 20);
        new KitManager(this);
        new DuelMenuManager();
        new DuelManager(this);
        new DuelRequestManager(this);
        new PartyRequestManager(this);
        new WorldManager();

        //COMMANDS
        this.getCommand("duel").setExecutor(new duel(this));
        this.getCommand("duelaccept").setExecutor(new duelaccept(this));
        this.getCommand("party").setExecutor(new party(this));
        this.getCommand("p").setExecutor(new party(this));
        this.getCommand("partyaccept").setExecutor(new partyaccept(this));
        this.getCommand("spectate").setExecutor(new spectate());
        this.getCommand("ckits").setExecutor(new ckits());
        this.getCommand("savekit").setExecutor(new savekit());
        this.getCommand("leave").setExecutor(new leave());
        this.getCommand("deletekit").setExecutor(new deletekit());

        this.getCommand("party").setTabCompleter(new partytab());
        this.getCommand("p").setTabCompleter(new partytab());
        this.getCommand("deletekit").setTabCompleter(new deletetab());
        this.getCommand("dijondebug").setExecutor(new debug());



    }

    @Override
    public void onDisable() {
        KitManager.saveCustomKits();
    }
}
