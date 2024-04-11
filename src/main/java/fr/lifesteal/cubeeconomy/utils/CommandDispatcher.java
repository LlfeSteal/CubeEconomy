package fr.lifesteal.cubeeconomy.utils;

import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.command.*;
import org.bukkit.entity.Player;

public class CommandDispatcher {

    public static boolean dispatch(Player player, String[] args) {
        GenericMoneyCommand genericMoneyCommand;
        if (args.length > 0) {
            String command = args[0];
            switch (command) {
                case "balance":
                    genericMoneyCommand = new BalanceCommand(player, args);
                    break;
                case "pay":
                    genericMoneyCommand = new PayCommand(player, args);
                    break;
                case "give":
                    genericMoneyCommand = new GiveCommand(player, args);
                    break;
                case "take":
                    genericMoneyCommand = new TakeCommand(player, args);
                    break;
                case "set":
                    genericMoneyCommand = new SetCommand(player, args);
                    break;
                case "giveall":
                    genericMoneyCommand = new GiveAllCommand(player, args);
                    break;
                default:
                    Utils.sendPlayerMessage(player, Config.getInstance().getMessage("invalid-command"));
                    return false;
            }
        } else {
            genericMoneyCommand = new BalanceCommand(player, args);
        }
        return genericMoneyCommand.execute();
    }
}
