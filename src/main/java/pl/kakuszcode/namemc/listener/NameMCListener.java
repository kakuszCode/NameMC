package pl.kakuszcode.namemc.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.kakuszcode.namemc.NameMC;
import pl.kakuszcode.namemc.api.events.NameMCLikeEvent;

public class NameMCListener implements Listener {
    @EventHandler
    public void onNameMCLike(NameMCLikeEvent event) {
        for (String s : NameMC.getInstance().getConfiguration().getCommandsOnAccept()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", event.getPlayer().getName()));
        }
    }
}
