package pl.kakuszcode.namemc.user;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kakuszcode.namemc.NameMC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NameMCUser {
    private final UUID uniqueId;
    private final UUID premiumUniqueId;

    public NameMCUser(Player p, UUID premiumUniqueId) {
        this.uniqueId = p.getUniqueId();
        this.premiumUniqueId = premiumUniqueId;
    }

    public NameMCUser(ResultSet resultSet) throws SQLException {
        this.uniqueId = UUID.fromString(resultSet.getString(1));
        this.premiumUniqueId = UUID.fromString(resultSet.getString(2));
    }
    public NameMCUser(Document document){
        this.uniqueId = UUID.fromString(document.getString("uniqueId"));
        this.premiumUniqueId = UUID.fromString(document.getString("premiumUniqueId"));
    }

    public UUID getPremiumUniqueId() {
        return premiumUniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
