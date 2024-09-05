package dijon.zombiesbase.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class perktabcomplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("jug");
            completions.add("speed");
            completions.add("double");
            completions.add("quick");
            completions.add("stamina");
            completions.add("phd");
            completions.add("mule");
            completions.add("cherry");
            completions.add("widow");
            completions.add("vulture");
            completions.add("clear");
        }

        return completions;
    }
}
