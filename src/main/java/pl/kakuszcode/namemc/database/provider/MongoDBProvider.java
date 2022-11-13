package pl.kakuszcode.namemc.database.provider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//this database is shitty
public class MongoDBProvider implements Database {

    private MongoCollection<Document> collection;

    @Override
    public void connect(String password, Logger logger) {
        MongoClient client = MongoClients.create(password);
        collection = client.getDatabase("namemc").getCollection("users");
    }

    @Override
    public void insertNameMCUser(NameMCUser user, JavaPlugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                collection.insertOne(new Document("uniqueId", user.getUniqueId().toString()).append("premiumUniqueId", user.getPremiumUniqueId().toString()));
            }
        }.runTaskAsynchronously(plugin);
    }

    @Override
    public List<NameMCUser> getNameMCUsers(Logger logger) {
        List<NameMCUser> users = new ArrayList<>();
        if (collection != null) {
            collection.find().forEach(document -> users.add(new NameMCUser(document)));
        }

        return users;
    }


}
