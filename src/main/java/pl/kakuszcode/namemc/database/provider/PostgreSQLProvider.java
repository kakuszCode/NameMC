package pl.kakuszcode.namemc.database.provider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLProvider implements Database {
    private Connection connection;

    @Override
    public void connect(String password, JavaPlugin plugin) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            plugin.getLogger().severe("Błąd: " + e);
        }
        HikariConfig config = new HikariConfig();
        String[] passArray = password.split(":");
        config.setJdbcUrl(passArray[0]);
        config.setUsername(passArray[1]);
        config.setPassword(passArray[2]);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource db = new HikariDataSource(config);
        try {
            connection = db.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `NameMCUsers` (`uuid` VARCHAR NOT NULL, `premiumUuid` VARCHAR NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("Problem z połączeniem z bazą danych!" + e);
        }
    }

    @Override
    public void insertNameMCUser(NameMCUser user, JavaPlugin plugin) {
        new BukkitRunnable() {
            public void run() {
                try {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO `NameMCUsers` (`uuid`, `premiumUuid`) VALUES (?, ?)");
                    ps.setString(1, user.getUniqueId().toString());
                    ps.setString(2, user.getPremiumUniqueId().toString());
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException e) {
                    plugin.getLogger().severe("Problem z bazą danych!" + e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    @Override
    public List<NameMCUser> getNameMCUsers(JavaPlugin plugin) {
        List<NameMCUser> users = new ArrayList<>();
        try {
            ResultSet set = connection.prepareStatement("SELECT * FROM `NameMCUsers`").executeQuery();
            while (set.next()) {
                NameMCUser user = new NameMCUser(set);
                users.add(user);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Błąd:" + e);
        }
        return users;
    }
}
