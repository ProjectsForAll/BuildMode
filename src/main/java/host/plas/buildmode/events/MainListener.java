package host.plas.buildmode.events;

import host.plas.bou.events.ListenerConglomerate;
import host.plas.bou.utils.SenderUtils;
import host.plas.buildmode.BuildMode;
import host.plas.buildmode.config.messages.MessageContainer;
import host.plas.buildmode.data.PlayerData;
import host.plas.buildmode.data.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tv.quaint.events.BaseEventHandler;

public class MainListener implements ListenerConglomerate {
    public MainListener() {
        Bukkit.getPluginManager().registerEvents(this, BuildMode.getInstance());
        BaseEventHandler.bake(this, BuildMode.getInstance());
        BuildMode.getInstance().logInfo("Registered MainListener!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(BuildMode.getMainConfig().getBuildPermission())) {
            PlayerData data = PlayerManager.getOrCreatePlayer(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(BuildMode.getMainConfig().getBuildPermission())) {
            PlayerData data = PlayerManager.getOrCreatePlayer(player);
            data.save();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block =  event.getClickedBlock();
        if (block == null) return;
        Location location = block.getLocation();

        PlayerData data = PlayerManager.getOrCreatePlayer(player);
        data.checkAndAct(event, player, location);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        PlayerData data = PlayerManager.getOrCreatePlayer(player);
        data.checkAndAct(event, player, event.getBlock().getLocation());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        PlayerData data = PlayerManager.getOrCreatePlayer(player);
        data.checkAndAct(event, player, event.getBlock().getLocation());
    }
}
