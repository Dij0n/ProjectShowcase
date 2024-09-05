package dijon.zombiesbase;

import dijon.zombiesbase.commands.givegun;
import dijon.zombiesbase.commands.perk;
import dijon.zombiesbase.commands.perktabcomplete;
import dijon.zombiesbase.hud.TemporaryHUD;
import dijon.zombiesbase.perks.PerkHandler;
import dijon.zombiesbase.playerdata.JoinInitializer;
import dijon.zombiesbase.shooting.listeners.ADSHandler;
import dijon.zombiesbase.tick.TestRunnable;
import dijon.zombiesbase.utility.PluginGrabber;
import dijon.zombiesbase.shooting.GunType;
import dijon.zombiesbase.shooting.listeners.HoldingHandler;
import dijon.zombiesbase.shooting.listeners.ReloadHandler;
import dijon.zombiesbase.shooting.listeners.ShootHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZombiesBase extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("If you're seeing this, it works");

        new TestRunnable().runTaskTimer(this, 0, 1);

        //-------UTILITY---------
        new PluginGrabber(this);
        new GunType();

        //-------HANDLERS---------
        new ShootHandler(this);
        new HoldingHandler(this);
        new ReloadHandler(this);
        new JoinInitializer(this);
        new PerkHandler(this);
        new ADSHandler(this);

        //-------COMMANDS----------
        this.getCommand("givegun").setExecutor(new givegun());
        this.getCommand("perk").setExecutor(new perk());
        this.getCommand("perk").setTabCompleter(new perktabcomplete());

        //------RUNNABLES----------
        new TemporaryHUD().runTaskTimer(this, 0, 1);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
