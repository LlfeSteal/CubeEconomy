package fr.lifesteal.cubeeconomy.core.command;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.core.CubeEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class GenericMoneyCommand {

    protected final IConfigurationService configurationService;
    protected Player player;
    private String[] args;
    protected Economy economy;

    public GenericMoneyCommand(IConfigurationService configurationService, Player player, String[] args) {
        this.configurationService = configurationService;
        this.player = player;
        this.args = args;
        this.economy = CubeEconomy.getEconomy();
    }

    public abstract boolean execute();

    public OfflinePlayer getTargetPlayer() {
        return Bukkit.getPlayer(args[1]);
    }

    public double getTargetMoney() {
        return Double.parseDouble(args[2]);
    }

    public String[] getArgs() {
        return args;
    }
}
