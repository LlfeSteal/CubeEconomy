package fr.lifesteal.cubeeconomy.core.business;

import fr.lifesteal.cubeeconomy.api.business.IAccountService;
import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.api.repository.IAccountRepository;
import fr.lifesteal.cubeeconomy.core.data.Account;
import fr.lifesteal.cubeeconomy.core.manager.AccountManager;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.UUID;

public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final IConfigurationService configurationService;
    private final AccountManager accountManager;

    public AccountService(IAccountRepository accountRepository, IConfigurationService configurationService, AccountManager accountManager) {
        this.accountRepository = accountRepository;
        this.configurationService = configurationService;
        this.accountManager = accountManager;
    }

    @Override
    public double getAccountBalance(UUID playerUUID) {
        if (playerUUID == null) return 0;

        var account = this.accountManager.getPlayerAccount(playerUUID);
        return account != null
                ? account.getAmount()
                : 0;
    }

    @Override
    public EconomyResponse withdraw(UUID playerUUID, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        Account account = this.accountManager.getPlayerAccount(playerUUID);

        if (account == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Account doesn't exist");
        }

        if (!account.has(amount)) {
            return new EconomyResponse(0, account.getAmount(), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }

        if (!this.accountRepository.removeMoney(playerUUID, amount)) {
            return new EconomyResponse(amount, account.getAmount(), EconomyResponse.ResponseType.FAILURE, "Error occured while updating the account.");
        }

        account.remove(amount);
        return new EconomyResponse(amount, account.getAmount(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse deposit(UUID playerUUID, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        var account = this.accountManager.getPlayerAccount(playerUUID);

        if (account == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Account doesn't exist");
        }

        if (!this.accountRepository.addMoney(playerUUID, amount)) {
            return new EconomyResponse(amount, account.getAmount(), EconomyResponse.ResponseType.FAILURE, "Error occured while updating the account.");
        }

        account.add(amount);
        return new EconomyResponse(amount, account.getAmount(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public boolean hasAccount(UUID playerUUID) {
        return this.accountManager.getPlayerAccount(playerUUID) != null;
    }

    @Override
    public boolean createAccount(UUID playerUUID) {
        if (hasAccount(playerUUID) || !this.accountRepository.createAccount(playerUUID, this.configurationService.getDefaultMoney())) {
            return false;
        }

        accountManager.createPlayerAccount(playerUUID, this.configurationService.getDefaultMoney());
        return true;
    }
}
