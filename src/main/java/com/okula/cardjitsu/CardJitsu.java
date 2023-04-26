package com.okula.cardjitsu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okula.cardjitsu.commands.DuelCommand;
import com.okula.cardjitsu.gson.GsonStorage;
import com.okula.cardjitsu.listeners.PlayerConnectionListeners;
import com.okula.cardjitsu.managers.DuelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardJitsu extends JavaPlugin {

    @Getter
    private static CardJitsu instance;
    @Getter
    private static DuelManager duelManager;
    @Getter
    private Gson gson;
    @Getter
    private GsonStorage gsonStorage;
    @Override
    public void onEnable() {
        instance = this;
        duelManager = new DuelManager();
        registerCommands();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.gsonStorage = new GsonStorage(this);
        new PlayerConnectionListeners();

    }

    private void registerCommands() {
        this.getCommand("cj").setExecutor(new DuelCommand(duelManager));

    }

}
