package pl.kakuszcode.namemc.database.provider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.database.Database;
import pl.kakuszcode.namemc.user.NameMCUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//this database is shitty
public class MongoDBProvider implements Database {

    private MongoCollection<Document> collection;

    @Override
    public void connect(String password) {
        MongoClient client = MongoClients.create(password);
        collection = client.getDatabase("namemc").getCollection("users");
    }

    @Override
    public void insertNameMCUser(NameMCUser user) {
        new BukkitRunnable(){
            @Override
            public void run() {
                collection.insertOne(new Document("uniqueId", user.getUniqueId().toString()).append("premiumUniqueId", user.getPremiumUniqueId().toString()));
            }
        }.runTaskAsynchronously(NameMC.getInstance());
    }

    @Override
    public List<NameMCUser> getNameMCUsers() {
        List<NameMCUser> users = new ArrayList<>();
        if (collection != null) {
            collection.find().forEach(document -> users.add(new NameMCUser(document)));
        }

        return users;
    }


}
