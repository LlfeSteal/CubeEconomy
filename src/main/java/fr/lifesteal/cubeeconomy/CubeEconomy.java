package fr.lifesteal.cubeeconomy;

import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.database.DB;
import fr.lifesteal.cubeeconomy.database.MoneyTable;
import fr.lifesteal.cubeeconomy.listener.MoneyListener;
import fr.lifesteal.cubeeconomy.manager.AccountManager;
import fr.lifesteal.cubeeconomy.manager.ConfigManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class CubeEconomy extends JavaPlugin {

    private static Economy economy;
    private ConfigManager configManager = new ConfigManager(this);
    private AccountManager accountManager = new AccountManager();

    @Override
    public void onEnable() {
        configManager.initConfiguration();
        setupDatabase();
        setupEconomy();
        setupMoneyListener();
        getCommand("money").setExecutor(new MoneyCommandExecutor());
    }

    private void setupMoneyListener() {
        getServer().getPluginManager().registerEvents(new MoneyListener(), this);
    }

    private void setupDatabase() {
        DB database = Config.getInstance().getDatabase();
        Connection connection = database.getConnection();
        MoneyTable moneyTable = new MoneyTable(database.getdB_prefix(), connection);
        try {
            moneyTable.createTableIfNotExist();
            accountManager.setPlayerAccounts(moneyTable.getAllAcounts());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupEconomy() {
        getServer().getServicesManager().register(Economy.class, new VaultHandler(this), this, ServicePriority.Highest);
        if (!isSetupEconomy()) {
            System.out.println("[CubeEconomy] Vault was not found !");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private boolean isSetupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
}
