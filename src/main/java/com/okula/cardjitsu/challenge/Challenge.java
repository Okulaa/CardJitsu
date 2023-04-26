package com.okula.cardjitsu.challenge;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Challenge {
    @Getter
    private Player player1;
    @Getter
    private Player player2;
    @Getter
    private Set<Player> players = new HashSet<>();

    public Challenge(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        players.add(player1);
        players.add(player2);

    }



}
