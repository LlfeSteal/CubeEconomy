package fr.lifesteal.cubeeconomy.manager;

import fr.lifesteal.cubeeconomy.data.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountManager {

    private List<Account> playerAccounts = new ArrayList<>();

    public void setPlayerAccounts(List<Account> accounts) {
        playerAccounts = accounts;
    }

    public Account getPlayerAccount(UUID playerUUID) {
        for (Account account : playerAccounts) {
            if (account.getPlayerUUID().equals(playerUUID)) {
                return account;
            }
        }
        return null;
    }

    public void createPlayerAccount(UUID playerUUID, int amount) {
        Account newAccount = new Account(playerUUID, amount);
        this.playerAccounts.add(newAccount);
    }
}
