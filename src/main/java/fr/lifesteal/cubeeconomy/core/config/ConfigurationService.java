package fr.lifesteal.cubeeconomy.core.config;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationService implements IConfigurationService {

    private final JavaPlugin javaPlugin;
    private final FileConfiguration fileConfiguration;

    private String pluginPrefix;
    private Map<String, String> messages;

    private String databaseHost;
    private int databasePort;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;
    private String databasePrefix;

    private double defaultMoney;
    private String singleMoneyName;
    private String multipleMoneyName;
    private String moneySymbol;

    public ConfigurationService(JavaPlugin javaPlugin, FileConfiguration fileConfiguration) {
        this.javaPlugin = javaPlugin;
        this.fileConfiguration = fileConfiguration;
    }

    @Override
    public void init() {
        this.initDefaultConfiguration();
        this.loadConfiguration();
    }

    @Override
    public String getPluginPrefix() {
        return pluginPrefix;
    }

    @Override
    public String getDatabasePrefix() {
        return this.databasePrefix;
    }

    @Override
    public String getDatabaseHost() {
        return this.databaseHost;
    }

    @Override
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public int getDatabasePort() {
        return this.databasePort;
    }

    @Override
    public String getDatabaseUser() {
        return this.databaseUser;
    }

    @Override
    public String getDatabasePassword() {
        return this.databasePassword;
    }

    @Override
    public String getMessage(String key) {
        return this.messages.get(key);
    }

    @Override
    public double getDefaultMoney() {
        return defaultMoney;
    }

    @Override
    public String getSingleMoneyName() {
        return singleMoneyName;
    }

    @Override
    public String getMultipleMoneyName() {
        return multipleMoneyName;
    }

    @Override
    public String getMoneySymbol() {
        return moneySymbol;
    }

    private void loadConfiguration() {
        this.javaPlugin.reloadConfig();

        this.pluginPrefix = Utils.parseColors(this.fileConfiguration.getString("prefix"));

        this.databaseHost = this.fileConfiguration.getString("mysql.host");
        this.databasePort = this.fileConfiguration.getInt("mysql.port");
        this.databaseName = this.fileConfiguration.getString("mysql.name");
        this.databaseUser = this.fileConfiguration.getString("mysql.user");
        this.databasePassword = this.fileConfiguration.getString("mysql.pass");
        this.databasePrefix = this.fileConfiguration.getString("mysql.prefix");

        this.messages = new HashMap<>();
        ConfigurationSection section = this.fileConfiguration.getConfigurationSection("messages");
        for (String messageName : section.getKeys(false)) {
            String message = this.fileConfiguration.getString(section.getCurrentPath() + "." + messageName);
            String parsedMessage = Utils.parseColors(message);
            this.messages.put(messageName, parsedMessage);
        }

        this.defaultMoney = this.fileConfiguration.getDouble("money.default-money");
        this.singleMoneyName = this.fileConfiguration.getString("money.single");
        this.multipleMoneyName = this.fileConfiguration.getString("money.multiple");
        this.moneySymbol = this.fileConfiguration.getString("money.symbol");
    }

    private void initDefaultConfiguration() {
        this.fileConfiguration.addDefault("prefix", "&f[&aCubeEconomy&f]");
        this.fileConfiguration.addDefault("mysql", new HashMap<>() {{
            put("host", "localhost");
            put("port", "1336");
            put("name", "database");
            put("user", "user");
            put("pass", "password");
            put("prefix", "playerMoney_");
        }});

        this.fileConfiguration.addDefault("money", new HashMap<>() {{
            put("default-money", "0.0");
            put("single", "dollar");
            put("multiple", "dollars");
            put("symbol", "$");
        }});

        this.fileConfiguration.addDefault("messages", new HashMap<>() {{
            put("invalid-command", "&cCommande &f%command% &cnot found");
            put("unknown-error", "&cUnknow error occured");
            put("no-permission", "&cYou don't have the permission to use this command");
            put("display-money", "&aYou have &f%balance% ");
            put("other-display-money", "The player %player% has %balance%");
            put("send-money", "&aYou just send &f%amount% &ato %player%");
            put("receive-money", "&aYou received %amount% &afrom %player%");
            put("give-money", "&aYou just gave &f%amount% &ato %player%");
            put("give-receive-money", "The player %player% gave you %amount%");
            put("set-money", "You set %player% money to %amount%");
            put("player-set-money", "Your money has been set to %amount% by %player%");
            put("take-money", "You take %amount% from %player%");
            put("player-take-money", "The player %player% took you %amount%");
            put("succes-giveall", "You just sent %amount% to all connected players");
            put("player-display-money", "The player %player% has %balance%");
        }});

        this.fileConfiguration.options().copyDefaults(true);
        this.javaPlugin.saveConfig();
    }
}
