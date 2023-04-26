package com.okula.cardjitsu.duel;

import com.okula.cardjitsu.CardJitsu;
import com.okula.cardjitsu.card.Card;
import com.okula.cardjitsu.card.CardInfo;
import com.okula.cardjitsu.gson.GsonStorage;
import com.okula.cardjitsu.guis.DuelGui;
import com.okula.cardjitsu.guis.MatchGui;
import com.okula.cardjitsu.guis.WinGui;
import com.okula.cardjitsu.managers.DuelManager;
import com.okula.cardjitsu.playerinfo.PlayerInfo;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;

public class Duel {

    private final DuelManager duelManager;
    private final Duel duel;
    @Getter
    private Player player1;
    @Getter
    private Player player2;
    private Player winner;
    private DuelGui duelGui;
    @Getter
    Set<Card> player1CardsWon = new HashSet<>();
    @Getter
    Set<Card> player2CardsWon = new HashSet<>();
    private final GsonStorage gsonStorage;
    public Duel(Player player1, Player player2, DuelManager duelManager) {
        this.gsonStorage = CardJitsu.getInstance().getGsonStorage();
        this.duelManager = duelManager;
        this.player1 = player1;
        this.player2 = player2;
        duel = this;
        duelGui = new DuelGui(player1, player2, this);
        duelGui.openInventory(player1);
        duelGui.openInventory(player2);
    }

    public void readyCheck() {
        if(duelGui.getCardsGui().isReady() && duelGui.getCardsGui2().isReady()) {
            Card winCard = roundWinner(duelGui.getCardsGui().getSelectedCard(), duelGui.getCardsGui2().getSelectedCard());
            if(gameWinCheck(winCard)) {
                WinGui winGui = new WinGui(winner, player1,player2);
                winGui.openInventory(player1);
                winGui.openInventory(player2);
                PlayerInfo playerInfo = gsonStorage.getPlayerInfo(winner.getName());
                winner.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.WHITE + "You now have " + ChatColor.GREEN + (playerInfo.getWins() + 1) + ChatColor.WHITE +  " " + oneWinCheck(playerInfo.getWins()));
                playerInfo.addWins(winner,1);
                gsonStorage.save(playerInfo);
                return;
            }
            duelGui.getCardsGui().newCard();
            duelGui.getCardsGui2().newCard();
            duelGui.getCardsGui().setReady(false);
            duelGui.getCardsGui2().setReady(false);
            duelGui.getCardsGui().getCardsGuiInv().clear();
            duelGui.getCardsGui2().getCardsGuiInv().clear();
            duelGui.getCardsGui().initializeItems();
            duelGui.getCardsGui2().initializeItems();
            MatchGui matchGui1 = new MatchGui(player1, player2, winCard, duelGui.getCardsGui(), duelGui.getCardsGui2());
            MatchGui matchGui2 = new MatchGui(player1, player2, winCard, duelGui.getCardsGui(), duelGui.getCardsGui2());
            matchGui1.openInventory(player1);
            matchGui2.openInventory(player2);

        }
    }

    private String oneWinCheck(int wins) {
        if(wins != 0) {
            return "wins";
        }
        return "win";

    }

    public Card roundWinner(Card card1, Card card2) {
        String element1 = card1.getElement();
        String element2 = card2.getElement();

        if(element1.equals("Fire")) {
            if(element2.equals("Snow")) {
                if(player1CardsWon.size() < 1) {
                    player1CardsWon.add(card1);
                }
                if(!cardCheck(player1CardsWon, card1)) {
                    player1CardsWon.add(card1);
                }

                return card1;

            }
            if(element2.equals("Water")) {
                if(player2CardsWon.size() < 1) {
                    player2CardsWon.add(card2);
                }
                if(!cardCheck(player2CardsWon, card2)) {
                    player2CardsWon.add(card2);
                }
                return card2;

            }
            if(element2.equals("Fire")) {
                return null;

            }
        }
        if(element1.equals("Snow")) {
            if(element2.equals("Snow")) {
                return null;

            }
            if(element2.equals("Water")) {
                if(player1CardsWon.size() < 1) {
                    player1CardsWon.add(card1);
                }
                if(!cardCheck(player1CardsWon, card1)) {
                    player1CardsWon.add(card1);
                }
                return card1;

            }
            if(element2.equals("Fire")) {
                if(player2CardsWon.size() < 1) {
                    player2CardsWon.add(card2);
                }
                if(!cardCheck(player2CardsWon, card2)) {
                    player2CardsWon.add(card2);
                }
                return card2;

            }
        }
        if(element1.equals("Water")) {
            if(element2.equals("Snow")) {
                if(player2CardsWon.size() < 1) {
                    player2CardsWon.add(card2);
                }
                if(!cardCheck(player2CardsWon, card2)) {
                    player2CardsWon.add(card2);
                }
                return card2;

            }
            if(element2.equals("Water")) {
                return null;

            }
            if(element2.equals("Fire")) {
                if(player1CardsWon.size() < 1) {
                    player1CardsWon.add(card1);
                }
                if(!cardCheck(player1CardsWon, card1)) {
                    player1CardsWon.add(card1);
                }
                return card1;

            }
        }
        return null;

    }

    public boolean cardCheck(Set<Card> cards, Card card) {
        for(Card checkCard : cards) {
            if(checkCard.getElement().equals(card.getElement())) {
                if(checkCard.getColor().equals(card.getColor())) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean gameWinCheck(Card winCard) {
        if(winCard == null) return false;
        if(winCard == duelGui.getCardsGui().getSelectedCard()) {
            int fireCards = 0;
            int snowCards = 0;
            int waterCards = 0;
            CardInfo cardInfoFire = new CardInfo(0,0,0);
            CardInfo cardInfoSnow = new CardInfo(0,0,0);
            CardInfo cardInfoWater = new CardInfo(0,0,0);
            for(Card card : player1CardsWon) {

                if(card.getElement().equals("Fire")) {
                    fireCards++;
                    if(card.getColor().equals("Green")) {
                        cardInfoFire.setGreen(1);
                    }
                    if(card.getColor().equals("Yellow")) {
                        cardInfoFire.setYellow(1);
                    }
                    if(card.getColor().equals("Red")) {
                        cardInfoFire.setRed(1);
                    }
                    continue;
                }
                if(card.getElement().equals("Snow")) {
                    snowCards++;
                    if(card.getColor().equals("Green")) {
                        cardInfoSnow.setGreen(1);
                    }
                    if(card.getColor().equals("Yellow")) {
                        cardInfoSnow.setYellow(1);
                    }
                    if(card.getColor().equals("Red")) {
                        cardInfoSnow.setRed(1);
                    }
                    continue;
                }
                if(card.getElement().equals("Water")) {
                    if(card.getColor().equals("Green")) {
                        cardInfoWater.setGreen(1);
                    }
                    if(card.getColor().equals("Yellow")) {
                        cardInfoWater.setYellow(1);
                    }
                    if(card.getColor().equals("Red")) {
                        cardInfoWater.setRed(1);
                    }
                    waterCards++;
                }

            }
            if(fireCards == 3 || snowCards == 3 || waterCards == 3) {
                winner = player1;
                return true;
            }
            if(cardInfoFire.getGreen() == 1 && cardInfoSnow.getYellow() == 1 && cardInfoWater.getRed() == 1) {
                winner = player1;
                return true;
            }
            if(cardInfoFire.getGreen() == 1 && cardInfoSnow.getRed() == 1 && cardInfoWater.getYellow() == 1) {
                winner = player1;
                return true;
            }
            if(cardInfoFire.getYellow() == 1 && cardInfoSnow.getGreen() == 1 && cardInfoWater.getRed() == 1) {
                winner = player1;
                return true;
            }
            if(cardInfoFire.getYellow() == 1 && cardInfoSnow.getRed() == 1 && cardInfoWater.getGreen() == 1) {
                winner = player1;
                return true;
            }
            if(cardInfoFire.getRed() == 1 && cardInfoSnow.getYellow() == 1 && cardInfoWater.getGreen() == 1) {
                winner = player1;
                return true;
            }
            if(cardInfoFire.getRed() == 1 && cardInfoSnow.getGreen() == 1 && cardInfoWater.getYellow() == 1) {
                winner = player1;
                return true;
            }


        }

        if(winCard == duelGui.getCardsGui2().getSelectedCard()) {
            int fireCards = 0;
            int snowCards = 0;
            int waterCards = 0;
            CardInfo cardInfoFire = new CardInfo(0,0,0);
            CardInfo cardInfoSnow = new CardInfo(0,0,0);
            CardInfo cardInfoWater = new CardInfo(0,0,0);
            for(Card card : player2CardsWon) {
                if(card.getElement().equals("Fire")) {
                    fireCards++;
                    if(card.getColor().equals("Green")) {
                        cardInfoFire.setGreen(1);
                    }
                    if(card.getColor().equals("Yellow")) {
                        cardInfoFire.setYellow(1);
                    }
                    if(card.getColor().equals("Red")) {
                        cardInfoFire.setRed(1);
                    }
                    continue;
                }
                if(card.getElement().equals("Snow")) {
                    snowCards++;
                    if(card.getColor().equals("Green")) {
                        cardInfoSnow.setGreen(1);
                    }
                    if(card.getColor().equals("Yellow")) {
                        cardInfoSnow.setYellow(1);
                    }
                    if(card.getColor().equals("Red")) {
                        cardInfoSnow.setRed(1);
                    }
                    continue;
                }
                if(card.getElement().equals("Water")) {
                    waterCards++;
                    if(card.getColor().equals("Green")) {
                        cardInfoWater.setGreen(1);
                    }
                    if(card.getColor().equals("Yellow")) {
                        cardInfoWater.setYellow(1);
                    }
                    if(card.getColor().equals("Red")) {
                        cardInfoWater.setRed(1);
                    }
                }

            }
            if(fireCards == 3 || snowCards == 3 || waterCards == 3) {
                winner = player2;
                return true;
            }
            if(cardInfoFire.getGreen() == 1 && cardInfoSnow.getYellow() == 1 && cardInfoWater.getRed() == 1) {
                winner = player2;
                return true;
            }
            if(cardInfoFire.getGreen() == 1 && cardInfoSnow.getRed() == 1 && cardInfoWater.getYellow() == 1) {
                winner = player2;
                return true;
            }
            if(cardInfoFire.getYellow() == 1 && cardInfoSnow.getGreen() == 1 && cardInfoWater.getRed() == 1) {
                winner = player2;
                return true;
            }
            if(cardInfoFire.getYellow() == 1 && cardInfoSnow.getRed() == 1 && cardInfoWater.getGreen() == 1) {
                winner = player2;
                return true;
            }
            if(cardInfoFire.getRed() == 1 && cardInfoSnow.getYellow() == 1 && cardInfoWater.getGreen() == 1) {
                winner = player2;
                return true;
            }
            if(cardInfoFire.getRed() == 1 && cardInfoSnow.getGreen() == 1 && cardInfoWater.getYellow() == 1) {
                winner = player2;
                return true;
            }


        }

        return false;
    }

}
