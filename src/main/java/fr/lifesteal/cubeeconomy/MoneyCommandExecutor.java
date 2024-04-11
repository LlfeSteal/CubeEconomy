package fr.lifesteal.cubeeconomy;

import fr.lifesteal.cubeeconomy.utils.CommandDispatcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return CommandDispatcher.dispatch(player, args);
        }
        return true;
    }
}
