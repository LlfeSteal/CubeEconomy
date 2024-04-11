package fr.lifesteal.cubeeconomy.core.repository;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.api.repository.IAccountRepository;
import fr.lifesteal.cubeeconomy.api.repository.IConnectionFactory;
import fr.lifesteal.cubeeconomy.core.data.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepository implements IAccountRepository {
    private static final String TableName = "money";
    private final IConnectionFactory connectionFactory;
    private final IConfigurationService configurationService;

    public AccountRepository(IConnectionFactory connectionFactory, IConfigurationService configurationService) {
        this.connectionFactory = connectionFactory;
        this.configurationService = configurationService;
    }

    public void createTableIfNotExist() {
        try(var connection = this.connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + getTableFullName() + "( `player_uuid` varchar(36) CHARACTER SET utf8mb4 NOT NULL, `money` decimal(10,0) NOT NULL ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;");
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(UUID playerUUID, double amount) {
        try(var connection = this.connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + getTableFullName() + " (player_uuid, money) VALUES (?,?);");
            statement.setString(1, playerUUID.toString());
            statement.setDouble(2, amount);
            statement.execute();

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean addMoney(UUID playerUUID, double amount) {
        try(var connection = this.connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE " + getTableFullName() + " SET money = money + ? WHERE player_uuid = ?;");
            statement.setDouble(1, amount);
            statement.setString(2, playerUUID.toString());
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean removeMoney(UUID playerUUID, double amount) {
        try(var connection = this.connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE " + getTableFullName() + " SET money = money - ? WHERE player_uuid = ?;");
            statement.setDouble(1, amount);
            statement.setString(2, playerUUID.toString());
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Account> getAllAcounts() {
        try(var connection = this.connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + getTableFullName());
            ResultSet resultSet = statement.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("player_uuid"));
                double money = resultSet.getDouble("money");
                accounts.add(new Account(uuid, money));
            }
            return accounts;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    private String getTableFullName() {
        return this.configurationService.getDatabasePrefix() + TableName;
    }
}
