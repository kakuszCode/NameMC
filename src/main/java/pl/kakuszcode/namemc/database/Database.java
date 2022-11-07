package pl.kakuszcode.namemc.database;

import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.List;

public interface Database {
    void connect(String password);
    void insertNameMCUser(NameMCUser user);
    List<NameMCUser> getNameMCUsers();
}
