package fr.lifesteal.cubeeconomy.core.utils;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandDispatcher {

    private final IConfigurationService configurationService;

    public CommandDispatcher(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public boolean dispatch(Player player, String[] args) {
        GenericMoneyCommand genericMoneyCommand;
        System.out.println("ARGS : " + Arrays.toString(args));
        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "balance" -> genericMoneyCommand = new BalanceCommand(configurationService, player, args);
                case "pay" -> genericMoneyCommand = new PayCommand(configurationService, player, args);
                case "give" -> genericMoneyCommand = new GiveCommand(configurationService, player, args);
                case "take" -> genericMoneyCommand = new TakeCommand(configurationService, player, args);
                case "set" -> genericMoneyCommand = new SetCommand(configurationService, player, args);
                case "giveall" -> genericMoneyCommand = new GiveAllCommand(configurationService, player, args);
                default -> {
                    Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, Utils.parse(configurationService.getMessage("invalid-command"), "%player%", command));
                    return false;
                }
            }
        } else {
            genericMoneyCommand = new BalanceCommand(configurationService, player, args);
        }

        return genericMoneyCommand.execute();
    }
}
