package pl.kakuszcode.namemc.database.provider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2Provider implements Database {
    private Connection connection;

    @Override
    public void connect(String password) {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            NameMC.getSl4fjLogger().error("Błąd: ", e);
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(password);
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
            NameMC.getSl4fjLogger().error("Problem z połączeniem z bazą danych!", e);
        }
    }

    @Override
    public void insertNameMCUser(NameMCUser user) {
        new BukkitRunnable() {
            public void run() {
                try {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO `NameMCUsers` (`uuid`, `premiumUuid`) VALUES (?, ?)");
                    ps.setString(1, user.getUniqueId().toString());
                    ps.setString(2, user.getPremiumUniqueId().toString());
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException e) {
                    NameMC.getSl4fjLogger().error("Problem z bazą danych!", e);
                }
            }
        }.runTaskAsynchronously(NameMC.getInstance());
    }

    @Override
    public List<NameMCUser> getNameMCUsers() {
        List<NameMCUser> users = new ArrayList<>();
        try {
            ResultSet set = connection.prepareStatement("SELECT * FROM `NameMCUsers`").executeQuery();
            while (set.next()) {
                NameMCUser user = new NameMCUser(set);
                users.add(user);
            }
        } catch (SQLException e) {
            NameMC.getSl4fjLogger().error("Błąd:", e);
        }
        return users;
    }
}
