package pl.kakuszcode.namemc.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.api.events.NameMCLikeEvent;
import pl.kakuszcode.namemc.user.NameMCUser;
import pl.kakuszcode.namemc.utils.ChatHelper;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//fuck api bukkit
public class NameMCTask extends BukkitRunnable {
    private static final ConcurrentHashMap<Player, NameMCUser> namemc = new ConcurrentHashMap<>();
    @Override
    public void run() {
        for (Map.Entry<Player, NameMCUser> entry : namemc.entrySet()) {
            NameMCLikeEvent nameMCLikeEvent = new NameMCLikeEvent(entry.getKey(), entry.getValue().getPremiumUniqueId());
            Bukkit.getPluginManager().callEvent(nameMCLikeEvent);
            NameMC.getInstance().getUserService().getUsers().put(entry.getKey().getUniqueId(), entry.getValue());
            NameMC.getInstance().getDB().insertNameMCUser(entry.getValue());
            entry.getKey().sendMessage(ChatHelper.fixColor(NameMC.getInstance().getConfiguration().getMessageIsAccept()));
            namemc.remove(entry.getKey(), entry.getValue());
        }
    }

    public static ConcurrentHashMap<Player, NameMCUser> getNamemc() {
        return namemc;
    }
}
