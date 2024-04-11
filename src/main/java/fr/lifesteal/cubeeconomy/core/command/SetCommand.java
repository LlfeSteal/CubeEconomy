package fr.lifesteal.cubeeconomy.core.command;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class SetCommand extends GenericMoneyCommand {

    public SetCommand(IConfigurationService configurationService, Economy economy, Player player, String[] args) {
        super(configurationService, economy, player, args);
    }

    @Override
    public boolean execute() {
        if (player.hasPermission("cubeeconomy.set")) {
            EconomyResponse withdraw = economy.withdrawPlayer(getTargetPlayer(), economy.getBalance(getTargetPlayer()));
            if (withdraw.transactionSuccess()) {
                EconomyResponse deposit = economy.depositPlayer(getTargetPlayer(), getTargetMoney());
                if (deposit.transactionSuccess()) {
                    String giverMessage = configurationService.getMessage("set-money");
                    giverMessage = Utils.parse(giverMessage, "%amount%", economy.format(getTargetMoney()));
                    giverMessage = Utils.parse(giverMessage, "%player%", getTargetPlayer().getName());
                    Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, giverMessage);
                    if (getTargetPlayer().isOnline()) {
                        Player targetPlayer = (Player) getTargetPlayer();
                        String receiverMessage = configurationService.getMessage("player-set-money");
                        receiverMessage = Utils.parse(receiverMessage, "%amount%", economy.format(getTargetMoney()));
                        receiverMessage = Utils.parse(receiverMessage, "%player%", player.getName());
                        Utils.sendPlayerMessage(configurationService.getPluginPrefix(), targetPlayer, receiverMessage);
                    }
                    return true;
                }
            }
            Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, configurationService.getMessage("unknown-error"));
            return false;
        }
        Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, configurationService.getMessage("no-permission"));
        return false;
    }
}
