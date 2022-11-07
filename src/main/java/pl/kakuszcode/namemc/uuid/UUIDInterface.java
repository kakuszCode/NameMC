package pl.kakuszcode.namemc.uuid;

import org.bukkit.entity.Player;
import pl.kakuszcode.namemc.uuid.provider.GoxyProvider;
import pl.kakuszcode.namemc.uuid.provider.MojangAPIProvider;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UUIDInterface {
    static CompletableFuture<UUID> getUUIDPremiumByPlayer(Player p){
       try {
            Class.forName("pl.goxy.minecraft.api.GoxyApi");
            return new GoxyProvider().getUUIDByPlayer(p);
       } catch (ClassNotFoundException ignored) {
           return new MojangAPIProvider().getUUIDByPlayer(p);
       }
    }
    CompletableFuture<UUID> getUUIDByPlayer(Player p);

}
