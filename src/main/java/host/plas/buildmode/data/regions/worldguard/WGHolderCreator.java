package host.plas.buildmode.data.regions.worldguard;

import host.plas.bou.compat.HolderCreator;

public class WGHolderCreator implements HolderCreator {
    @Override
    public WGHolder get() {
        return new WGHolder();
    }
}
