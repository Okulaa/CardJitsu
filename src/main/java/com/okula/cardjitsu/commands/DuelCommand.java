package com.okula.cardjitsu.commands;

import com.okula.cardjitsu.challenge.Challenge;
import com.okula.cardjitsu.managers.DuelManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {
    DuelManager duelManager;
    public DuelCommand(DuelManager duelManager) {
        this.duelManager = duelManager;

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // /cj duel <player>
        // /cj accept <player>
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }
        Player player1 = (Player) sender;
        if(args[0].equals("accept")) {
            if(args.length != 2) {
                player1.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + " >> " + ChatColor.WHITE + "Usage" + ChatColor.GREEN + " /cj duel accept <player>");
                return false;
            }
            Challenge challenge = duelManager.getChallenge(Bukkit.getPlayer(args[1]), player1);
            if(challenge == null) {
                player1.sendMessage("There is no challenge.");
                return false;
            }
            if(challenge.getPlayer2() != player1) {
                player1.sendMessage("Only opponent can accept the challenge.");
                return false;
            }
            duelManager.addDuel(challenge.getPlayer1(), challenge.getPlayer2());
            duelManager.deleteChallenge(challenge.getPlayer1(), challenge.getPlayer2());
            return true;
        }
        if(!args[0].equals("duel")) return false;
        if(args.length < 2) {
            player1.sendMessage(ChatColor.YELLOW + "Card Jitsu" + ChatColor.GRAY + " >> " + ChatColor.WHITE + " Usage" + ChatColor.GREEN + " /cj duel <player>");
            return false;
        }
        if(args.length == 2) {
            if(Bukkit.getPlayer(args[1]) == null) {
                player1.sendMessage(ChatColor.YELLOW + "Card Jitsu" + ChatColor.GRAY + " >> " + ChatColor.WHITE + " Usage" + ChatColor.GREEN + " /cj duel <player>");
                return false;
            }
            Challenge challenge = duelManager.getPlayerChallenge(player1);
            if(challenge != null) {
                Player player2 = challenge.getPlayer2();
                player1.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + " >> " + ChatColor.WHITE + "You can't request more than one duel, currently waiting for duel with " + ChatColor.GREEN + player2.getName() + ChatColor.WHITE + ".");
                return false;
            }
            Player player2 = Bukkit.getPlayer(args[1]);
            if(player1 == player2) {
                player1.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + " >> " + ChatColor.RED + "You can't request a duel with yourself.");
                return false;
            }
            duelManager.addChallenge(player1,player2);
            player1.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.WHITE + "Duel with " + ChatColor.GREEN + player2.getName() + ChatColor.WHITE + " requested.");
            final TextComponent textComponent = Component.text()
                    .content("Card Jitsu ")
                    .color(NamedTextColor.YELLOW)
                    .append(Component.text(">> ")
                            .color(NamedTextColor.GRAY))
                    .append(Component.text(player1.getName())
                            .color(NamedTextColor.GREEN))
                    .append(Component.text(" invited you to a")
                            .color(NamedTextColor.WHITE))
                    .append(Component.text(" Card Jitsu")
                            .color(NamedTextColor.YELLOW))
                    .append(Component.text(" duel.\n")
                            .color(NamedTextColor.WHITE))
                    .append(Component.text(" ACCEPT ")
                            .decoration(TextDecoration.BOLD, true)
                            .clickEvent(ClickEvent.runCommand("/cj accept " + player1.getName()))
                            .hoverEvent(HoverEvent.showText(Component.text("ACCEPT").color(NamedTextColor.GREEN)))
                            .color(NamedTextColor.GREEN))
                    .append(Component.text("|").color(NamedTextColor.WHITE))
                    .append(Component.text(" DECLINE")
                            .decoration(TextDecoration.BOLD, true)
                            .clickEvent(ClickEvent.runCommand("/cj duel " + player1.getName() + " cancel"))
                            .hoverEvent(HoverEvent.showText(Component.text("DECLINE").color(NamedTextColor.RED)))
                            .color(NamedTextColor.RED))
                    .build();
            player2.sendMessage(textComponent);
            return false;

        }
        Player player2 = Bukkit.getPlayer(args[1]);
        if(!(args[2].equals("cancel"))) return false;

        if(duelManager.getChallenge(player1, player2) != null) {
            duelManager.deleteChallenge(player1,player2);
            player1.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.WHITE + "Duel with " + ChatColor.GREEN + player2.getName() + ChatColor.WHITE + " has been" + ChatColor.RED + " canceled.");
            player2.sendMessage(ChatColor.YELLOW + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.WHITE + "Duel with " + ChatColor.GREEN + player1.getName() + ChatColor.WHITE + " has been" + ChatColor.RED + " canceled.");
            return false;
        }
        player1.sendMessage("No challenge found.");
        return true;
    }
}
