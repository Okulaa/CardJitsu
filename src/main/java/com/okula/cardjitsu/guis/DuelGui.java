package com.okula.cardjitsu.guis;

import com.okula.cardjitsu.CardJitsu;
import com.okula.cardjitsu.card.Card;
import com.okula.cardjitsu.duel.Duel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class DuelGui implements Listener {

    @Getter
    private final Duel duel;
    private final Inventory duelGui;
    @Getter
    private final CardsGui cardsGui;
    @Getter
    private final CardsGui cardsGui2;
    private final Player player1;
    private final Player player2;


    public DuelGui(Player player1, Player player2, Duel duel) {
        this.player1 = player1;
        this.player2 = player2;
        this.duel = duel;
        duelGui = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.DARK_RED + "Duel");
        cardsGui = new CardsGui(player1, this);
        cardsGui2 = new CardsGui(player2, this);
        initializeItems();
        Bukkit.getServer().getPluginManager().registerEvents(this, CardJitsu.getInstance());
    }

    public void initializeItems() {
        duelGui.setItem(1, getHead(player1,0, 255, 0));
        duelGui.setItem(7, getHead(player2,0, 255, 0));
        duelGui.setItem(9, createGuiItem(Material.BLAZE_POWDER, "§cFire", 1));
        duelGui.setItem(15, createGuiItem(Material.BLAZE_POWDER, "§cFire", 1));
        duelGui.setItem(10, createGuiItem(Material.SNOWBALL, "§fSnow", 1));
        duelGui.setItem(16, createGuiItem(Material.SNOWBALL, "§fSnow", 1));
        duelGui.setItem(11, createGuiItem(Material.HEART_OF_THE_SEA, "§bWater", 1));
        duelGui.setItem(17, createGuiItem(Material.HEART_OF_THE_SEA, "§bWater", 1));
        duelGui.setItem(49, createGuiItem(Material.LECTERN, "§fPlay →", 1));
        addWinningCards();

    }

    public ItemStack getHead(Player player, int r, int g, int b) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = skull.getItemMeta();
        Component component = Component.text(player.getName())
                        .decoration(TextDecoration.ITALIC, false);
        if(!(itemMeta.hasDisplayName())) {
            itemMeta.displayName();
        }
        itemMeta.displayName(component.color(TextColor.color(r, g, b)));
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwningPlayer(player);
        skull.setItemMeta(skullMeta);
        return skull;
    }

    protected ItemStack createGuiItem(final Material material, final String name, final int amount) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();
        Component component = Component.text(ChatColor.WHITE + name);
        meta.displayName(component);
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    private void addWinningCards() {
        int fireSlot1 = 18;
        int snowSlot1 = 19;
        int waterSlot1 = 20;
        for(Card card : duel.getPlayer1CardsWon()) {
            if(card.getElement().equals("Fire")) {
                if(fireSlot1 == 45) continue;
                duelGui.setItem(fireSlot1, createGuiItem(card.getElementMaterial(), card.getColor(), 1));
                fireSlot1 += 9;
                continue;
            }
            if(card.getElement().equals("Snow")) {
                if(snowSlot1 == 46) continue;
                duelGui.setItem(snowSlot1, createGuiItem(card.getElementMaterial(), card.getColor(), 1));
                snowSlot1 += 9;
                continue;
            }
            if(card.getElement().equals("Water")) {
                if(waterSlot1 == 47) continue;
                duelGui.setItem(waterSlot1, createGuiItem(card.getElementMaterial(), card.getColor(), 1));
                waterSlot1 += 9;
            }
        }
        int fireSlot2 = 24;
        int snowSlot2 = 25;
        int waterSlot2 = 26;
        for(Card card : duel.getPlayer2CardsWon()) {
            if(card.getElement().equals("Fire")) {
                if(fireSlot2 == 51) continue;
                duelGui.setItem(fireSlot2, createGuiItem(card.getElementMaterial(), card.getColor(), 1));
                fireSlot2 += 9;
                continue;
            }
            if(card.getElement().equals("Snow")) {
                if(snowSlot2 == 52) continue;
                duelGui.setItem(snowSlot2, createGuiItem(card.getElementMaterial(), card.getColor(), 1));
                snowSlot2 += 9;
                continue;
            }
            if(card.getElement().equals("Water")) {
                if(waterSlot2 == 53) continue;
                duelGui.setItem(waterSlot2, createGuiItem(card.getElementMaterial(), card.getColor(), 1));
                waterSlot2 += 9;
            }
        }
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(duelGui);
    }

    @EventHandler
    public void on(final InventoryClickEvent e) {
        if (!e.getInventory().equals(duelGui)) return;
        e.setCancelled(true);
        if(e.getSlot() == 49) {
            if(e.getWhoClicked() == player1) {
                cardsGui.openInventory(player1);
            }
            if(e.getWhoClicked() == player2) {
                cardsGui2.openInventory(player2);
            }

        }

    }

}
