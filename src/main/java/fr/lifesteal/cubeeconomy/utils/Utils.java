package fr.lifesteal.cubeeconomy.utils;

import fr.lifesteal.cubeeconomy.data.Config;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

public class Utils {

    public static String parseColors(String string) {
        return Utils.parse(string, "&", "§");
    }

    public static String parse(String string, String regex, String replace) {
        Utils.log("MESSAGE : " + string);
        Utils.log("regex : " + regex);
        Utils.log("replace : " + replace);
        return string.replaceAll(regex, Matcher.quoteReplacement(replace));
    }

    public static void log(String message) {
        System.out.println("[CubeEconomy] " + message);
    }

    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage(Config.getInstance().getPrefix() + " " + message);
    }
}
