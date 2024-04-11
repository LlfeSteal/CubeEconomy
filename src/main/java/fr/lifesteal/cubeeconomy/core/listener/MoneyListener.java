package fr.lifesteal.cubeeconomy.core.listener;

import fr.lifesteal.cubeeconomy.core.CubeEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MoneyListener implements Listener {
    private final Economy economy;

    public MoneyListener(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!economy.hasAccount(player)) {
            economy.createPlayerAccount(player);
        }
    }
}
