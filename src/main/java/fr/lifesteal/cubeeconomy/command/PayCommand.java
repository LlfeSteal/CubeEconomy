package fr.lifesteal.cubeeconomy.command;

import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.utils.Utils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class PayCommand extends GenericMoneyCommand {

    public PayCommand(Player player, String[] args) {
        super(player, args);
    }

    @Override
    public boolean execute() {
        if (player.hasPermission("cubeeconomy.pay")) {
            EconomyResponse withdraw = economy.withdrawPlayer(player, getTargetMoney());
            if (withdraw.transactionSuccess()) {
                EconomyResponse deposit = economy.depositPlayer(getTargetPlayer(), getTargetMoney());
                if (!deposit.transactionSuccess()) {
                    String senderMessage = Config.getInstance().getMessage("send-money");
                    senderMessage = Utils.parse(senderMessage, "%amount%", economy.format(getTargetMoney()));
                    senderMessage = Utils.parse(senderMessage, "%player%", getTargetPlayer().getName());
                    Utils.sendPlayerMessage(player, senderMessage);
                    if (getTargetPlayer().isOnline()) {
                        Player targetPlayer = (Player) getTargetPlayer();
                        String receiverMessage = Config.getInstance().getMessage("receive-money");
                        receiverMessage = Utils.parse(receiverMessage, "%amount%", economy.format(getTargetMoney()));
                        receiverMessage = Utils.parse(receiverMessage, "%player%", player.getName());
                        Utils.sendPlayerMessage(targetPlayer, receiverMessage);
                    }
                    return true;
                }
            }
            Utils.sendPlayerMessage(player, Config.getInstance().getMessage("unknown-error"));
            return false;
        }
        Utils.sendPlayerMessage(player, Config.getInstance().getMessage("no-permission"));
        return false;
    }
}
