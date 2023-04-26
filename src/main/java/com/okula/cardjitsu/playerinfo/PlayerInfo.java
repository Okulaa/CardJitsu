package com.okula.cardjitsu.playerinfo;

import com.okula.cardjitsu.enums.BeltEnums;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class PlayerInfo {
    @Getter
    private final String name;
    @Getter
    private int wins;
    @Setter
    private transient Player player;
    private transient BeltEnums beltEnum;

    public PlayerInfo(Player player) {
        this.player = player;
        this.name = player.getName();
        this.beltEnum = BeltEnums.WHITE;

    }

    private BeltEnums getBelt() {
        for(BeltEnums beltEnum : BeltEnums.values()) {
            if (wins >= beltEnum.getWins()) {
                return beltEnum;
            }
        }
        return null;
    }

    private void updateBelt(Player player1) {
        if(!getBelt().equals(beltEnum)) {
            BeltEnums previousBelt = beltEnum;
            beltEnum = getBelt();
            promotion();
        }
    }

    private void promotion() {
        final TextComponent textComponent = Component.text()
                .content("Card Jitsu ")
                .color(NamedTextColor.YELLOW)
                        .append(Component.text(">> ")
                                .color(NamedTextColor.GRAY))
                                        .append(Component.text("Congratulations you are now ")
                                                .color(NamedTextColor.WHITE))
                                                    .append(Component.text(beltEnum.name().toLowerCase())
                                                            .color(TextColor.fromHexString(beltEnum.getColor())))
                                                                .append(Component.text(" belt")
                                                                        .color(NamedTextColor.WHITE))
                                                                                .build();
            player.sendMessage(textComponent);
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
    public void addWins(Player player1, int wins) {
        this.wins = this.wins + wins;
        updateBelt(player1);
    }
    public void removeNumberOfWins(int wins) {
        this.wins =- wins;
    }

}
