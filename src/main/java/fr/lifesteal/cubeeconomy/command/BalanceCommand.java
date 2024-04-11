package fr.lifesteal.cubeeconomy.command;

import fr.lifesteal.cubeeconomy.CubeEconomy;
import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BalanceCommand extends GenericMoneyCommand {


    public BalanceCommand(Player player, String[] args) {
        super(player, args);
    }

    @Override
    public boolean execute() {
        OfflinePlayer target = this.getTargetPlayer();
        Economy economy = CubeEconomy.getEconomy();
        String message = Config.getInstance().getMessage("no-permission");
        if (target != null && player.hasPermission("cubeeconomy.balance.other")) {
            String moneyString = economy.format(economy.getBalance(target));
            message = Config.getInstance().getMessage("display-money");
            message = Utils.parse(message, "%player%", target.getName());
            message = Utils.parse(message, "%balance%", moneyString);
        } else if (player.hasPermission("cubeeconomy.balance")) {
            String moneyString = economy.format(economy.getBalance(player));
            message = Config.getInstance().getMessage("display-money");
            message = Utils.parse(message, "%balance%", moneyString);
        }
        Utils.sendPlayerMessage(player, message);
        return true;
    }
}
