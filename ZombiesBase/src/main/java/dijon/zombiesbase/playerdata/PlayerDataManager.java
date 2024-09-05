package dijon.zombiesbase.playerdata;

import dijon.zombiesbase.perks.Perk;
import dijon.zombiesbase.perks.PerkType;
import dijon.zombiesbase.shooting.Gun;
import dijon.zombiesbase.utility.PluginGrabber;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {
    private static final HashMap<UUID, PlayerData> playerData = new HashMap<>();

    //-----INITALIZER-----
    public static void initialize(Player p){
        if(!playerData.containsKey(p.getUniqueId())){
            PlayerData pd = new PlayerData();
            playerData.put(p.getUniqueId(), pd);
        }
    }

    //-------GUNS-------
    public static void setMainGun(Player p, Gun gun){
        playerData.get(p.getUniqueId()).setGun(gun);
    }
    public static Gun getMainGun(Player p){
        return playerData.get(p.getUniqueId()).getGun();
    }


    //-------STATUS------
    public static void setStatus(Player p, Status s){
        playerData.get(p.getUniqueId()).setStatus(s);
    }
    public static Status getStatus(Player p){
        return playerData.get(p.getUniqueId()).getStatus();
    }

    //-------PERKS-------
    public static void addPerk(Player p, Perk perk){
        playerData.get(p.getUniqueId()).addPerk(perk);
        perk.getAction().runP(p);
    }
    public static boolean hasPerk(Player p, Perk perk){
        return playerData.get(p.getUniqueId()).getPerks().contains(perk);
    }
    public static void resetPerks(Player p){
        playerData.get(p.getUniqueId()).getPerks().clear();
        p.clearActivePotionEffects();
    }
    public static ArrayList<Perk> getPerks(Player p){
        return playerData.get(p.getUniqueId()).getPerks();
    }







    //------POINTS------
    public static void increasePoints(Player p, int points){
        if(playerData.containsKey(p.getUniqueId())){
            playerData.get(p.getUniqueId()).incPoints(points);
        }else{
            PlayerData pd = new PlayerData();
            playerData.put(p.getUniqueId(), pd);
        }
    }
    public static void decreasePoints(Player p, int points){
        if(playerData.containsKey(p.getUniqueId())){
            playerData.get(p.getUniqueId()).decPoints(points);
        }else{
            PlayerData pd = new PlayerData();
            playerData.put(p.getUniqueId(), pd);
        }
    }
    public static int getPoints(Player p){
        if(playerData.containsKey(p.getUniqueId())){
            return playerData.get(p.getUniqueId()).getPoints();
        }else{
            PlayerData pd = new PlayerData();
            playerData.put(p.getUniqueId(), pd);
            return 0;
        }
    }



    public static void reloadAttempt(Player p){

        if(getMainGun(p).getAmmo() >= getMainGun(p).getMaxClip()) return;
        if(getStatus(p).equals(Status.RELOADING)) return;

        long reloadTicks = (long) (getMainGun(p).getReloadTime() * 20);

        if(PlayerDataManager.hasPerk(p, PerkType.SPEEDCOLA)){ //PERK CHECK
            reloadTicks /= 2;
        }

        if(PlayerDataManager.hasPerk(p, PerkType.ELECTRICCHERRY)){ //PERK CHECK
            p.getWorld().strikeLightning(p.getLocation());
        }

        setStatus(p, Status.RELOADING);

        //PRE-RELOAD EFFECTS

        p.playSound(p, Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE, 10, 0.75f);
        p.getWorld().spawnParticle(getMainGun(p).getParticle(), getGunSmokeLocation(p), 5, new Particle.DustOptions(Color.SILVER, 1.0F));

        //POST-RELOAD EFFECTS

        Bukkit.getScheduler().runTaskLater(PluginGrabber.plugin, () -> {
            p.playSound(p, Sound.BLOCK_WOODEN_TRAPDOOR_OPEN, 10, 0.75f);
            p.getWorld().spawnParticle(getMainGun(p).getParticle(), getGunSmokeLocation(p), 5, new Particle.DustOptions(Color.YELLOW, 1.0F));
            setStatus(p, Status.IDLE);
            getMainGun(p).reload();
        }, reloadTicks);


    }






    //--------HELPERS--------

    public static Location getGunSmokeLocation(Player p){
        //---Vector magic to place the empty ammo smoke---
        Vector playerDirectionYZeroed = p.getEyeLocation().getDirection().setY(0);
        playerDirectionYZeroed.rotateAroundY(Math.PI/2);
        Vector playerLookPerpAxis = p.getEyeLocation().getDirection().crossProduct(playerDirectionYZeroed);
        Vector rotatedAroundPep = p.getEyeLocation().getDirection().rotateAroundAxis(playerLookPerpAxis, -Math.PI/4);
        rotatedAroundPep.rotateAroundAxis(p.getEyeLocation().getDirection(), Math.PI/9);
        return p.getEyeLocation().add(rotatedAroundPep.multiply(0.7));
        //---Vector magic to place the empty ammo smoke---
    }




}
