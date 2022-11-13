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
    private final ConcurrentHashMap<UUID, NameMCUser> users = new ConcurrentHashMap<>();
    private final List<UUID> pendingUsers = new ArrayList<>();

    public List<UUID> getPendingUsers() {
        return pendingUsers;
    }
    public Optional<NameMCUser> getNameMCUser(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }
    public void load(Database database, Logger logger) {
        database.getNameMCUsers(logger).forEach(nameMCUser -> users.put(nameMCUser.getUniqueId(), nameMCUser));
    }
}
