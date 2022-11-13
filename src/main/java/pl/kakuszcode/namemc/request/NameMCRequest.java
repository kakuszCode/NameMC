package pl.kakuszcode.namemc.request;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pl.kakuszcode.namemc.config.Configuration;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class NameMCRequest {
    private final OkHttpClient client;
    private final Configuration configuration;

    public NameMCRequest(Configuration configuration) {
        this.configuration = configuration;
        this.client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .build();
    }
    public void stopRequest(){
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }

    public CompletableFuture<Boolean> isLiked(UUID uuid) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        client.newCall(new Request.Builder()
               .url("https://api.namemc.com/server/" + configuration.getServerIP() + "/likes?profile=" + uuid.toString())
               .build())
               .enqueue(new Callback() {
                   @Override
                   public void onFailure(@NotNull Call call, @NotNull IOException e) {
                       future.completeExceptionally(e);
                   }

                   @Override
                   public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.body().string().equalsIgnoreCase("true")) {
                            future.complete(true);
                            return;
                        }
                        future.complete(false);
                   }
               });
        return future;
    }
}
