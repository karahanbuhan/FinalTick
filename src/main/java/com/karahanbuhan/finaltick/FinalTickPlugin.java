package com.karahanbuhan.finaltick;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class FinalTickPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Reading configuration to create countdown task");
        final long target;
        final Map<Integer, String[]> triggers;
        final Map<Integer, BossBarProperties> bossBarProperties;
        try {
            final Configuration config = new Configuration(this);
            target = config.getTarget();
            triggers = config.getTriggers();
            bossBarProperties = config.getBossBarProperties();
        } catch (Exception e) {
            getLogger().warning("Could not load config: " + e);
            setEnabled(false);
            return;
        }

        if (triggers == null || triggers.isEmpty()) {
            getLogger().severe("No triggers found in config");
            setEnabled(false);
            return;
        }

        final Countdown countdown = new Countdown(target);
        if (countdown.isTimeUp()) {
            getLogger().severe("Countdown already completed");
            setEnabled(false);
            return;
        }

        getLogger().info("Starting countdown task, remaining time is %d seconds.".formatted(countdown.getTimeRemainingMillis() * 1000));

        final BossBarHandler bossBarHandler = new BossBarHandler();
        final CountdownTask countdownTask = new CountdownTask(countdown, triggers, bossBarHandler, bossBarProperties);
        countdownTask.runTaskTimer(this, 0l, 20l);

        Bukkit.getPluginManager().registerEvents(bossBarHandler, this);

        getLogger().info("Plugin enabled");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
        super.onDisable();
    }
}
