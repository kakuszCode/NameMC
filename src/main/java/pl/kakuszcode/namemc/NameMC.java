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
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public final class NameMC extends JavaPlugin {

    private Database database;
    private NameMCRequest nameMCRequest;

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
        CompletableFuture.runAsync(() -> {
            switch (configuration.getDatabaseEnum()) {
                case H2:
                    database.connect(configuration.getH2Jdbc(),getLogger());
                    break;
                case MYSQL:
                    database.connect(configuration.getMysqlJdbc() + ":" + configuration.getMysqlUsername() + ":" + configuration.getMysqlPassword(),getLogger());
                    break;
                case MONGODB:
                    database.connect(configuration.getMongodbJdbc(),getLogger());
                    break;
                case POSTGRESQL:
                    database.connect(configuration.getPostGreSQLJdbc() + ":" + configuration.getPostGreSQLUsername() + ":" + configuration.getPostGreSQLPassword(), getLogger());
                    break;
            }
        });
        this.nameMCRequest = new NameMCRequest(configuration);
        userService.load(database, getLogger());
        this.getCommand("namemc").setExecutor(new NameMCCommand(this, configuration, userService, nameMCRequest));
    }

    @Override
    public void onDisable() {
        nameMCRequest.stopRequest();
    }
}
