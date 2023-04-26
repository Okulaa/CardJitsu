package com.okula.cardjitsu.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Config {
    private final File file;
    private final JSONParser parser = new JSONParser();

    public Config(File file) throws IOException, ParseException {
        this.file = file;
        reload();
    }

    public void reload() throws IOException, ParseException {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                PrintWriter pw = new PrintWriter(file, "UTF-8");
                pw.print("{");
                pw.print("}");
                pw.flush();
                pw.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), "UTF-8"));

    }
}
