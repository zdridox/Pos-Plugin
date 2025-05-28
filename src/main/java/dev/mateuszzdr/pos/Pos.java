package dev.mateuszzdr.pos;

import dev.mateuszzdr.pos.Commands.PosCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.ConfigurationSection;


import java.util.HashMap;

public final class Pos extends JavaPlugin implements Listener {

    private static Pos instance;

    public static Pos GetInstance() {
        return  instance;
    }

    public static HashMap<String, Location> PlayerCords = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        getCommand("Pos").setExecutor(new PosCommand());
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage("Pos by zdridox");
        PlayerCords = loadHashMap();

        // get player cords every minute
        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                getCords();
            }
        }, 0L, 1200L);

        // save cords to file
        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {saveHashMap(PlayerCords);}
        }, 0L, 6000L);
    }

    public void getCords() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            PlayerCords.put(p.getName(), p.getLocation());
        }
    }

    @Override
    public void onDisable() {
        saveHashMap(PlayerCords);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getCords();
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        getCords();
    }

    public void saveHashMap(HashMap<String, Location> map) {
        for (HashMap.Entry<String, Location> entry : map.entrySet()) {
            getConfig().set("PlayersCords." + entry.getKey(), entry.getValue());
        }
        saveConfig();
    }

    public HashMap<String, Location>loadHashMap() {
        HashMap<String, Location> map = new HashMap<>();
        ConfigurationSection section = getConfig().getConfigurationSection("PlayersCords");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Location value = section.getLocation(key);
                map.put(key, value);
            }
        }
        Bukkit.getConsoleSender().sendMessage("[Pos] PlayerCords Loaded");
        return map;
    }


}
