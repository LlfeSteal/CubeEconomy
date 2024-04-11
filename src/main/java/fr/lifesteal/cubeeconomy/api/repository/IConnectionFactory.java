package fr.lifesteal.cubeeconomy.api.repository;

import java.sql.Connection;

public interface IConnectionFactory {
    Connection getConnection();
}
