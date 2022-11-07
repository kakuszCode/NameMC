package pl.kakuszcode.namemc;


import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kakuszcode.namemc.commands.NameMCCommand;
import pl.kakuszcode.namemc.config.Configuration;
import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.listener.NameMCListener;
import pl.kakuszcode.namemc.task.NameMCTask;
import pl.kakuszcode.namemc.user.service.UserService;

import java.io.File;

public final class NameMC extends JavaPlugin {

    private static NameMC instance;

    private static Logger logger;
    private UserService userService;

    private Database database;
    private Configuration configuration;
    public static NameMC getInstance() {
        return instance;
    }

    public static Logger getSl4fjLogger() {
        return logger;
    }

    @Override
    public void onEnable() {
        logger = LoggerFactory.getLogger(getLogger().getName());
        instance = this;
        configuration = ConfigManager.create(Configuration.class, it ->
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit())
                        .withBindFile(new File(getDataFolder(), "config.yml"))
                        .saveDefaults()
                        .load()
        );

        try {
            database = configuration.getDatabaseEnum().getDatabaseClass().newInstance();
        } catch (Exception e) {
            logger.error("Błąd: " + e);
        }
        userService = new UserService();
        switch (configuration.getDatabaseEnum()) {
            case H2:
                database.connect(configuration.getH2Jdbc());
                break;
            case MYSQL:
                database.connect(configuration.getMysqlJdbc() + ":" + configuration.getMysqlUsername() + ":" + configuration.getMysqlPassword());
                break;
            case MONGODB:
                database.connect(configuration.getMongodbJdbc());
                break;
            case POSTGRESQL:
                database.connect(configuration.getPostGreSQLJdbc() + ":" + configuration.getPostGreSQLUsername() + ":" + configuration.getPostGreSQLPassword());
                break;
        }
        userService.load(database);
        LiteCommands<CommandSender> liteCommands = LiteBukkitFactory.builder(getServer(), "kakusz-namemc")
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("&4Błąd: &cMusisz być graczem aby wywołać tą komendę!"))
                .command(NameMCCommand.class)
                .register();
        getServer().getPluginManager().registerEvents(new NameMCListener(), this);
        new NameMCTask().runTaskTimer(this, 20L, 20L);
    }

    public UserService getUserService() {
        return userService;
    }


    public Database getDB() {
        return database;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
