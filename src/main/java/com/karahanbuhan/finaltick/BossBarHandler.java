package com.karahanbuhan.finaltick;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossBarHandler implements Listener {
    private BossBar activeBossbar;

    public void updateActiveBossbar(BossBar bossBar){

        if (activeBossbar != null)
            activeBossbar.removeAll();

        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);

        activeBossbar = bossBar;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        activeBossbar.addPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        activeBossbar.removePlayer(player);
    }
}
