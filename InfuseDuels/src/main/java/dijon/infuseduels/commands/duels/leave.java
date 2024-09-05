package dijon.infuseduels.commands.duels;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.duels.Duel;
import dijon.infuseduels.duels.DuelManager;
import dijon.infuserevamp.filemanagers.EffectFileManager;
import dijon.infuserevamp.filemanagers.InfuseEffects;
import dijon.infuserevamp.tick.AbilityManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class leave implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;

        if(p.getWorld().equals(Bukkit.getWorld("ckitworld"))){
            p.getInventory().clear();
            p.teleport(DuelManager.home);
            p.setGameMode(GameMode.SURVIVAL);
            return true;
        }

        if(!DuelManager.isInDuel(p)){
            p.sendMessage(ChatColor.RED + "You can only use this command in a duel or in the custom kit lobby");
            return true;
        }


        Duel duel = DuelManager.getDuel(p);
        DuelManager.updateDeath(duel, p);
        EffectFileManager.setPrimary(p.getUniqueId().toString(), InfuseEffects.NONE);
        EffectFileManager.setSecondary(p.getUniqueId().toString(), InfuseEffects.NONE);
        AbilityManager.abilityResetter(p);
        p.getInventory().clear();
        p.displayName(Component.text(p.getName()));
        p.teleport(DuelManager.home);
        p.setGameMode(GameMode.SURVIVAL);

        return true;
    }
}
