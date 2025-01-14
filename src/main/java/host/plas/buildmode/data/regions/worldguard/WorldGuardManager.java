package host.plas.buildmode.data.regions.worldguard;

import host.plas.buildmode.BuildMode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Optional;

public class WorldGuardManager {
    @Getter @Setter
    private static WGHeld held;
    private static boolean enabled;

    public static void init() {
        try {
            held = new WGHeld();
            enabled = true;
        } catch (Throwable t) {
            BuildMode.getInstance().logInfo("WorldGuard not found, disabling WorldGuard support.");
        }
    }

    public static boolean isEnabled() {
        return enabled && held != null && held.isEnabled();
    }

    public static boolean hasRegionLocationSafe(Location location) {
        if (isEnabled()) {
            return ((WGHolder) held.getHolder()).getRegionList().hasLocation(location);
        } else {
            return false;
        }
    }

    public static boolean canActInRegionSafe(Location location) {
        if (isEnabled()) {
            return ((WGHolder) held.getHolder()).canActInRegion(location);
        } else {
            return true;
        }
    }

    public static void loadRegionListSafe() {
        if (isEnabled()) {
            ((WGHolder) held.getHolder()).getRegionList();
        }
    }
}
