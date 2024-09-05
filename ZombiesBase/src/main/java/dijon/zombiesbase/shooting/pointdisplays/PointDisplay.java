package dijon.zombiesbase.shooting.pointdisplays;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class PointDisplay {

    Player p;
    String message;
    Location spawnLoation;
    float scale;

    TextDisplay textDisplay;

    public PointDisplay(Player p, String message, Location spawnLoation, float scale) {
        this.message = message;
        this.spawnLoation = spawnLoation;
        this.scale = scale;

        textDisplay = (TextDisplay) spawnLoation.getWorld().spawnEntity(spawnLoation, EntityType.TEXT_DISPLAY);

        Transformation transformation = new Transformation(new Vector3f(0,0,0), new AxisAngle4f(0,0,0,0), new Vector3f(scale, scale, scale), new AxisAngle4f(0,0,0,0));

        textDisplay.text(Component.text(message).color(TextColor.color(255,255,0)));
        textDisplay.setBackgroundColor(Color.fromARGB(0, 255, 0 , 0));
        textDisplay.setTransformation(transformation);
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.setTextOpacity((byte) 255);
        textDisplay.setShadowed(true);

        new PointRunnable(textDisplay, p);
    }



}
