package pl.kakuszcode.namemc.database;

import org.bukkit.plugin.java.JavaPlugin;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.List;
import java.util.logging.Logger;

public interface Database {
    void connect(String password, Logger logger);
    void insertNameMCUser(NameMCUser user, JavaPlugin plugin);
    List<NameMCUser> getNameMCUsers(Logger logger);
}
