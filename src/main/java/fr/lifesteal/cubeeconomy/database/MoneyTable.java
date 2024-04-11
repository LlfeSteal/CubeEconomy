package fr.lifesteal.cubeeconomy.database;

import fr.lifesteal.cubeeconomy.data.Account;
import fr.lifesteal.cubeeconomy.data.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoneyTable {
    private String dB_prefix;
    private Connection connection;
    private static final String dB_table = "money";


    public MoneyTable(String dB_prefix, Connection connection) {
        this.connection = connection;
        this.dB_prefix = dB_prefix;
    }


    public void createTableIfNotExist() {
        try {
            PreparedStatement statement = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + dB_prefix + dB_table + " ( `player_uuid` varchar(36) CHARACTER SET utf8mb4 NOT NULL, `money` decimal(10,0) NOT NULL ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;");
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(UUID playerUUID) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO " + dB_prefix + dB_table + " (player_uuid, money) VALUES (?,?);");
        statement.setString(1, playerUUID.toString());
        statement.setDouble(2, Config.getInstance().getDefaultMoneyValue());
        statement.execute();
    }

    public void addMoney(UUID playerUUID, double amount) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("UPDATE " + dB_prefix + dB_table + " SET money = money + ? WHERE player_uuid = ?;");
        statement.setDouble(1, amount);
        statement.setString(2, playerUUID.toString());
        statement.execute();
    }


    public void removeMoney(UUID playerUUID, double amount) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("UPDATE " + dB_prefix + dB_table + " SET money = money - ? WHERE player_uuid = ?;");
        statement.setDouble(1, amount);
        statement.setString(2, playerUUID.toString());
        statement.execute();
    }

    public List<Account> getAllAcounts() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM " + dB_prefix + dB_table);
        ResultSet resultSet = statement.executeQuery();
        List<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            UUID uuid = UUID.fromString(resultSet.getString("player_uuid"));
            double money = resultSet.getDouble("money");
            accounts.add(new Account(uuid, money));
        }
        return accounts;
    }


}
