package pl.kakuszcode.namemc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.api.events.NameMCLikeEvent;
import pl.kakuszcode.namemc.request.NameMCRequest;
import pl.kakuszcode.namemc.user.NameMCUser;
import pl.kakuszcode.namemc.user.service.UserService;
import pl.kakuszcode.namemc.utils.ChatHelper;
import pl.kakuszcode.namemc.uuid.UUIDInterface;

public class NameMCCommand implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHelper.fixColor("&4Błąd: &cMusisz być graczem aby wywołać tą komendę!"));
            return false;
        }
        Player player = (Player) sender;
        UserService service = NameMC.getInstance().getUserService();
        if (service.getUsers().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatHelper.fixColor(NameMC.getInstance().getConfiguration().getMessageIsReward()));
            return false;
        }
        if (service.getPendingUsers().contains(player.getUniqueId())) {
            player.sendMessage(ChatHelper.fixColor(NameMC.getInstance().getConfiguration().getMessageIsPending()));
            return false;
        }
        NameMC.getInstance().getUserService().getPendingUsers().add(player.getUniqueId());
        UUIDInterface.getUUIDPremiumByPlayer(player).thenAccept(uuid -> {
            if (uuid == null) {
                player.sendMessage(ChatHelper.fixColor(NameMC.getInstance().getConfiguration().getMessageIsNotPremium()));
                return;
            }
            NameMCRequest.isLiked(uuid)
                    .thenAccept(b -> {
                        if (!b){
                            player.sendMessage(ChatHelper.fixColor(NameMC.getInstance().getConfiguration().getMessageIsNotLiked()));
                            return;

                        }
                        NameMCUser nameMCUser = new NameMCUser(player, uuid);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (String s : NameMC.getInstance().getConfiguration().getCommandsOnAccept()) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
                                }
                                NameMCLikeEvent event = new NameMCLikeEvent(player, uuid);
                                Bukkit.getPluginManager().callEvent(event);
                            }
                        }.runTask(NameMC.getInstance());
                    }).exceptionally(throwable -> {
                        NameMC.getSl4fjLogger().error("Błąd: ", throwable);
                        player.sendMessage("Błąd! Poinformuj administratora!");
                        return null;
                    });
        }).exceptionally(throwable -> {
            NameMC.getSl4fjLogger().error("Błąd: ", throwable);
            player.sendMessage("Błąd! Poinformuj administratora!");
            return null;
        });
        NameMC.getInstance().getUserService().getPendingUsers().remove(player.getUniqueId());
        return false;
    }
}
