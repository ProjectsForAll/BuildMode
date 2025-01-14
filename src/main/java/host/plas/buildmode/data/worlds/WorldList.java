package host.plas.buildmode.data.worlds;

import host.plas.buildmode.data.StringedList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.concurrent.ConcurrentSkipListSet;

@Getter @Setter
public class WorldList extends StringedList {
    public WorldList(boolean blacklist, ConcurrentSkipListSet<String> worlds) {
        super("worlds", blacklist, worlds);
    }

    public WorldList() {
        super("worlds");
    }

    public ConcurrentSkipListSet<String> getWorlds() {
        return getStrings();
    }

    public boolean hasWorld(String world) {
        return getWorlds().contains(world);
    }

    public boolean hasWorld(World world) {
        return hasWorld(world.getName());
    }

    public boolean hasLocation(Location location) {
        return hasWorld(location.getWorld());
    }
}
