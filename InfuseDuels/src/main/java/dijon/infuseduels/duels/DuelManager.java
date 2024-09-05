package dijon.infuseduels.duels;

import dijon.infuseduels.InfuseDuels;
import dijon.infuseduels.guis.DuelMenuManager;
import dijon.infuseduels.guis.EffectMenuHandler;
import dijon.infuseduels.kits.KitApplier;
import dijon.infuseduels.worlds.WorldManager;
import dijon.infuserevamp.filemanagers.EffectFileManager;
import dijon.infuserevamp.filemanagers.InfuseEffects;
import dijon.infuserevamp.filemanagers.PlayerSave;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class DuelManager {

    public static ArrayList<Duel> masterDuelList = new ArrayList<>();;

    public static Location home = new Location(Bukkit.getWorld("world"), 134, 122, -52);

    public static InfuseDuels plugin;
    public DuelManager(InfuseDuels plugin) {
        DuelManager.plugin = plugin;
    }

    public static void startDuel(Duel duel){
        int[] spawnCoords = WorldManager.getArenaCoords(duel.arenaName);

        Location p1Spawn = new Location(duel.world, spawnCoords[0], spawnCoords[1], spawnCoords[2]);
        Location p2Spawn = new Location(duel.world, spawnCoords[3], spawnCoords[4], spawnCoords[5]);

        duel.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        duel.world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        duel.world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        duel.world.setGameRule(GameRule.MOB_GRIEFING, false);
        duel.world.setTime(0);

        applySettings(duel);

        for(Player p : duel.p1){
            PlayerSave save = EffectFileManager.getPlayerData().get(p.getUniqueId().toString());
            p.getInventory().clear();
            p.teleport(p1Spawn);
            p.setHealth(20);
            p.setSaturation(20);
            p.setGameMode(GameMode.SURVIVAL);
            p.displayName(Component.text(p.getName()).color(TextColor.color(255, 0, 0)));
            EffectFileManager.setPrimaryCooldown(save, System.currentTimeMillis() - 10000);
            EffectFileManager.setSecondaryCooldown(save, System.currentTimeMillis() - 10000);
            for(Player teammate : duel.p1){
                if(teammate.equals(p)) continue;
                save.addTrustedPlayer(teammate);
            }
            if(duel.settings[5]){
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    p.openInventory(DuelMenuManager.primaryEffectMenu);
                }, 10);
            }
        }
        for(Player p : duel.p2){
            PlayerSave save = EffectFileManager.getPlayerData().get(p.getUniqueId().toString());
            p.getInventory().clear();
            p.teleport(p2Spawn);
            p.setHealth(20);
            p.setSaturation(20);
            p.setGameMode(GameMode.SURVIVAL);
            p.displayName(Component.text(p.getName()).color(TextColor.color(255, 0, 0)));
            for(Player teammate : duel.p2){
                if(teammate.equals(p)) continue;
                save.addTrustedPlayer(teammate);
            }
            if(duel.settings[5]){
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    p.openInventory(DuelMenuManager.primaryEffectMenu);
                }, 10);
            }
        }

        sendTexts(duel);

        masterDuelList.add(duel);
    }

    private static void applySettings(Duel duel){
        boolean[] settings = duel.settings;
        duel.world.setGameRule(GameRule.KEEP_INVENTORY, !settings[3]);
    }

    public static void updateDeath(Duel duel, Player p){
        int index = masterDuelList.indexOf(duel);
        masterDuelList.get(index).updateDeath(p); //This is fucking stupid //Future dijon update: Still fucking stupid //Hey dijon again. This is still stupid

        boolean team1Wins = true;
        boolean team2Wins = true;

        Bukkit.getLogger().info("[INFUSEDUELS] TEAM 1");


        for(Player t1 : duel.p1){
            Bukkit.getLogger().info("[INFUSEDUELS] " + t1.getName() + " IS " + t1.getGameMode().equals(GameMode.SPECTATOR));
            if(!t1.getWorld().equals(duel.world)) return;
            if(!t1.getGameMode().equals(GameMode.SPECTATOR)){
                team2Wins = false;
            }
        }

        Bukkit.getLogger().info("[INFUSEDUELS] TEAM 2");

        for(Player t2 : duel.p2){
            Bukkit.getLogger().info("[INFUSEDUELS] " + t2.getName() + " IS " + t2.getGameMode().equals(GameMode.SPECTATOR));
            if(!t2.getWorld().equals(duel.world)) return;
            if(!t2.getGameMode().equals(GameMode.SPECTATOR)){
                team1Wins = false;
            }
        }

        Bukkit.getLogger().info("[INFUSEDUELS] FINALS ARE " + team1Wins + " " + team2Wins);

        if(team1Wins){
            endDuel(duel, duel.p1, duel.p2);
        }else if(team2Wins){
            endDuel(duel, duel.p2, duel.p1);
        }
    }

    public static void sendTexts(Duel duel){
        int[] spawnCoords = WorldManager.getArenaCoords(duel.arenaName);
        Location p1Spawn = new Location(duel.world, spawnCoords[0], spawnCoords[1], spawnCoords[2]);
        Location p2Spawn = new Location(duel.world, spawnCoords[3], spawnCoords[4], spawnCoords[5]);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player p : duel.world.getPlayers()){
                p.sendTitlePart(TitlePart.TITLE, Component.text("5"));
            }
        }, 30);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player p : duel.world.getPlayers()){
                p.sendTitlePart(TitlePart.TITLE, Component.text("4"));
            }
        }, 60);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player p : duel.world.getPlayers()){
                p.sendTitlePart(TitlePart.TITLE, Component.text("3"));
            }
        }, 90);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player p : duel.world.getPlayers()){
                p.sendTitlePart(TitlePart.TITLE, Component.text("2"));
            }
        }, 120);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player p : duel.world.getPlayers()){
                p.sendTitlePart(TitlePart.TITLE, Component.text("1"));
            }
        }, 150);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player p : duel.p1){
                p.getInventory().clear();
                p.teleport(p1Spawn);
                p.setHealth(20);
                p.setSaturation(20);
                p.setGameMode(GameMode.SURVIVAL);
            }
            for(Player p : duel.p2){
                p.getInventory().clear();
                p.teleport(p2Spawn);
                p.setHealth(20);
                p.setSaturation(20);
                p.setGameMode(GameMode.SURVIVAL);
            }
            for(Player player : duel.world.getPlayers()){
                player.sendTitlePart(TitlePart.TITLE, Component.text("Go!"));
                KitApplier.applyKit(player, duel.kit);
            }
        }, 180);
    }

    public static void endDuel(Duel duel, ArrayList<Player> winners, ArrayList<Player> losers){

        for(Player loser : losers){
            loser.setGameMode(GameMode.SPECTATOR);
            loser.sendTitlePart(TitlePart.TITLE, Component.text("You Lose!").color(TextColor.color(255,0,0)));
            EffectFileManager.setPrimary(loser.getUniqueId().toString(), InfuseEffects.NONE);
            EffectFileManager.setSecondary(loser.getUniqueId().toString(), InfuseEffects.NONE);
            PlayerSave save = EffectFileManager.getPlayerData().get(loser.getUniqueId().toString());
            for(Player teammate : losers){
                if(teammate.equals(loser)) continue;
                save.removeTrustedPlayer(teammate);
            }
        }

        for(Player winner : winners){
            winner.sendTitlePart(TitlePart.TITLE, Component.text("You Win!").color(TextColor.color(0,255,0)));
            EffectFileManager.setPrimary(winner.getUniqueId().toString(), InfuseEffects.NONE);
            EffectFileManager.setSecondary(winner.getUniqueId().toString(), InfuseEffects.NONE);
            PlayerSave save = EffectFileManager.getPlayerData().get(winner.getUniqueId().toString());
            for(Player teammate : winners){
                if(teammate.equals(winner)) continue;
                save.removeTrustedPlayer(teammate);
            }
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            WorldManager.freeUpWorld(duel.world.getName());
            for(Player player : duel.world.getPlayers()){
                player.getInventory().clear();
                player.teleport(home);
                player.setGameMode(GameMode.SURVIVAL);
                player.displayName(Component.text(player.getName()));
            }
            WorldManager.clearItems(duel.world);
        }, 100);



        masterDuelList.remove(duel);
    }




    public static Duel getDuel(Player p){
        for(Duel duel : masterDuelList){
            if(duel.p1.contains(p) || duel.p2.contains(p)) return duel;
        }
        return null;
    }

    public static boolean isInDuel(Player p){
        for(Duel duel : masterDuelList){
            if(duel.p1.contains(p) || duel.p2.contains(p)) return true;
        }
        return false;
    }

    public static boolean onSameTeam(Player p1, Player p2){
        Duel duel = getDuel(p1);
        if(duel == null){
            Bukkit.getLogger().info("[INFUSEDUELS] ERROR: DuelManager:228. Duel is Null");
            return false;
        }

        if(duel.p1.contains(p1) && duel.p1.contains(p2)) return true;
        if(duel.p2.contains(p1) && duel.p2.contains(p2)) return true;
        return false;
    }



}
