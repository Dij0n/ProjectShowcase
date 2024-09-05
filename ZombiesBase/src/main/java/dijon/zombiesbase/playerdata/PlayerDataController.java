package dijon.zombiesbase.playerdata;

import dijon.zombiesbase.perks.Perk;
import dijon.zombiesbase.shooting.Gun;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerDataController {

    //THIS CLASS IS A PASSTHROUGH INTO PLAYERDATAMANAGER
    //----------------------------------------------------
    //All this does is store a Player variable and pass that value
    //through to the manager. This class is fairly redundant, but
    //helps clean up code and reduce constant calls to PlayerDataManager

    Player p;

    public PlayerDataController(Player p) {
        this.p = p;
    }

    public void setMainGun(Gun gun){
        PlayerDataManager.setMainGun(p, gun);
    }
    public Gun getMainGun(){
        return PlayerDataManager.getMainGun(p);
    }
    public void setStatus(Status s){
        PlayerDataManager.setStatus(p, s);
    }
    public Status getStatus(){
        return PlayerDataManager.getStatus(p);
    }

    public void addPerk(Perk perk){
        PlayerDataManager.addPerk(p, perk);
    }
    public boolean hasPerk(Perk perk){
        return PlayerDataManager.hasPerk(p, perk);
    }
    public void resetPerks(){
        PlayerDataManager.resetPerks(p);
    }
    public ArrayList<Perk> getPerks(){
        return PlayerDataManager.getPerks(p);
    }






    public void increasePoints(int points){
        PlayerDataManager.increasePoints(p, points);
    }
    public void decreasePoints(int points){
        PlayerDataManager.decreasePoints(p, points);
    }
    public int getPoints(){
        return PlayerDataManager.getPoints(p);
    }
    public void reloadAttempt(){
        PlayerDataManager.reloadAttempt(p);
    }
    public Location getGunSmokeLocation(){
        return PlayerDataManager.getGunSmokeLocation(p);
    }

}
