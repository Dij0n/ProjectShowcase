package dijon.zombiesbase.perks;

import dijon.zombiesbase.perks.actions.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PerkType {

    public static Perk JUGGERNOG = new Perk("Juggernog", 2500, new JugAction());

    public static Perk SPEEDCOLA = new Perk("Speed Cola", 3000, new SpeedAction());

    public static Perk DOUBLETAP = new Perk("Double Tap Root Beer", 2000, new DoubleAction());

    public static Perk QUICKREVIVE = new Perk("Quick Revive", 500, new QuickAction());

    public static Perk STAMINUP = new Perk("Stamin-Up Soda", 2000, new StamiAction());

    public static Perk PHDFLOPPER = new Perk("PhD Flopper", 2500, new PHDAction());

    public static Perk MULEKICK = new Perk("Mule Kick", 4000, new MuleAction());

    public static Perk ELECTRICCHERRY = new Perk("Electric Cherry", 2000, new ElectricAction());

    public static Perk WIDOWSWINE = new Perk("Widow's Wine", 4000, new WidowAction());

    public static Perk VULTURE = new Perk("Vulture Aid", 3000, new VultureAction());

}
