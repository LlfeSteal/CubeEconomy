package fr.lifesteal.cubeeconomy.core.command;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveAllCommand extends GenericMoneyCommand {

    public GiveAllCommand(IConfigurationService configurationService, Player player, String[] args) {
        super(configurationService, player, args);
    }

    @Override
    public boolean execute() {
        String message = configurationService.getMessage("no-permission");
        if (player.hasPermission("cubeeconomy.giveall")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Player currentPlayer = onlinePlayer.getPlayer();
                if (currentPlayer != null) {
                    String[] args = new String[3];
                    args[1] =  currentPlayer.getName();
                    args[2] = String.valueOf(this.getTargetMoney());
                    GiveCommand giveCommand = new GiveCommand(configurationService, player, args);
                    giveCommand.execute();
                }
            }
            message = configurationService.getMessage("succes-giveall");
        }
        Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, message);
        return false;
    }

    @Override
    public double getTargetMoney() {
        return Double.parseDouble(getArgs()[1]);
    }
}
