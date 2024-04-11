package fr.lifesteal.cubeeconomy.core;

import fr.lifesteal.cubeeconomy.api.IVaultHandler;
import fr.lifesteal.cubeeconomy.api.business.IAccountService;
import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.utils.Utils;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VaultHandler implements IVaultHandler {
    private static final String name = "CubeEconomy";
    private final IAccountService accountService;
    private final IConfigurationService configurationService;

    private CubeEconomy plugin;

    public VaultHandler(CubeEconomy plugin, IAccountService accountService, IConfigurationService configurationService) {
        this.plugin = plugin;
        this.accountService = accountService;
        this.configurationService = configurationService;


        //Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(), plugin);
    }

    @Override
    public void disable() {
        plugin = null;
    }

    @Override
    public boolean isEnabled() {
        return plugin != null && plugin.isEnabled();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String format(double amount) {
        return Utils.format(configurationService.getMoneySymbol(), amount);
    }

    @Override
    public String currencyNameSingular() {
        return configurationService.getSingleMoneyName();
    }

    @Override
    public String currencyNamePlural() {
        return configurationService.getMultipleMoneyName();
    }

    @Override
    public double getBalance(String playerName) {
        return getAccountBalance(Bukkit.getPlayer(playerName).getUniqueId());
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getAccountBalance(offlinePlayer.getUniqueId());
    }

    private double getAccountBalance(UUID playerUUID) {
        return this.accountService.getAccountBalance(playerUUID);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return withdraw(Bukkit.getPlayer(playerName).getUniqueId(), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        return withdraw(offlinePlayer.getUniqueId(), amount);
    }

    private EconomyResponse withdraw(UUID playerUUID, double amount) {
        return this.accountService.withdraw(playerUUID, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return deposit(Bukkit.getPlayer(playerName).getUniqueId(), amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        return deposit(offlinePlayer.getUniqueId(), amount);
    }

    private EconomyResponse deposit(UUID playerUUID, double amount) {
        return this.accountService.deposit(playerUUID, amount);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        return getBalance(offlinePlayer) >= amount;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, name + " does not support bank accounts!");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public boolean hasAccount(String playerName) {
        UUID playerUUID = Bukkit.getPlayer(playerName).getUniqueId();
        return this.accountService.hasAccount(playerUUID);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return this.accountService.hasAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return this.accountService.createAccount(Bukkit.getPlayer(playerName).getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return this.accountService.createAccount(offlinePlayer.getUniqueId());
    }


    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String worldName) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String playerName, String worldName) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String worldName) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return has(offlinePlayer, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return withdrawPlayer(offlinePlayer, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return depositPlayer(offlinePlayer, amount);
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String worldName) {
        return createPlayerAccount(offlinePlayer);
    }
}
