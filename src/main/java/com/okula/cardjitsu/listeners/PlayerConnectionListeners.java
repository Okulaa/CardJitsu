package com.okula.cardjitsu.listeners;

import com.okula.cardjitsu.CardJitsu;
import com.okula.cardjitsu.gson.GsonStorage;
import com.okula.cardjitsu.playerinfo.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListeners implements Listener {

    private final GsonStorage gsonStorage;
    public PlayerConnectionListeners() {
        gsonStorage = CardJitsu.getInstance().getGsonStorage();
        Bukkit.getServer().getPluginManager().registerEvents(this, CardJitsu.getInstance());
    }
    @EventHandler
    public void on(PlayerJoinEvent event) {
        PlayerInfo playerInfo = gsonStorage.getPlayerInfo(event.getPlayer().getName());
        if(playerInfo == null) {
            playerInfo = new PlayerInfo(event.getPlayer());
            playerInfo.setWins(0);
            gsonStorage.addPlayerInfo(event.getPlayer().getName(), playerInfo);
            gsonStorage.save(playerInfo);
        }
        playerInfo.setPlayer(event.getPlayer());

    }

}
