package dijon.zombiesbase.hud;

import dijon.zombiesbase.playerdata.PlayerDataController;
import dijon.zombiesbase.shooting.Gun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TemporaryHUD extends BukkitRunnable {

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            PlayerDataController pd = new PlayerDataController(p);

            Gun gun = pd.getMainGun();
            int ammo = gun.getAmmo();
            int reserveAmmo = gun.getReserveAmmo();
            int points = pd.getPoints();

            String space = HUDText.SPACE.toString();
            String ammoString = getBigStringOfNum(ammo);
            String reserveString = getSmallStringOfNum(reserveAmmo);


            p.sendActionBar(space + ammoString + " " + reserveString);


        }
    }

    public String intToBigText(int num){
        HUDText text = switch (num) {
            case 1 -> HUDText.ONE;
            case 2 -> HUDText.TWO;
            case 3 -> HUDText.THREE;
            case 4 -> HUDText.FOUR;
            case 5 -> HUDText.FIVE;
            case 6 -> HUDText.SIX;
            case 7 -> HUDText.SEVEN;
            case 8 -> HUDText.EIGHT;
            case 9 -> HUDText.NINE;
            default -> HUDText.ZERO;
        };
        return text.toString();
    }

    public String intToSmallText(int num){
        HUDText text = switch (num) {
            case 1 -> HUDText.ONES;
            case 2 -> HUDText.TWOS;
            case 3 -> HUDText.THREES;
            case 4 -> HUDText.FOURS;
            case 5 -> HUDText.FIVES;
            case 6 -> HUDText.SIXS;
            case 7 -> HUDText.SEVENS;
            case 8 -> HUDText.EIGHTS;
            case 9 -> HUDText.NINES;
            default -> HUDText.ZEROS;
        };
        return text.toString();
    }

    public String getBigStringOfNum(int number){
        //MEMOIZATION SO THAT NUMBER ISN'T CALCULATED INFINITELY
        if(number < 10){
            return intToBigText(number);
        }
        if(number < 100){
            String string = "";

            string += intToBigText((int) Math.floor((double) number / 10));
            string += intToBigText(number % 10);
            return string;
        }
        if(number < 1000){
            String string = "";
            string += intToBigText((int) Math.floor((double) number / 100));
            string += intToBigText((int) Math.floor((double) (number % 100) / 10));
            string += intToBigText(number % 10);
            return string;
        }
        return "";
    }

    public String getSmallStringOfNum(int number){
        //MEMOIZATION SO THAT NUMBER ISN'T CALCULATED INFINITELY
        if(number < 10){
            return intToSmallText(number);
        }
        if(number < 100){
            String string = "";

            string += intToSmallText((int) Math.floor((double) number / 10));
            string += intToSmallText(number % 10);
            return string;
        }
        if(number < 1000){
            String string = "";
            string += intToSmallText((int) Math.floor((double) number / 100));
            string += intToSmallText((int) Math.floor((double) (number % 100) / 10));
            string += intToSmallText(number % 10);
            return string;
        }
        return "";
    }
}
