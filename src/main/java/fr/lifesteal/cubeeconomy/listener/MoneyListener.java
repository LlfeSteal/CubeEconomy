package fr.lifesteal.cubeeconomy.listener;

import fr.lifesteal.cubeeconomy.CubeEconomy;
import fr.lifesteal.cubeeconomy.utils.Utils;
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
            Utils.log("Le joueur n'a pas de compte");
            Economy economy = CubeEconomy.getEconomy();
            economy.createPlayerAccount(player);
        } else Utils.log("Le joueur a un compte");
    }
}
