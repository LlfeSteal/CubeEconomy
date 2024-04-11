package fr.lifesteal.cubeeconomy.api.business;

import net.milkbowl.vault.economy.EconomyResponse;

import java.util.UUID;

public interface IAccountService {
    double getAccountBalance(UUID playerUUID);
    EconomyResponse withdraw(UUID playerUUID, double amount);
    EconomyResponse deposit(UUID playerUUID, double amount);
    boolean hasAccount(UUID playerUUID);
    boolean createAccount(UUID playerUUID);
}
