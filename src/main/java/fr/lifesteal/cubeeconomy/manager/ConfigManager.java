package fr.lifesteal.cubeeconomy.manager;

import fr.lifesteal.cubeeconomy.CubeEconomy;
import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.database.DB;
import fr.lifesteal.cubeeconomy.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    private FileConfiguration config;
    private CubeEconomy cubeEconomy;

    public ConfigManager(CubeEconomy cubeEconomy) {
        this.cubeEconomy = cubeEconomy;
    }


    private void getConfiguration() {
        Config config = Config.getInstance();
        config.setDatabase(getDatabaseConfig());
        config.setPrefix(getPrefixConfig());
        config.setMessages(getMessagesConfig());
        config.setSingleMoneyName(getSingleMoneyName());
        config.setMultipleMoneyName(getMultipleMoneyName());
        config.setMoneySymbol(getSymbolMoneyName());
        config.setDefaultMoneyValue(getDefaultMoneyValue());
    }

    private DB getDatabaseConfig() {
        String dB_host = this.config.getString("mysql.host");
        String dB_name = this.config.getString("mysql.name");
        String dB_prefix = this.config.getString("mysql.prefix");
        String dB_user = this.config.getString("mysql.user");
        String dB_pass = this.config.getString("mysql.pass");
        int dB_port = this.config.getInt("mysql.port");

        return new DB(dB_host, dB_name, dB_port, dB_user, dB_pass, dB_prefix);
    }

    private String getPrefixConfig() {
        return Utils.parseColors(this.config.getString("prefix"));
    }

    private Map<String, String> getMessagesConfig() {
        Map<String, String> messages = new HashMap<>();
        ConfigurationSection section = this.config.getConfigurationSection("messages");
        for (String messageName : section.getKeys(false)) {
            String message = this.config.getString(section.getCurrentPath() + "." + messageName);
            String parsedMessage = Utils.parseColors(message);
            messages.put(messageName, parsedMessage);
            Utils.log("[" + messageName + "] " + parsedMessage);
        }
        return messages;
    }

    private String getSingleMoneyName() {
        return this.config.getString("money.single");
    }

    private String getMultipleMoneyName() {
        return this.config.getString("money.multiple");
    }

    private String getSymbolMoneyName() {
        return this.config.getString("money.symbol");
    }

    private Double getDefaultMoneyValue() {
        return this.config.getDouble("money.default-money");
    }

    public void initConfiguration() {

        File configFile = new File(cubeEconomy.getDataFolder() + File.separator + "config.yml");


        if (!cubeEconomy.getDataFolder().exists()) {
            boolean dirCreated = cubeEconomy.getDataFolder().mkdir();
        }

        if (!configFile.exists()) {
            createConfiguration(configFile);
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);

        getConfiguration();
    }

    private void createConfiguration(File configFile) {

        try {
            boolean configFileCreated = configFile.createNewFile();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream((configFile.getName()));

            OutputStream outputStream = new FileOutputStream(configFile);

            copy(inputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
            //If needed, close streams.
        } finally {
            input.close();
            output.close();
        }
    }
}
