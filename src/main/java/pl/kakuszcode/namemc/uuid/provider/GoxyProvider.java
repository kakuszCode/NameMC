package pl.kakuszcode.namemc.uuid.provider;

import org.bukkit.entity.Player;
import pl.goxy.minecraft.api.GoxyApi;
import pl.goxy.minecraft.api.player.GoxyPlayer;
import pl.kakuszcode.namemc.uuid.UUIDInterface;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class GoxyProvider implements UUIDInterface {

    @Override
    public CompletableFuture<UUID> getUUIDByPlayer(Player p) {
        CompletableFuture<UUID> completableFuture = new CompletableFuture<>();
        GoxyPlayer player = GoxyApi.getPlayerStorage().getPlayer(p.getUniqueId());
        if (player != null && player.isPremium()) {
            completableFuture.complete(player.getRealUniqueId());
        } else {
            completableFuture.complete(null);
        }
        return completableFuture;
    }
}
