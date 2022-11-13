package pl.kakuszcode.namemc.user.service;

import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class UserService {
    private final ConcurrentHashMap<UUID, NameMCUser> users;
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
        pendingUsers.add(uuid);
    }
    public Optional<NameMCUser> getNameMCUser(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }
    public void load(Database database, Logger logger) {
        database.getNameMCUsers(logger).forEach(nameMCUser -> users.put(nameMCUser.getUniqueId(), nameMCUser));
    }
}
