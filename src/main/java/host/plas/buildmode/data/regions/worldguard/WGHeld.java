package host.plas.buildmode.data.regions.worldguard;

import host.plas.bou.compat.HeldHolder;

public class WGHeld extends HeldHolder {
    public WGHeld() {
        super("WorldGuard", new WGHolderCreator());
    }
}
