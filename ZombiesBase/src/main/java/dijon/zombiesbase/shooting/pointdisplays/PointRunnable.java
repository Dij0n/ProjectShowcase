package dijon.zombiesbase.shooting.pointdisplays;

import dijon.zombiesbase.utility.PluginGrabber;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class PointRunnable extends BukkitRunnable {

    TextDisplay textDisplay;
    byte opValue;
    Player p;
    Location randomDirectionLoc;

    public PointRunnable(TextDisplay textDisplay, Player p){
        this.textDisplay = textDisplay;
        this.p = p;
        randomDirectionLoc = textDisplay.getLocation().add(getRandomVector());
        runTaskTimer(PluginGrabber.plugin, 0, 1);
    }


    @Override
    public void run() {

        opValue = textDisplay.getTextOpacity();
        opValue -= 10;

        textDisplay.setTextOpacity(opValue);
        textDisplay.teleport(getLerped(textDisplay.getLocation(), randomDirectionLoc));

        if((opValue & 0xFF) <= 5){
            textDisplay.remove();
            cancel();
        }

    }


    public Vector getRandomVector(){
        //---Vector magic to get random launch angle---
        org.bukkit.util.Vector playerDirectionYZeroed = p.getEyeLocation().getDirection().setY(0);
        playerDirectionYZeroed.rotateAroundY(Math.PI/2);
        org.bukkit.util.Vector playerLookPerpAxis = p.getEyeLocation().getDirection().crossProduct(playerDirectionYZeroed);
        double random = Math.random();
        random *= Math.PI/2;
        org.bukkit.util.Vector rotated = playerLookPerpAxis.rotateAroundAxis(p.getEyeLocation().getDirection(), random);
        return rotated.normalize();
        //---Vector magic to get random launch angle---
    }

    public Location getLerped(Location currentLoc, Location finalLoc){
        Location finalCopy = finalLoc.clone();
        Location currentCopy = currentLoc.clone();

        Location curToFinal = finalCopy.subtract(currentCopy);
        curToFinal.multiply(0.25);

        return currentCopy.add(curToFinal);
    }

}
