package host.plas.buildmode.config;

import host.plas.bou.sql.ConnectorSet;
import host.plas.bou.sql.DatabaseType;
import host.plas.buildmode.BuildMode;
import tv.quaint.storage.resources.flat.simple.SimpleConfiguration;

public class DatabaseConfig extends SimpleConfiguration {
    public DatabaseConfig() {
        super("database-config.yml", BuildMode.getInstance(), true);
    }

    @Override
    public void init() {
        getDatabaseHost();
        getDatabasePort();
        getDatabaseUsername();
        getDatabasePassword();
        getDatabaseDatabase();
        getDatabaseTablePrefix();
        getDatabaseType();
        getSqliteFileName();
    }

    public String getDatabaseHost() {
        reloadResource();

        return getOrSetDefault("database.host", "localhost");
    }

    public int getDatabasePort() {
        reloadResource();

        return getOrSetDefault("database.port", 3306);
    }

    public String getDatabaseUsername() {
        reloadResource();

        return getOrSetDefault("database.username", "root");
    }

    public String getDatabasePassword() {
        reloadResource();

        return getOrSetDefault("database.password", "password");
    }

    public String getDatabaseDatabase() {
        reloadResource();

        return getOrSetDefault("database.database", "buildmode");
    }

    public String getDatabaseTablePrefix() {
        reloadResource();

        return getOrSetDefault("database.table-prefix", "bm_");
    }

    public DatabaseType getDatabaseType() {
        reloadResource();

        return DatabaseType.valueOf(getOrSetDefault("database.type", DatabaseType.SQLITE.name()).toUpperCase());
    }

    public String getSqliteFileName() {
        reloadResource();

        return getOrSetDefault("database.sqlite-file-name", "buildmode.db");
    }

    public ConnectorSet getConnectorSet() {
        return new ConnectorSet(
                getDatabaseType(),
                getDatabaseHost(),
                getDatabasePort(),
                getDatabaseDatabase(),
                getDatabaseUsername(),
                getDatabasePassword(),
                getDatabaseTablePrefix(),
                getSqliteFileName()
        );
    }
}
