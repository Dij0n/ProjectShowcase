package dijon.strengthsmp.data;

import dijon.strengthsmp.handlers.MythicHandler;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class PlayerData {

    public Player player;

    public UUID uuid;

    public int strength;

    public String basicAttack;

    public String ultimateAttack;

    public Random random = new Random();

    public int playerHits;

    public double lastUltActivation;

    public boolean dragonEgg = false;

    public PlayerData(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.strength = 2;
        this.basicAttack = getRandomBasicAttack();
        this.ultimateAttack = null;
        this.playerHits = 0;
        this.lastUltActivation = 0;
    }

    public PlayerData() {
        this.strength = 2;
        this.basicAttack = getRandomBasicAttack();
        this.ultimateAttack = null;
        this.playerHits = 0;
        this.lastUltActivation = 0;
    }


    public void incStrength(){
        if(strength < 3){
            strength++;
            player.sendMessage(ChatColor.GREEN + "Your new strength is " + ChatColor.DARK_GREEN + "+" + strength);
        }
        if(strength == 3 && ultimateAttack == null){
            ultimateAttack = getRandomUltimateAttack();
            player.sendMessage(ChatColor.GREEN + "Your new ultimate attack is " + ChatColor.DARK_GREEN + ultimateAttack);
        }
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1 + strength);
    }

    public void decStrength(){
        if(strength > 1){
            strength--;
            player.sendMessage(ChatColor.RED + "Your new strength is " + ChatColor.DARK_RED + "+" + strength);
        }
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1 + strength);
    }


    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1 + strength);
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public int getPlayerHits() {
        return playerHits;
    }

    public void incPlayerHits() {
        playerHits++;
        if(playerHits >= 5){
            MythicHandler.mythicBasicAttack(player, basicAttack);
            playerHits = 0;
        }
    }

    public String getBasicAttack() {
        return this.basicAttack;
    }

    public String getUltimateAttack() {
        return this.ultimateAttack;
    }

    public void setBasicAttack(String basicAttack) {
        this.basicAttack = basicAttack;
    }

    public void setUltimateAttack(String ultimateAttack) {
        this.ultimateAttack = ultimateAttack;
    }

    public boolean isDragonEgg() {
        return dragonEgg;
    }

    public void setDragonEgg(boolean dragonEgg) {
        this.dragonEgg = dragonEgg;
    }

    public String getRandomBasicAttack() {
        return PlayerDataManager.BASIC_ATTACKS[random.nextInt(PlayerDataManager.BASIC_ATTACKS.length)];
    }

    public String getDifferentBasicAttack(){
        String attack = basicAttack;
        while(attack.equals(basicAttack)){
            attack = PlayerDataManager.BASIC_ATTACKS[random.nextInt(PlayerDataManager.BASIC_ATTACKS.length)];
        }
        return attack;
    }

    public String getRandomUltimateAttack() {
        return PlayerDataManager.ULTIMATE_ATTACKS[random.nextInt(PlayerDataManager.ULTIMATE_ATTACKS.length)];
    }

    public String getDifferentUltimateAttack(){
        String attack = ultimateAttack;
        if(attack == null) attack = PlayerDataManager.ULTIMATE_ATTACKS[random.nextInt(PlayerDataManager.ULTIMATE_ATTACKS.length)];
        while(attack.equals(ultimateAttack)){
            attack = PlayerDataManager.ULTIMATE_ATTACKS[random.nextInt(PlayerDataManager.ULTIMATE_ATTACKS.length)];
        }
        return attack;
    }

}
