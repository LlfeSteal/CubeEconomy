package fr.lifesteal.cubeeconomy.api.config;

public interface IConfigurationService {
    void init();
    String getPluginPrefix();

    String getMessage(String key);

    String getDatabasePrefix();
    String getDatabaseHost();
    String getDatabaseName();
    int getDatabasePort();
    String getDatabaseUser();
    String getDatabasePassword();
    double getDefaultMoney();
    String getSingleMoneyName();
    String getMultipleMoneyName();

    String getMoneySymbol();
}
