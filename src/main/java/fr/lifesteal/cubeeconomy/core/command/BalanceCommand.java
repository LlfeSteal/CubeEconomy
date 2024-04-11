package fr.lifesteal.cubeeconomy.core.command;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.CubeEconomy;
import fr.lifesteal.cubeeconomy.core.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BalanceCommand extends GenericMoneyCommand {


    public BalanceCommand(IConfigurationService configurationService, Player player, String[] args) {
        super(configurationService, player, args);
    }

    @Override
    public boolean execute() {
        OfflinePlayer target = this.getTargetPlayer();
        Economy economy = CubeEconomy.getEconomy();
        String message = configurationService.getMessage("no-permission");

        if (target != null && player.hasPermission("cubeeconomy.balance.other")) {
            String moneyString = economy.format(economy.getBalance(target));
            message = configurationService.getMessage("display-money");
            message = Utils.parse(message, "%player%", target.getName());
            message = Utils.parse(message, "%balance%", moneyString);
        } else if (player.hasPermission("cubeeconomy.balance")) {
            String moneyString = economy.format(economy.getBalance(player));
            message = configurationService.getMessage("display-money");
            message = Utils.parse(message, "%balance%", moneyString);
        }

        Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, message);
        return true;
    }
}
