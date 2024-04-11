package fr.lifesteal.cubeeconomy;

import fr.lifesteal.cubeeconomy.data.Account;
import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.database.DB;
import fr.lifesteal.cubeeconomy.database.MoneyTable;
import fr.lifesteal.cubeeconomy.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VaultHandler implements Economy {
    private final String name = "Fe";

    private CubeEconomy plugin;

    public VaultHandler(CubeEconomy plugin) {
        this.plugin = plugin;

        Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(), plugin);

        Utils.log("Vault support enabled.");
    }

    @Override
    public boolean isEnabled() {
        return plugin != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String format(double amount) {
        return Config.getInstance().format(amount);
    }

    @Override
    public String currencyNameSingular() {
        return Config.getInstance().getSingleMoneyName();
    }

    @Override
    public String currencyNamePlural() {
        return Config.getInstance().getMultipleMoneyName();
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

        Account account = null;
        if (playerUUID != null) {
            account = plugin.getAccountManager().getPlayerAccount(playerUUID);
        }
        if (account == null) {
            return 0;
        }
        return account.getAmount();
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
        if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        Account account = plugin.getAccountManager().getPlayerAccount(playerUUID);

        if (account == null) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist");
        }

        if (account.has(amount)) {
            DB database = Config.getInstance().getDatabase();
            Connection connection = database.getConnection();
            MoneyTable moneyTable = new MoneyTable(database.getdB_prefix(), connection);
            try {
                moneyTable.removeMoney(playerUUID, amount);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            account.remove(amount);

            return new EconomyResponse(amount, account.getAmount(), ResponseType.SUCCESS, "");
        } else {
            return new EconomyResponse(0, account.getAmount(), ResponseType.FAILURE, "Insufficient funds");
        }
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
        if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        Account account = plugin.getAccountManager().getPlayerAccount(playerUUID);

        if (account == null) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist");
        }
        DB database = Config.getInstance().getDatabase();
        Connection connection = database.getConnection();
        MoneyTable moneyTable = new MoneyTable(database.getdB_prefix(), connection);
        try {
            moneyTable.addMoney(playerUUID, amount);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        account.add(amount);

        return new EconomyResponse(amount, account.getAmount(), ResponseType.SUCCESS, "");
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
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Fe does not support bank accounts!");
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
        if (playerUUID != null) {
            return false;
        }
        Account account = plugin.getAccountManager().getPlayerAccount(playerUUID);
        if (account != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        Account account = plugin.getAccountManager().getPlayerAccount(offlinePlayer.getUniqueId());
        if (account != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return createAccount(Bukkit.getPlayer(playerName).getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return createAccount(offlinePlayer.getUniqueId());
    }

    private boolean createAccount(UUID playerUUID) {
        if (hasAccount(Bukkit.getOfflinePlayer(playerUUID))) {
            return false;
        }
        plugin.getAccountManager().createPlayerAccount(playerUUID, 0);
        DB database = Config.getInstance().getDatabase();
        Connection connection = database.getConnection();
        MoneyTable moneyTable = new MoneyTable(database.getdB_prefix(), connection);
        try {
            moneyTable.createAccount(playerUUID);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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

    public class EconomyServerListener implements Listener {
        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent event) {
            if (plugin == null) {
                Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Fe");

                if (plugin != null && plugin.isEnabled()) {
                    VaultHandler.this.plugin = (CubeEconomy) plugin;

                    Utils.log("Vault support enabled.");
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
            if (plugin != null) {
                if (event.getPlugin().getDescription().getName().equals(name)) {
                    plugin = null;
                    Utils.log("Vault support disabled.");
                }
            }
        }
    }
}
