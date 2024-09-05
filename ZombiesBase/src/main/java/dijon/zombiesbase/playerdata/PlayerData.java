package dijon.zombiesbase.playerdata;

import dijon.zombiesbase.perks.Perk;
import dijon.zombiesbase.shooting.Gun;
import dijon.zombiesbase.shooting.GunType;

import java.util.ArrayList;

public class PlayerData {

    Gun currentlyEquipped;
    int points;
    Status status;
    ArrayList<Perk> perks;

    public PlayerData(){
        currentlyEquipped = GunType.NONE;
        status = Status.IDLE;
        points = 0;
        perks = new ArrayList<>();
    }


    //-------POINTS----------
    public void incPoints(int amount){
        points += amount;
    }
    public void decPoints(int amount){
        points -= amount;
    }


    //-------PERKS----------
    public void addPerk(Perk perk){
        perks.add(perk);
    }
    public boolean hasPerk(Perk perk){
        return perks.contains(perk);
    }

    public Gun getGun() {
        return currentlyEquipped;
    }
    public void setGun(Gun currentlyEquipped) {
        this.currentlyEquipped = currentlyEquipped;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public ArrayList<Perk> getPerks() {
        return perks;
    }
}
