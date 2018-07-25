package ml.enzodevelopment.infinitybot.utils;

import ml.enzodevelopment.infinitybot.connections.database.DBManager;
import io.github.binaryoverload.JSONConfig;

import java.awt.*;
import java.io.FileNotFoundException;

public class Config {

    public static DBManager DB;
    public static final Color ENZO_BLUE = new Color(51, 102, 153);

    public static String Discord_Token;
    public static String dbConnection;
    public static String fallback_prefix;
    public static String sentry_dsn;

    public void loadConfig() {
        try {
            JSONConfig config = new JSONConfig("config.json");
            Discord_Token = config.getString("Bot_Config.Authorization.Discord_Token").get();
            dbConnection = config.getString("Bot_Config.Authorization.Database_String").get();
            fallback_prefix = config.getString("Bot_Config.Settings.Fallback_Prefix").get();
            sentry_dsn = config.getString("Bot_Config.Authorization.Sentry_DSN").get();
            DB = new DBManager();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
