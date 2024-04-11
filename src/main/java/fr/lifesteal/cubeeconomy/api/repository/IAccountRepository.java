package fr.lifesteal.cubeeconomy.api.repository;

import fr.lifesteal.cubeeconomy.core.data.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IAccountRepository {
    void createTableIfNotExist();
    boolean createAccount(UUID playerUUID, double amount);
    boolean addMoney(UUID playerUUID, double amount);
    boolean removeMoney(UUID playerUUID, double amount);
    List<Account> getAllAcounts();
}
