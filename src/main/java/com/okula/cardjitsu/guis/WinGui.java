package com.okula.cardjitsu.guis;

import com.okula.cardjitsu.CardJitsu;
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

public class WinGui implements Listener {

    private final Inventory winGui;
    private Player player1;
    private Player player2;
    private Player winner;



    public WinGui(Player winner, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        winGui = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Card Jitsu " + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "Winner");
        initializeItems();
        Bukkit.getServer().getPluginManager().registerEvents(this, CardJitsu.getInstance());
    }

    public void initializeItems() {
        for(int i = 0; i <= 26; i++) {
            winGui.setItem(i, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, ""));

        }
        winGui.setItem(13, getHead(winner,0, 255, 0));

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

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(winGui);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(winGui)) return;
        e.setCancelled(true);


    }

}
