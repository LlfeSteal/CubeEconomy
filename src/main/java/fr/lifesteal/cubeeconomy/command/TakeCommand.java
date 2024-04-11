package fr.lifesteal.cubeeconomy.command;

import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.utils.Utils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class TakeCommand extends GenericMoneyCommand {

    public TakeCommand(Player player, String[] args) {
        super(player, args);
    }

    @Override
    public boolean execute() {
        if (player.hasPermission("cubeeconomy.take")) {
            EconomyResponse deposit = economy.depositPlayer(getTargetPlayer(), getTargetMoney());
            if (deposit.transactionSuccess()) {
                String giverMessage = Config.getInstance().getMessage("take-money");
                giverMessage = Utils.parse(giverMessage, "%amount%", economy.format(getTargetMoney()));
                giverMessage = Utils.parse(giverMessage, "%player%", getTargetPlayer().getName());
                Utils.sendPlayerMessage(player, giverMessage);
                if (getTargetPlayer().isOnline()) {
                    Player targetPlayer = (Player) getTargetPlayer();
                    String receiverMessage = Config.getInstance().getMessage("player-take-money");
                    receiverMessage = Utils.parse(receiverMessage, "%amount%", economy.format(getTargetMoney()));
                    receiverMessage = Utils.parse(receiverMessage, "%player%", player.getName());
                    Utils.sendPlayerMessage(targetPlayer, receiverMessage);
                }
                return true;
            }
            Utils.sendPlayerMessage(player, Config.getInstance().getMessage("unknown-error"));
            return false;
        }
        Utils.sendPlayerMessage(player, Config.getInstance().getMessage("no-permission"));
        return false;
    }
}
