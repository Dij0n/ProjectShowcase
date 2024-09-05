package dijon.zombiesbase.utility;

import dijon.zombiesbase.playerdata.PlayerData;
import dijon.zombiesbase.playerdata.PlayerDataManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Random;

public class Raycaster {

    enum TargetType{
        BLOCK,
        ENTITY,
        AIR
    }

    //These are Initalized
    Location initalLoc;
    Player p;
    double maxDist;
    double dist = 0;
    double interval;
    Particle particle;
    Particle.DustOptions dust;
    Vector bloomifiedVector;


    //These are found using the Raycast() method
    TargetType target;
    Location finalLoc;
    Block blockFound = null;
    Collection<Entity> entities = null;

    public Raycaster(Player p, double maxDist) {
        this.p = p;
        this.initalLoc = p.getEyeLocation();
        this.maxDist = maxDist;

        raycast(false);
    }

    public Raycaster(Player p, double maxDist, double interval, Particle particle, Particle.DustOptions dust, boolean bloomEnabled) {
        this.p = p;
        this.initalLoc = p.getEyeLocation();
        this.maxDist = maxDist;
        this.particle = particle;
        this.dust = dust;
        this.interval = interval;

        if(bloomEnabled){
            bloomifiedVector = randomBloomModifier(p.getEyeLocation().getDirection());
        }else{
            bloomifiedVector = p.getEyeLocation().getDirection();
        }

        bloomifiedVector.multiply(0.5);


        raycast(true);
    }


    private void moveForward(){
        initalLoc.add(bloomifiedVector);
    }



    private boolean blockCheck(){
        return p.getWorld().getBlockAt(initalLoc).getType() != Material.AIR;
    }
    private boolean entityCheck(){
        entities = p.getWorld().getNearbyEntities(initalLoc, 0.2, 0.2, 0.2);
        entities.remove(p);
        entities.removeIf(e -> !(e instanceof LivingEntity));
        return !entities.isEmpty();
    }

    private void raycast(boolean intervalEnabled){
        dist += 0.5;
        if(dist % interval == 0 && intervalEnabled){
            initalLoc.getWorld().spawnParticle(particle, initalLoc, 5, dust);
        }
        if(!(blockCheck() || entityCheck())){
            if(dist >= maxDist){
                target = TargetType.AIR;
                finalLoc = initalLoc;
                return;
            };
            moveForward();
            raycast(intervalEnabled);
        }else{
            if(blockCheck()){
                blockFound = p.getWorld().getBlockAt(initalLoc);
                target = TargetType.BLOCK;
            }
            if(entityCheck()){
                target = TargetType.ENTITY; //Entities have a higher priority, and so are checked last
            }
            finalLoc = initalLoc;
        }

    }

    public boolean isHeadshot(){
        Entity victim = getEntity();
        if (victim == null) return false;
        double distToFloor = victim.getLocation().distance(finalLoc);

        return (distToFloor >= 1.64D);
    }

    public Vector randomBloomModifier(Vector eyeVector){

        Vector newEyeVector = eyeVector.clone();

        Vector xAxis = eyeVector.rotateAroundY(90);
        xAxis.setY(0);
        Vector yAxis = new Vector(0, 1, 0);

        double range = PlayerDataManager.getMainGun(p).getBloomAngle();

        newEyeVector.rotateAroundAxis(yAxis, generateRandomRotation(range));
        newEyeVector.rotateAroundAxis(xAxis, generateRandomRotation(range));

        newEyeVector.normalize();

        return newEyeVector;
    }

    public double generateRandomRotation(double range){
        double modifier = Math.random();
        modifier *= range * 2;
        modifier -= range;
        return modifier;
    }

    public TargetType getTarget() {
        return target;
    }
    public Location getFinalLoc() {
        return finalLoc;
    }
    public Block getBlockFound() {
        return blockFound;
    }
    public Collection<Entity> getEntities() {
        if(entities.isEmpty()) return null;
        return entities;
    }
    public Entity getEntity() {
        if(entities.isEmpty()) return null;
        return entities.iterator().next();
    }
}
