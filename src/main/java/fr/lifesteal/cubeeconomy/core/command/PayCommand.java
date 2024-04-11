package fr.lifesteal.cubeeconomy.core.command;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.utils.Utils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class PayCommand extends GenericMoneyCommand {

    public PayCommand(IConfigurationService configurationService, Player player, String[] args) {
        super(configurationService, player, args);
    }

    @Override
    public boolean execute() {
        if (player.hasPermission("cubeeconomy.pay")) {
            EconomyResponse withdraw = economy.withdrawPlayer(player, getTargetMoney());
            if (withdraw.transactionSuccess()) {
                EconomyResponse deposit = economy.depositPlayer(getTargetPlayer(), getTargetMoney());
                if (!deposit.transactionSuccess()) {
                    String senderMessage = configurationService.getMessage("send-money");
                    senderMessage = Utils.parse(senderMessage, "%amount%", economy.format(getTargetMoney()));
                    senderMessage = Utils.parse(senderMessage, "%player%", getTargetPlayer().getName());
                    Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, senderMessage);
                    if (getTargetPlayer().isOnline()) {
                        Player targetPlayer = (Player) getTargetPlayer();
                        String receiverMessage = configurationService.getMessage("receive-money");
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
