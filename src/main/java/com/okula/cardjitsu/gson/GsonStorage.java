package com.okula.cardjitsu.gson;

import com.google.gson.Gson;
import com.okula.cardjitsu.CardJitsu;
import com.okula.cardjitsu.playerinfo.PlayerInfo;
import lombok.SneakyThrows;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GsonStorage {

    private final HashMap<String, PlayerInfo> playerInfos = new HashMap<>();
    private final CardJitsu main;

    public GsonStorage(CardJitsu main) {
        this.main = main;
        initialize();

    }

    public void addPlayerInfo(String name, PlayerInfo playerInfo) {
        playerInfos.put(name.toLowerCase(), playerInfo);

    }

    public PlayerInfo getPlayerInfo(String name) {
        return playerInfos.get(name.toLowerCase());
    }

    private File getDataFolder() {
        File file = new File(main.getDataFolder().getPath() + "/data");
        if (!file.exists()) file.getParentFile().mkdirs();
        if (!file.exists()) file.mkdir();
        return file;
    }

    @SneakyThrows
    public PlayerInfo load(File file) {
        try {
            return main.getGson().fromJson(new BufferedReader(new FileReader(file)), PlayerInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SneakyThrows
    public void save(PlayerInfo playerInfo) {
        Gson gson = main.getGson();
        if (gson == null) return;
        BufferedWriter bw = new BufferedWriter(new FileWriter(getDataFolder() + "/" + getFileName(playerInfo.getName())));
        gson.toJson(playerInfo, bw);
        bw.flush();
        bw.close();
    }

    public boolean delete(PlayerInfo playerInfo) {
        File warpFile = new File(getDataFolder() + "/" + getFileName(playerInfo.getName()));
        if (!warpFile.exists()) return false;
        playerInfos.remove(playerInfo.getName().toLowerCase());
        return warpFile.delete();
    }

    private void initialize() {
        File dataFolder = getDataFolder();
        File[] files = dataFolder.listFiles();
        if (files == null) return;
        for (File playerInfoFile : files) {
            PlayerInfo loaded = load(playerInfoFile);
            if (loaded == null) continue;
            playerInfos.put(loaded.getName().toLowerCase(), loaded);
        }
    }

    public Set<String> getPlayerInfos() {
        Set<String> names = new HashSet<>();
        for(PlayerInfo playerInfo : playerInfos.values()) {
            names.add(playerInfo.getName());
        }
        return names;
    }

    public String getFileName(String name) {
        return name.toLowerCase() + ".json";
    }
}
