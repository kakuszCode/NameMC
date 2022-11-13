package pl.kakuszcode.namemc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.api.events.NameMCLikeEvent;
import pl.kakuszcode.namemc.config.Configuration;
import pl.kakuszcode.namemc.request.NameMCRequest;
import pl.kakuszcode.namemc.user.NameMCUser;
import pl.kakuszcode.namemc.user.service.UserService;
import pl.kakuszcode.namemc.utils.ChatHelper;
import pl.kakuszcode.namemc.uuid.UUIDInterface;

public class NameMCCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Configuration configuration;
    private final UserService service;
    private final NameMCRequest nameMCRequest;

    public NameMCCommand(JavaPlugin plugin, Configuration configuration, UserService userService, NameMCRequest nameMCRequest) {
        this.plugin = plugin;
        this.configuration = configuration;
        this.service = userService;
        this.nameMCRequest = nameMCRequest;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHelper.fixColor("&4Błąd: &cMusisz być graczem aby wywołać tą komendę!"));
            return false;
        }
        Player player = (Player) sender;

        if (service.getUsers().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatHelper.fixColor(configuration.getMessageIsReward()));
            return false;
        }
        if (service.getPendingUsers().contains(player.getUniqueId())) {
            player.sendMessage(ChatHelper.fixColor(configuration.getMessageIsPending()));
            return false;
        }
        service.getPendingUsers().add(player.getUniqueId());
        UUIDInterface.getUUIDPremiumByPlayer(player).thenAccept(uuid -> {
            if (uuid == null) {
                player.sendMessage(ChatHelper.fixColor(configuration.getMessageIsNotPremium()));
                return;
            }
            nameMCRequest.isLiked(uuid)
                    .thenAccept(b -> {
                        if (!b){
                            player.sendMessage(ChatHelper.fixColor(configuration.getMessageIsNotLiked()));
                            return;

                        }
                        NameMCUser nameMCUser = new NameMCUser(player, uuid);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (String s : configuration.getCommandsOnAccept()) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
                                }
                                NameMCLikeEvent event = new NameMCLikeEvent(player, uuid);
                                Bukkit.getPluginManager().callEvent(event);
                            }
                        }.runTask(plugin);
                        player.sendMessage(ChatHelper.fixColor(configuration.getMessageIsAccept()));
                    }).exceptionally(throwable -> {
                        plugin.getLogger().severe("Błąd: " + throwable);
                        player.sendMessage("Błąd! Poinformuj administratora!");
                        return null;
                    });
        }).exceptionally(throwable -> {
            plugin.getLogger().severe("Błąd: " + throwable);
            player.sendMessage("Błąd! Poinformuj administratora!");
            return null;
        });
        service.getPendingUsers().remove(player.getUniqueId());
        return false;
    }
}
