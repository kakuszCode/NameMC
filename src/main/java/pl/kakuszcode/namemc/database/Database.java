package pl.kakuszcode.namemc.database;

import org.bukkit.plugin.java.JavaPlugin;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.List;

public interface Database {
    void connect(String password, JavaPlugin plugin);
    void insertNameMCUser(NameMCUser user, JavaPlugin plugin);
    List<NameMCUser> getNameMCUsers(JavaPlugin plugin);
}
