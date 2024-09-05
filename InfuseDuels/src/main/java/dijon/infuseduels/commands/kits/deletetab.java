package dijon.infuseduels.commands.kits;

import dijon.infuseduels.kits.CustomKit;
import dijon.infuseduels.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class deletetab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player p = (Player) sender;

        if (args.length == 1) {
            if(KitManager.getCustomKitList(p) == null){
                return completions;
            }
            for(CustomKit ckit : KitManager.getCustomKitList(p)){
                completions.add(ckit.getName());
            }
        }

        return completions;
    }
}
