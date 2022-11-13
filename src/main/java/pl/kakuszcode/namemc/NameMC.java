package pl.kakuszcode.namemc;


import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kakuszcode.namemc.commands.NameMCCommand;
import pl.kakuszcode.namemc.config.Configuration;
import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.request.NameMCRequest;
import pl.kakuszcode.namemc.user.service.UserService;

import java.io.File;
import java.util.logging.Logger;

public final class NameMC extends JavaPlugin {

    private Database database;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 16828);
        Logger logger = getLogger();
        Configuration configuration = ConfigManager.create(Configuration.class, it ->
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit())
                        .withBindFile(new File(getDataFolder(), "config.yml"))
                        .saveDefaults()
                        .load()
        );

        try {
            database = configuration.getDatabaseEnum().getDatabaseClass().newInstance();
        } catch (Exception e) {
            logger.severe("Błąd: " + e);
        }
        UserService userService = new UserService();
        switch (configuration.getDatabaseEnum()) {
            case H2:
                database.connect(configuration.getH2Jdbc(),this);
                break;
            case MYSQL:
                database.connect(configuration.getMysqlJdbc() + ":" + configuration.getMysqlUsername() + ":" + configuration.getMysqlPassword(),this);
                break;
            case MONGODB:
                database.connect(configuration.getMongodbJdbc(),this);
                break;
            case POSTGRESQL:
                database.connect(configuration.getPostGreSQLJdbc() + ":" + configuration.getPostGreSQLUsername() + ":" + configuration.getPostGreSQLPassword(), this);
                break;
        }
        NameMCRequest nameMCRequest = new NameMCRequest(configuration);
        userService.load(database, this);
        getCommand("namemc").setExecutor(new NameMCCommand(this, configuration, userService, nameMCRequest));
    }

}
