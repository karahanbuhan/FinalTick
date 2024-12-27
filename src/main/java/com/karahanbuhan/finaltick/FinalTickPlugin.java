package com.karahanbuhan.finaltick;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class FinalTickPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Reading configuration to create countdown task");
        final long target;
        final Map<Integer, String[]> triggers;
        try {
            final Configuration config = new Configuration(this);
            target = config.getTarget();
            triggers = config.getTriggers();
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
        final CountdownTask countdownTask = new CountdownTask(countdown, triggers);
        countdownTask.runTaskTimer(this, 0l, 20l);

        getLogger().info("Plugin enabled");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
        super.onDisable();
    }
}
