package fr.lifesteal.cubeeconomy.core.command;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class GenericMoneyCommand {

    protected final IConfigurationService configurationService;
    protected final Economy economy;
    protected Player player;
    private final String[] args;

    public GenericMoneyCommand(IConfigurationService configurationService, Economy economy, Player player, String[] args) {
        this.configurationService = configurationService;
        this.player = player;
        this.args = args;
        this.economy = economy;
    }

    public abstract boolean execute();

    public OfflinePlayer getTargetPlayer() {
        return args.length >= 2 ? Bukkit.getPlayer(args[1]) : null;
    }

    public double getTargetMoney() {
        return Double.parseDouble(args[2]);
    }

    public String[] getArgs() {
        return args;
    }
}
