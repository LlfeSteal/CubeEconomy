package fr.lifesteal.cubeeconomy.core.repository;

import fr.lifesteal.cubeeconomy.api.config.IConfigurationService;
import fr.lifesteal.cubeeconomy.api.repository.IConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory implements IConnectionFactory {
    public final IConfigurationService configurationService;
    public final Logger logger;

    public ConnectionFactory(Logger logger, IConfigurationService configurationService) {
        this.configurationService = configurationService;
        this.logger = logger;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(getConnectionString(), this.configurationService.getDatabaseUser(), this.configurationService.getDatabasePassword());
        } catch (SQLException e) {
            this.logger.log(Level.SEVERE, "Database connection failed", e);
            return null;
        }
    }

    private String getConnectionString() {
        return "jdbc:mysql://" + this.configurationService.getDatabaseHost()
                + ":" + this.configurationService.getDatabasePort()
                + "/" + this.configurationService.getDatabaseName();
    }
}
