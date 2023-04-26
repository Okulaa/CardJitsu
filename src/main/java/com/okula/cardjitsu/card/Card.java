package com.okula.cardjitsu.card;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import java.util.concurrent.ThreadLocalRandom;

public class Card {

    @Getter
    final private String color;
    @Getter
    final private String element;
    @Getter
    private Material elementMaterial;
    @Getter
    private ChatColor chatColor;
    @Getter
    private Material cardMaterial;
    public Card() {
        this.color = randomColor();
        this.element = randomElement();

    }

    public String randomColor() {
        int randomInt = ThreadLocalRandom.current().nextInt(1, 4);
        if(randomInt == 1) {
            elementMaterial = Material.LIME_STAINED_GLASS_PANE;
            return "Green";
        }
        if(randomInt == 2) {
            elementMaterial = Material.YELLOW_STAINED_GLASS_PANE;
            return "Yellow";
        }
        if(randomInt == 3) {
            elementMaterial = Material.RED_STAINED_GLASS_PANE;
            return "Red";
        }
        return null;
    }
    public String randomElement() {
        int randomInt = ThreadLocalRandom.current().nextInt(1, 4);
        if(randomInt == 1) {
            cardMaterial = Material.BLAZE_POWDER;
            chatColor = ChatColor.RED;
            return "Fire";
        }
        if(randomInt == 2) {
            cardMaterial = Material.SNOWBALL;
            chatColor = ChatColor.WHITE;
            return "Snow";
        }
        if(randomInt == 3) {
            cardMaterial = Material.HEART_OF_THE_SEA;
            chatColor = ChatColor.BLUE;
            return "Water";
        }
        return null;
    }

}
