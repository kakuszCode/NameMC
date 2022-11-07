package pl.kakuszcode.namemc.uuid.provider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import pl.kakuszcode.namemc.uuid.UUIDInterface;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MojangAPIProvider implements UUIDInterface {
    private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .build();

    @Override
    public CompletableFuture<UUID> getUUIDByPlayer(Player p) {
        CompletableFuture<UUID> completableFuture = new CompletableFuture<>();
        client.newCall(new Request.Builder().url("https://api.ashcon.app/mojang/v2/user/" + p.getName()).build()).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        completableFuture.completeExceptionally(e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() != 200){
                            completableFuture.complete(null);
                            return;
                        }
                        JsonObject jsonObject = new JsonParser().parse(response.body().string()).getAsJsonObject();
                        completableFuture.complete(UUID.fromString(jsonObject.get("uuid").getAsString()));
                    }
                }
        );
        return completableFuture;
    }
}
