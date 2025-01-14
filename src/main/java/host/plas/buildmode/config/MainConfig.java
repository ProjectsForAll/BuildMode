package host.plas.buildmode.config;

import host.plas.buildmode.BuildMode;
import host.plas.buildmode.config.messages.MessageContainer;
import host.plas.buildmode.data.regions.RegionList;
import host.plas.buildmode.data.regions.worldguard.WorldGuardManager;
import host.plas.buildmode.data.worlds.WorldList;
import tv.quaint.storage.resources.flat.simple.SimpleConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class MainConfig extends SimpleConfiguration {
    public MainConfig() {
        super("config.yml", BuildMode.getInstance(), true);
    }

    @Override
    public void init() {
        getMessageContainer();

        getSendCannotBuildMessage();

        getBuildPermission();
        getBuildDefault();

        getWorldList();
    }

    public MessageContainer getMessageContainer() {
        reloadResource();

        MessageContainer container = new MessageContainer();

        String toggleString = getOrSetDefault("messages.toggle", "&eBuild mode has been toggled to &r%status%&8.");
        container.setToggleString(toggleString);

        String cannotBuildString = getOrSetDefault("messages.cannot-build", "&cYou cannot build in this at this location&8.");
        container.setCannotBuildString(cannotBuildString);

        String statusEnabledString = getOrSetDefault("messages.status.enabled", "&aenabled");
        container.setStatusEnabledString(statusEnabledString);

        String statusDisabledString = getOrSetDefault("messages.status.disabled", "&cdisabled");
        container.setStatusDisabledString(statusDisabledString);

        String errorNoPermissionString = getOrSetDefault("messages.error.no-permission", "&cYou do not have permission to do that&8.");
        container.setErrorNoPermissionString(errorNoPermissionString);

        return container;
    }

    public boolean getSendCannotBuildMessage() {
        reloadResource();

        return getOrSetDefault("messages.send-cannot-build", false);
    }

    public String getBuildPermission() {
        reloadResource();

        return getOrSetDefault("build.permission", "buildmode.use");
    }

    public boolean getBuildDefault() {
        reloadResource();

        return getOrSetDefault("build.default", false);
    }

    public WorldList getWorldList() {
        reloadResource();

        boolean blacklist = getOrSetDefault("worlds.is-blacklist", true);
        List<String> worlds = getOrSetDefault("worlds.list", new ArrayList<>());

        return new WorldList(blacklist, new ConcurrentSkipListSet<>(worlds));
    }
}
