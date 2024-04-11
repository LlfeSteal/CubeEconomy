package fr.lifesteal.cubeeconomy.command;

import fr.lifesteal.cubeeconomy.CubeEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class GenericMoneyCommand {

    protected Player player;
    private String[] args;
    protected Economy economy;

    public GenericMoneyCommand(Player player, String[] args) {
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
