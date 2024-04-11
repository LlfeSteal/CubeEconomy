package fr.lifesteal.cubeeconomy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private String dB_host, dB_name, dB_user, dB_pass, dB_prefix;
    private int dB_port;

    public DB(String dB_host, String dB_name, int dB_port, String dB_user, String dB_pass, String dB_prefix) {
        this.dB_host = dB_host;
        this.dB_name = dB_name;
        this.dB_user = dB_user;
        this.dB_pass = dB_pass;
        this.dB_port = dB_port;
        this.dB_prefix = dB_prefix;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + this.dB_host + ":" + dB_port + "/" + this.dB_name, this.dB_user, this.dB_pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getdB_prefix() {
        return dB_prefix;
    }
}
