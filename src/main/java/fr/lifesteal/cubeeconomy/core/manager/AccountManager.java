package fr.lifesteal.cubeeconomy.core.manager;

import fr.lifesteal.cubeeconomy.core.data.Account;

import java.util.*;

public class AccountManager {

    private final Map<UUID, Account> playerAccounts = new HashMap<>();

    public void setPlayerAccounts(List<Account> accounts) {
        for (var account : accounts) {
            playerAccounts.put(account.getPlayerUUID(), account);
        }
    }

    public Account getPlayerAccount(UUID playerUUID) {
        return playerAccounts.get(playerUUID);
    }

    public void createPlayerAccount(UUID playerUUID, double amount) {
        Account newAccount = new Account(playerUUID, amount);
        this.playerAccounts.put(playerUUID, newAccount);
    }
}
