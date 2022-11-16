package pl.kakuszcode.namemc.user.service;

import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class UserService {
    private final Map<UUID, NameMCUser> users;
    private final List<UUID> pendingUsers;

    public UserService() {
        this.users = new ConcurrentHashMap<>();
        this.pendingUsers = new ArrayList<>();
    }

    public boolean containsPendingUser(UUID uuid) {
        return pendingUsers.contains(uuid);
    }
    public void addPendingUser(UUID uuid){
        pendingUsers.add(uuid);
    }
    public void removePendingUser(UUID uuid){
        pendingUsers.remove(uuid);
    }
    public Optional<NameMCUser> getNameMCUser(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }
    public void load(Database database, Logger logger) {
        for (NameMCUser user : database.getNameMCUsers(logger)) {
            users.put(user.getUniqueId(), user);
        }
    }
}
