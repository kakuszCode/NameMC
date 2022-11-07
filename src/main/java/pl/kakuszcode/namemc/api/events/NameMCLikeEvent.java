package pl.kakuszcode.namemc.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class NameMCLikeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final UUID premiumUniqueId;

    public NameMCLikeEvent(Player player, UUID premiumUniqueId) {
        this.player = player;
        this.premiumUniqueId = premiumUniqueId;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public UUID getPremiumUniqueId() {
        return premiumUniqueId;
    }

    public Player getPlayer() {
        return player;
    }
}
