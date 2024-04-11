package fr.lifesteal.cubeeconomy.command;

import fr.lifesteal.cubeeconomy.data.Config;
import fr.lifesteal.cubeeconomy.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveAllCommand extends GenericMoneyCommand {

    public GiveAllCommand(Player player, String[] args) {
        super(player, args);
    }

    @Override
    public boolean execute() {
        String message = Config.getInstance().getMessage("no-permission");
        if (player.hasPermission("cubeeconomy.giveall")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Player currentPlayer = onlinePlayer.getPlayer();
                if (currentPlayer != null) {
                    String[] args = new String[3];
                    args[1] =  currentPlayer.getName();
                    args[2] = String.valueOf(this.getTargetMoney());
                    GiveCommand giveCommand = new GiveCommand(player, args);
                    giveCommand.execute();
                }
            }
            message = Config.getInstance().getMessage("succes-giveall");
        }
        Utils.sendPlayerMessage(player, message);
        return false;
    }

    @Override
    public double getTargetMoney() {
        return Double.parseDouble(getArgs()[1]);
    }
}
