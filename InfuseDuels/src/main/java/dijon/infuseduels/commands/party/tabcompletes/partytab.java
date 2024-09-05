package dijon.infuseduels.commands.party.tabcompletes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class partytab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("add");
            completions.add("remove");
            completions.add("list");
            completions.add("leave");
            completions.add("play");
            completions.add("join");
            completions.add("open");
            completions.add("help");
        }

        return completions;
    }
}
