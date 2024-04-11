package fr.lifesteal.cubeeconomy.core.data;

import java.util.UUID;

public class Account {

    private final UUID playerUUID;
    private double amount;

    public Account(UUID playerUUID, double amount) {
        this.playerUUID = playerUUID;
        this.amount = amount;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public double getAmount() {
        return amount;
    }

    public boolean has(double amount) {
        return this.amount >= amount;
    }

    public void remove(double amount) {
        this.amount -= amount;
    }

    public void add(double amount) {
        this.amount += amount;
    }
}
