package host.plas.buildmode.data;

import host.plas.bou.utils.SenderUtils;
import host.plas.buildmode.BuildMode;
import host.plas.buildmode.config.messages.MessageContainer;
import host.plas.buildmode.data.regions.RegionList;
import host.plas.buildmode.data.regions.worldguard.WorldGuardManager;
import host.plas.buildmode.data.worlds.WorldList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import tv.quaint.objects.Identifiable;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter @Setter
public class PlayerData implements Identifiable {
    private String identifier;

    private boolean toggled;

    private AtomicBoolean fullyLoaded;

    public PlayerData(String identifier) {
        this.identifier = identifier;

        this.toggled = BuildMode.getMainConfig().getBuildDefault();

        this.fullyLoaded = new AtomicBoolean(false);
    }

    public PlayerData(Player player) {
        this(player.getUniqueId().toString());
    }

    public Optional<Player> asPlayer() {
        try {
            return Optional.ofNullable(Bukkit.getPlayer(UUID.fromString(identifier)));
        } catch (Throwable e) {
            BuildMode.getInstance().logWarning("Failed to get player from identifier: " + identifier, e);

            return Optional.empty();
        }
    }

    public Optional<OfflinePlayer> asOfflinePlayer() {
        try {
            return Optional.of(Bukkit.getOfflinePlayer(UUID.fromString(identifier)));
        } catch (Throwable e) {
            BuildMode.getInstance().logWarning("Failed to get offline player from identifier: " + identifier, e);

            return Optional.empty();
        }
    }

    public boolean isOnline() {
        return asPlayer().isPresent();
    }

    public void load() {
        PlayerManager.loadPlayer(this);
    }

    public void unload() {
        PlayerManager.unloadPlayer(this);
    }

    public void save() {
        PlayerManager.savePlayer(this);
    }

    public void save(boolean async) {
        PlayerManager.savePlayer(this, async);
    }

    public void augment(CompletableFuture<Optional<PlayerData>> future) {
        fullyLoaded.set(false);

        future.whenComplete((data, error) -> {
            if (error != null) {
                BuildMode.getInstance().logWarning("Failed to augment player data", error);

                this.fullyLoaded.set(true);
                return;
            }

            if (data.isPresent()) {
                PlayerData newData = data.get();

                this.toggled = newData.isToggled();
            }

            this.fullyLoaded.set(true);
        });
    }

    public boolean isFullyLoaded() {
        return fullyLoaded.get();
    }

    public boolean hasBuildPermission() {
        return asPlayer().map(player -> player.hasPermission(BuildMode.getMainConfig().getBuildPermission())).orElse(false);
    }

    public boolean checkCanAct(Location location) {
        if (isToggled()) return true;
        if (! hasBuildPermission()) return true;

        boolean canAct = true;
        WorldList worlds = BuildMode.getMainConfig().getWorldList();
        if (worlds.hasLocation(location)) {
            canAct = worlds.isWhitelist();
        } else {
            canAct = worlds.isBlacklist();
        }
        if (! canAct) return false;

        if (WorldGuardManager.isEnabled()) {
            canAct = WorldGuardManager.canActInRegionSafe(location);
        }

        return canAct;
    }

    public void checkAndAct(Cancellable event, Player player, Location location) {
        if (checkCanAct(location)) return;

        event.setCancelled(true);

        if (BuildMode.getMainConfig().getSendCannotBuildMessage()) {
            MessageContainer container = BuildMode.getMainConfig().getMessageContainer();
            if (!container.getCannotBuildString().isEmpty()) SenderUtils.getSender(player).sendMessage(container.getCannotBuildString().getString());
        }
    }
}
