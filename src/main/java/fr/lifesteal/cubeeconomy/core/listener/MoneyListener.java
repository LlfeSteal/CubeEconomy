package fr.lifesteal.cubeeconomy.core.listener;

import fr.lifesteal.cubeeconomy.core.CubeEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MoneyListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!CubeEconomy.getEconomy().hasAccount(player)) {
            Economy economy = CubeEconomy.getEconomy();
            economy.createPlayerAccount(player);
        }
    }
}
