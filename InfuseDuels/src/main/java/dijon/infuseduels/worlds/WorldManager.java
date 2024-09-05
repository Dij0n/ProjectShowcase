package dijon.infuseduels.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WorldManager {

    public static HashMap<String, ArrayList<World>> masterWorldMap = new HashMap<>();
    public static HashMap<String, Boolean> worldAvailabilityMap = new HashMap<>();
    public static HashMap<String, int[]> coordsMap = new HashMap<>();

    public WorldManager(){
        initializeWorlds();
        initializeCoords();
    }

    public static String convertItemToArena(String itemName){
        return switch (itemName) {
            case "PRISMARINE" -> "arena1";
            case "STRIPPED_CRIMSON_STEM" -> "arena2";
            case "MAGMA_BLOCK" -> "arena3";
            default -> null;
        };
    }

    public static boolean getArenaAvailability(String arena){
        ArrayList<World> worlds = masterWorldMap.get(arena);
        for(World world : worlds){
            if(!worldAvailabilityMap.get(world.getName())){
                return true;
            }
        }
        return false;
    }

    public static World getWorldByArena(String arena){
        ArrayList<World> worlds = masterWorldMap.get(arena);
        for(World world : worlds){
            if(!worldAvailabilityMap.get(world.getName())){
                worldAvailabilityMap.put(world.getName(), true);
                return world;
            }
        }
        return null;
    }

    public static int[] getArenaCoords(String arenaName){
        return coordsMap.get(arenaName);
    }

    public static void freeUpWorld(String worldName){
        worldAvailabilityMap.put(worldName, false);
    }

    public static void initializeWorlds(){
        ArrayList<World> arenas1 = new ArrayList<>();
        arenas1.add(Bukkit.getWorld("duels1"));
        ArrayList<World> arenas2 = new ArrayList<>();
        arenas2.add(Bukkit.getWorld("duels2"));
        ArrayList<World> arenas3 = new ArrayList<>();
        arenas3.add(Bukkit.getWorld("duels3"));
        ArrayList<World> arenas4 = new ArrayList<>();
        arenas4.add(Bukkit.getWorld("duels4"));
        ArrayList<World> arenas5 = new ArrayList<>();
        arenas5.add(Bukkit.getWorld("duels5"));
        masterWorldMap.put("arena1", arenas1);
        masterWorldMap.put("arena2", arenas2);
        masterWorldMap.put("arena3", arenas3);
        masterWorldMap.put("arena4", arenas4);
        masterWorldMap.put("arena5", arenas5);

        for(ArrayList<World> worlds : masterWorldMap.values()){
            for(World world : worlds){
                worldAvailabilityMap.put(world.getName(), false);
            }
        }
    }

    public static void initializeCoords(){
        int[] arena1Coords = {0, 81, -34, 0, 81, 34};
        int[] arena2Coords = {-19, 100, 0, 19, 100, 0};
        int[] arena3Coords = {20, 23, 16, 20, 23, -56};
        int[] arena4Coords = {10, -58, 0, -10, -58, 0};
        int[] arena5Coords = {10, -58, 0, -10, -58, 0};
        coordsMap.put("arena1", arena1Coords);
        coordsMap.put("arena2", arena2Coords);
        coordsMap.put("arena3", arena3Coords);
        coordsMap.put("arena4", arena4Coords);
        coordsMap.put("arena5", arena5Coords);
    }




    //HELPER

    public static void clearItems(World world){
        List<Entity> list = world.getEntities();
        for (Entity entity : list) {
            if (entity instanceof Item || entity instanceof Arrow) {
                entity.remove();
            }
        }
    }
}
