package host.plas.buildmode.data.regions;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import host.plas.buildmode.data.StringedList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.concurrent.ConcurrentSkipListSet;

@Getter @Setter
public class RegionList extends StringedList {
    public RegionList(boolean blacklist, ConcurrentSkipListSet<String> worlds) {
        super("regions", blacklist, worlds);
    }

    public RegionList() {
        super("regions");
    }

    public ConcurrentSkipListSet<String> getRegions() {
        return getStrings();
    }

    public boolean hasRegion(String region) {
        return getRegions().contains(region);
    }

    public boolean hasRegion(ProtectedRegion region) {
        return hasRegion(region.getId());
    }

    public boolean hasLocation(Location location) {
        BukkitWorld world = new BukkitWorld(location.getWorld());
        RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        if (manager == null) {
            return false;
        }

        BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
        ApplicableRegionSet set = manager.getApplicableRegions(vector);

        boolean hasRegion = false;
        for (ProtectedRegion region : set) {
            if (hasRegion(region)) {
                hasRegion = true;
                break;
            }
        }

        return hasRegion;
    }
}
