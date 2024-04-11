package fr.lifesteal.cubeeconomy.core;

import fr.lifesteal.cubeeconomy.api.business.IAccountService;
import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.api.repository.IAccountRepository;
import fr.lifesteal.cubeeconomy.core.business.AccountService;
import fr.lifesteal.cubeeconomy.core.config.ConfigurationService;
import fr.lifesteal.cubeeconomy.core.repository.ConnectionFactory;
import fr.lifesteal.cubeeconomy.core.listener.MoneyListener;
import fr.lifesteal.cubeeconomy.core.manager.AccountManager;
import fr.lifesteal.cubeeconomy.core.repository.AccountRepository;
import fr.lifesteal.cubeeconomy.core.utils.CommandDispatcher;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class CubeEconomy extends JavaPlugin {

    private static Economy economy;
    private IConfigurationService configurationService;
    private IAccountService accountService;

    @Override
    public void onEnable() {
        initServices();
        setupEconomy();
        setupMoneyListener();
        getCommand("money").setExecutor(new MoneyCommandExecutor(new CommandDispatcher(this.configurationService)));
    }

    private void setupMoneyListener() {
        getServer().getPluginManager().registerEvents(new MoneyListener(), this);
    }

    private void initServices() {
        this.configurationService = new ConfigurationService(this, this.getConfig());
        this.configurationService.init();

        var connectionFactory = new ConnectionFactory(getLogger(), configurationService);
        IAccountRepository accountRepository = new AccountRepository(connectionFactory, configurationService);
        accountRepository.createTableIfNotExist();

        var accountManager = new AccountManager();
        accountManager.setPlayerAccounts(accountRepository.getAllAcounts());

        this.accountService = new AccountService(accountRepository, this.configurationService, accountManager);
    }

    private void setupEconomy() {
        getServer().getServicesManager().register(Economy.class, new VaultHandler(this, this.accountService, this.configurationService), this, ServicePriority.Highest);
        if (!isSetupEconomy()) {
            getLogger().severe("[CubeEconomy] Vault was not found !");
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
}
