package fr.lifesteal.cubeeconomy.core.utils;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.command.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandDispatcher {

    private final IConfigurationService configurationService;
    private final Economy economy;

    public CommandDispatcher(IConfigurationService configurationService, Economy economy) {
        this.configurationService = configurationService;
        this.economy = economy;
    }

    public boolean dispatch(Player player, String[] args) {
        GenericMoneyCommand genericMoneyCommand;
        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "balance" -> genericMoneyCommand = new BalanceCommand(configurationService, economy, player, args);
                case "pay" -> genericMoneyCommand = new PayCommand(configurationService, economy, player, args);
                case "give" -> genericMoneyCommand = new GiveCommand(configurationService, economy, player, args);
                case "take" -> genericMoneyCommand = new TakeCommand(configurationService, economy, player, args);
                case "set" -> genericMoneyCommand = new SetCommand(configurationService, economy, player, args);
                case "giveall" -> genericMoneyCommand = new GiveAllCommand(configurationService, economy, player, args);
                default -> {
                    Utils.sendPlayerMessage(configurationService.getPluginPrefix(), player, Utils.parse(configurationService.getMessage("invalid-command"), "%command%", command));
                    return false;
                }
            }
        } else {
            genericMoneyCommand = new BalanceCommand(configurationService, economy, player, args);
        }

        return genericMoneyCommand.execute();
    }
}
