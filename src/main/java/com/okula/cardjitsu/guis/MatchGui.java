package com.okula.cardjitsu.guis;

import com.okula.cardjitsu.CardJitsu;
import com.okula.cardjitsu.card.Card;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
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

public class MatchGui implements Listener {
    private Player player1;
    private Player player2;
    private Inventory matchGui;
    private CardsGui player1CardsGui;
    private CardsGui player2CardsGui;
    private MatchGui matchGuiInstance;
    private Card winCard;
    public MatchGui(Player player1, Player player2, Card winCard, CardsGui player1CardsGui, CardsGui player2CardsGui) {
        matchGui = Bukkit.createInventory(null, 27, "Card Jitsu");
        matchGuiInstance = this;
        this.player1 = player1;
        this.player2 = player2;
        this.player1CardsGui = player1CardsGui;
        this.player2CardsGui = player2CardsGui;
        this.winCard = winCard;
        initializeItems();
        Bukkit.getServer().getPluginManager().registerEvents(this, CardJitsu.getInstance());

    }

    public void initializeItems() {
        matchGui.setItem(9, getHead(player1, 0, 255, 0));
        matchGui.setItem(17, getHead(player2, 0, 255, 0));
        Card selCard1 = player1CardsGui.getSelectedCard();
        Card selCard2 = player2CardsGui.getSelectedCard();
        matchGui.setItem(11, createGuiItem(selCard1.getElementMaterial(), selCard1.getColor()));
        matchGui.setItem(15, createGuiItem(selCard2.getElementMaterial(), selCard2.getColor()));
        matchGui.setItem(20, createCardGuiItem(selCard1.getCardMaterial(), selCard1.getElement(), selCard1));
        matchGui.setItem(24, createCardGuiItem(selCard2.getCardMaterial(), selCard2.getElement(), selCard2));
        matchGui.setItem(22, createGuiItem(Material.TRIPWIRE_HOOK, "§fNext Round ->"));
        if(winCard == null) {
            matchGui.setItem(13, createGuiItem(Material.YELLOW_BANNER, "§6Draw"));
            return;
        }
        if(selCard1 == winCard) {
            matchGui.setItem(2, createGuiItem(Material.GREEN_BANNER, "§aWinner"));
            matchGui.setItem(6, createGuiItem(Material.RED_BANNER, "§cLoser"));
            return;
        }

        matchGui.setItem(2, createGuiItem(Material.RED_BANNER, "§cLoser"));
        matchGui.setItem(6, createGuiItem(Material.GREEN_BANNER, "§aWinner"));

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
        Component component = Component.text(name);
        meta.displayName(component);
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(matchGui);
    }

    @EventHandler
    public void on(final InventoryClickEvent e) {
        if (!e.getInventory().equals(matchGui)) return;
        e.setCancelled(true);
        if(e.getSlot() == 22) {
            if(e.getWhoClicked() == player1) {
                player1CardsGui.getDuelGui().initializeItems();
                player1CardsGui.getDuelGui().openInventory(player1);
                return;
            }
            player2CardsGui.getDuelGui().initializeItems();
            player2CardsGui.getDuelGui().openInventory(player2);
        }

    }

}
