package com.karahanbuhan.finaltick;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossBarHandler implements Listener {
    private BossBar activeBossbar;

    public void updateActiveBossbar(BossBarProperties bossBarProperties){
        String title = bossBarProperties.getTitle();
        double progress = bossBarProperties.getProgress();
        BarColor color = bossBarProperties.getColor();
        BarStyle style = bossBarProperties.getStyle();

        Bukkit.broadcastMessage(title);

        if (activeBossbar == null) {
            BossBar bossBar = Bukkit.createBossBar(title, color, style);
            bossBar.setProgress(progress);

            activeBossbar = bossBar;

            Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        } else {
            activeBossbar.setTitle(title);
            activeBossbar.setProgress(progress);
            activeBossbar.setColor(color);
            activeBossbar.setStyle(style);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (activeBossbar != null)
            activeBossbar.addPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (activeBossbar != null)
            activeBossbar.removePlayer(player);
    }
}
