package host.plas.buildmode;

import host.plas.bou.BetterPlugin;
import host.plas.buildmode.commands.BuildCMD;
import host.plas.buildmode.config.DatabaseConfig;
import host.plas.buildmode.config.MainConfig;
import host.plas.buildmode.data.PlayerManager;
import host.plas.buildmode.data.regions.worldguard.WorldGuardManager;
import host.plas.buildmode.database.BuildOperator;
import host.plas.buildmode.events.MainListener;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class BuildMode extends BetterPlugin {
    @Getter @Setter
    private static BuildMode instance;
    @Getter @Setter
    private static MainConfig mainConfig;
    @Getter @Setter
    private static DatabaseConfig databaseConfig;

    @Getter @Setter
    private static BuildOperator database;

    @Getter @Setter
    private static MainListener mainListener;

    @Getter @Setter
    private static BuildCMD buildCMD;

    public BuildMode() {
        super();
    }

    @Override
    public void onBaseEnabled() {
        // Plugin startup logic
        setInstance(this);

        setMainConfig(new MainConfig());
        setDatabaseConfig(new DatabaseConfig());

        // Must be after MainConfig instantiation.
        WorldGuardManager.init();
        WorldGuardManager.loadRegionListSafe();

        setDatabase(new BuildOperator());

        setMainListener(new MainListener());

        setBuildCMD(new BuildCMD());
    }

    @Override
    public void onBaseDisable() {
        // Plugin shutdown logic
        PlayerManager.getLoadedPlayers().forEach(data -> {
            data.save(false);
            data.unload();
        });
    }
}
