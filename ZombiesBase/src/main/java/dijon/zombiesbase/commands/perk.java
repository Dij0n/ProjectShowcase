package dijon.zombiesbase.commands;

import dijon.zombiesbase.perks.PerkType;
import dijon.zombiesbase.playerdata.PlayerData;
import dijon.zombiesbase.playerdata.PlayerDataController;
import dijon.zombiesbase.playerdata.PlayerDataManager;
import dijon.zombiesbase.shooting.GunType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class perk implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.RED + "Incorrect Usage!");
            return true;
        }

        Player p = (Player) commandSender;

        PlayerDataController pd = new PlayerDataController(p);

        switch (strings[0]){
            case "jug":
                pd.addPerk(PerkType.JUGGERNOG);
                break;
            case "speed":
                pd.addPerk(PerkType.SPEEDCOLA);
                break;
            case "double":
                pd.addPerk(PerkType.DOUBLETAP);
                break;
            case "quick":
                pd.addPerk(PerkType.QUICKREVIVE);
                break;
            case "stamina":
                pd.addPerk(PerkType.STAMINUP);
                break;
            case "phd":
                pd.addPerk(PerkType.PHDFLOPPER);
                break;
            case "mule":
                pd.addPerk(PerkType.MULEKICK);
                break;
            case "cherry":
                pd.addPerk(PerkType.ELECTRICCHERRY);
                break;
            case "widow":
                pd.addPerk(PerkType.WIDOWSWINE);
                break;
            case "vulture":
                pd.addPerk(PerkType.VULTURE);
                break;
            case "clear":
                pd.resetPerks();
                break;

        }
        return false;
    }
}
