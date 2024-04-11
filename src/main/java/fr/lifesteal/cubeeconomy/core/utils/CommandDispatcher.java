package fr.lifesteal.cubeeconomy.core.utils;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.command.*;
import org.bukkit.entity.Player;

public class CommandDispatcher {

    private final IConfigurationService configurationService;

    public CommandDispatcher(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public boolean dispatch(Player player, String[] args) {
        GenericMoneyCommand genericMoneyCommand;
        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "balance":
                    genericMoneyCommand = new BalanceCommand(configurationService, player, args);
                    break;
                case "pay":
                    genericMoneyCommand = new PayCommand(configurationService, player, args);
                    break;
                case "give":
                    genericMoneyCommand = new GiveCommand(configurationService, player, args);
                    break;
                case "take":
                    genericMoneyCommand = new TakeCommand(configurationService, player, args);
                    break;
                case "set":
                    genericMoneyCommand = new SetCommand(configurationService, player, args);
                    break;
                case "giveall":
                    genericMoneyCommand = new GiveAllCommand(configurationService, player, args);
                    break;
                default:
                    Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, configurationService.getMessage("invalid-command"));
                    return false;
            }
        } else {
            genericMoneyCommand = new BalanceCommand(configurationService, player, args);
        }

        return genericMoneyCommand.execute();
    }
}
