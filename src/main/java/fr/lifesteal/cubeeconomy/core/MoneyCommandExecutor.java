package fr.lifesteal.cubeeconomy.core;

import fr.lifesteal.cubeeconomy.core.utils.CommandDispatcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommandExecutor implements CommandExecutor {

    private final CommandDispatcher commandDispatcher;

    public MoneyCommandExecutor(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return commandDispatcher.dispatch(player, args);
        }
        return true;
    }
}
