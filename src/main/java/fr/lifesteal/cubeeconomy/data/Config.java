package fr.lifesteal.cubeeconomy.data;

import fr.lifesteal.cubeeconomy.database.DB;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static Config instance = new Config();
    private DB database;
    private String prefix;
    private Map<String, String> messages = new HashMap<>();
    private String singleMoneyName;
    private String multipleMoneyName;
    private String moneySymbol;
    private double defaultMoneyValue;

    public static Config getInstance() {
        return instance;
    }

    public DB getDatabase() {
        return database;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public void setDatabase(DB database) {
        this.database = database;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMessage(String messageName) {
        return this.messages.get(messageName);
    }

    public String format(double amount) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        String formattedAmount = twoDForm.format(amount);
        formattedAmount = formattedAmount.replace(",", ".");
        return formattedAmount + Config.getInstance().getMoneySymbol();
    }

    public void setSingleMoneyName(String singleMoneyName) {
        this.singleMoneyName = singleMoneyName;
    }

    public void setMultipleMoneyName(String multipleMoneyName) {
        this.multipleMoneyName = multipleMoneyName;
    }

    public void setMoneySymbol(String moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

    public String getSingleMoneyName() {
        return singleMoneyName;
    }

    public String getMultipleMoneyName() {
        return multipleMoneyName;
    }

    public String getMoneySymbol() {
        return moneySymbol;
    }

    public double getDefaultMoneyValue() {
        return defaultMoneyValue;
    }

    public void setDefaultMoneyValue(double defaultMoneyValue) {
        this.defaultMoneyValue = defaultMoneyValue;
    }
}
