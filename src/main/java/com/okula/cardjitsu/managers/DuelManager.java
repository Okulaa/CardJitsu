package com.okula.cardjitsu.managers;

import com.okula.cardjitsu.challenge.Challenge;
import com.okula.cardjitsu.duel.Duel;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;

public class DuelManager {

    Set<Duel> duels = new HashSet<>();
    Set<Challenge> challenges = new HashSet<>();
    public DuelManager() {

    }

    public void addDuel(Player player1, Player player2) {
        Duel duel = new Duel(player1, player2, this);
        duels.add(duel);
    }
    public void addChallenge(Player player1, Player player2) {
        Challenge challenge = new Challenge(player1, player2);
        challenges.add(challenge);
    }

    public Challenge getChallenge(Player player1, Player player2) {
        for(Challenge challenge : challenges) {
            if(challenge.getPlayers().contains(player1) && challenge.getPlayers().contains(player2)) {
                return challenge;
            }
        }
        return null;
    }

    public Challenge getPlayerChallenge(Player player1) {
        for(Challenge challenge : challenges) {
            if(challenge.getPlayers().contains(player1)) {
                return challenge;
            }
        }
        return null;
    }
    public Duel getDuel(Player player) {
        for(Duel duel : duels) {
            if(duel.getPlayer1() == player || duel.getPlayer2() == player) {
                return duel;
            }
        }
        return null;
    }
    public void deleteDuel(Player player) {
        Duel duel = getDuel(player);
        if(duel != null) {
            duels.remove(duel);
        }

    }
    public void deleteChallenge(Player player1, Player player2) {
        Challenge challenge = getChallenge(player1, player2);
        if(challenge != null) {
            challenges.remove(challenge);
        }

    }

}
