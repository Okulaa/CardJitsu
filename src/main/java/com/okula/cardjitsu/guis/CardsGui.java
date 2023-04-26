package com.okula.cardjitsu.guis;

import com.okula.cardjitsu.CardJitsu;
import com.okula.cardjitsu.card.Card;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
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

public class CardsGui implements Listener {

    @Getter
    private final Inventory cardsGuiInv;
    @Getter
    private final DuelGui duelGui;
    private Player player;
    @Getter
    @Setter
    private boolean isReady = false;
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    @Getter
    private Card selectedCard;



    public CardsGui(Player player, DuelGui duelGui) {
        this.player = player;
        cardsGuiInv = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + "Cards");
        this.duelGui = duelGui;
        startingCards();
        initializeItems();
        Bukkit.getServer().getPluginManager().registerEvents(this, CardJitsu.getInstance());
    }

    public void initializeItems() {
        // Colors
        cardsGuiInv.setItem(0, createGuiItem(card1.getElementMaterial(), card1.getColor()));
        cardsGuiInv.setItem(2, createGuiItem(card2.getElementMaterial(), card2.getColor()));
        cardsGuiInv.setItem(4, createGuiItem(card3.getElementMaterial(), card3.getColor()));
        cardsGuiInv.setItem(6, createGuiItem(card4.getElementMaterial(), card4.getColor()));
        cardsGuiInv.setItem(8, createGuiItem(card5.getElementMaterial(), card5.getColor()));

        // Elements
        cardsGuiInv.setItem(9, createCardGuiItem(card1.getCardMaterial(), card1.getElement(), card1));
        cardsGuiInv.setItem(11, createCardGuiItem(card2.getCardMaterial(), card2.getElement(), card2));
        cardsGuiInv.setItem(13, createCardGuiItem(card3.getCardMaterial(), card3.getElement(), card3));
        cardsGuiInv.setItem(15, createCardGuiItem(card4.getCardMaterial(), card4.getElement(), card4));
        cardsGuiInv.setItem(17, createCardGuiItem(card5.getCardMaterial(), card5.getElement(), card5));

        // Go back item
        cardsGuiInv.setItem(31, createGuiItem(Material.PAPER, "§f← Check Stats"));

    }

    private void startingCards() {
        card1 = new Card();
        card2 = new Card();
        card3 = new Card();
        card4 = new Card();
        card5 = new Card();

    }

    public void newCard () {
        if(selectedCard == card1) {
            card1 = new Card();
            return;
        }
        if(selectedCard == card2) {
            card2 = new Card();
            return;
        }
        if(selectedCard == card3) {
            card3 = new Card();
            return;
        }
        if(selectedCard == card4) {
            card4 = new Card();
            return;
        }
        if(selectedCard == card5) {
            card5 = new Card();
        }
    }

    protected ItemStack createCardGuiItem(final Material material, final String name, final Card card) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        Component component = Component.text(card.getChatColor() + name)
                        .decoration(TextDecoration.ITALIC, false);
        meta.displayName(component);
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }
    protected ItemStack createGuiItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        Component component = Component.text(ChatColor.WHITE + name)
                .decoration(TextDecoration.ITALIC, false);
        meta.displayName(component);
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(cardsGuiInv);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(cardsGuiInv)) return;
        e.setCancelled(true);
        int slotNumber = e.getSlot();
        if(slotNumber == 31) {
            duelGui.openInventory(e.getWhoClicked());
            return;
        }
        if(slotNumber == 9) {
            selectedCard = card1;
            setSelectedCardGuiItem(18);
            isReady = true;
            duelGui.getDuel().readyCheck();
            return;
        }
        if(slotNumber == 11) {
            selectedCard = card2;
            setSelectedCardGuiItem(20);
            isReady = true;
            duelGui.getDuel().readyCheck();
            return;
        }
        if(slotNumber == 13) {
            selectedCard = card3;
            setSelectedCardGuiItem(22);
            isReady = true;
            duelGui.getDuel().readyCheck();
            return;
        }
        if(slotNumber == 15) {
            selectedCard = card4;
            setSelectedCardGuiItem(24);
            isReady = true;
            duelGui.getDuel().readyCheck();
            return;
        }
        if(slotNumber == 17) {
            selectedCard = card5;
            setSelectedCardGuiItem(26);
            isReady = true;
            duelGui.getDuel().readyCheck();
        }

    }

    private void setSelectedCardGuiItem(int slot) {
        for(int i = 18; i < 28; i += 2) {
            cardsGuiInv.clear(i);
        }
        cardsGuiInv.setItem(slot, createGuiItem(Material.EMERALD, "§a>Selected Card<"));
    }

}
