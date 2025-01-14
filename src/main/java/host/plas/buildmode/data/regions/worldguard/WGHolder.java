package host.plas.buildmode.data.regions.worldguard;

import com.sk89q.worldguard.WorldGuard;
import host.plas.bou.compat.ApiHolder;
import host.plas.buildmode.BuildMode;
import host.plas.buildmode.data.regions.RegionList;
import host.plas.buildmode.data.worlds.WorldList;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class WGHolder extends ApiHolder<WorldGuard> {
    public WGHolder() {
        super("WorldGuard", (v) -> WorldGuard.getInstance());
    }

    public RegionList getRegionList() {
        BuildMode.getMainConfig().reloadResource();

        boolean blacklist = BuildMode.getMainConfig().getOrSetDefault("regions.is-blacklist", true);
        List<String> regions = BuildMode.getMainConfig().getOrSetDefault("regions.list", new ArrayList<>());

        return new RegionList(blacklist, new ConcurrentSkipListSet<>(regions));
    }

    public boolean canActInRegion(Location location) {
        boolean canAct = true;
        RegionList regions = getRegionList();
        if (regions.hasLocation(location)) {
            canAct = regions.isWhitelist();
        } else {
            canAct = regions.isBlacklist();
        }

        return canAct;
    }
}
