package fr.lifesteal.cubeeconomy.core.utils;

import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.regex.Matcher;

public class Utils {

    public static String parseColors(String string) {
        return Utils.parse(string, "&", "§");
    }

    public static String parse(String string, String regex, String replace) {
        return string.replaceAll(regex, Matcher.quoteReplacement(replace));
    }

    public static void sendPlayerMessage(String prefix, Player player, String message) {
        player.sendMessage(prefix + " " + message);
    }

    public static String format(String symbol, double amount) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        String formattedAmount = twoDForm.format(amount);
        formattedAmount = formattedAmount.replace(",", ".");
        return formattedAmount + symbol;
    }
}
