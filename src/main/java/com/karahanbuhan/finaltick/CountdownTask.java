package com.karahanbuhan.finaltick;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CountdownTask extends BukkitRunnable {
    private final Countdown countdown;
    private final LinkedHashMap<Integer, String[]> triggers;

    private final BossBarHandler bossBarHandler;
    private final Map<Integer, BossBar> bossbars;

    public CountdownTask(Countdown countdown, Map<Integer, String[]> triggers, BossBarHandler bossBarHandler, Map<Integer, BossBar> bossbars) {
        this.countdown = countdown;
        // Sorting to run triggers by order if timing problems occur
        this.triggers = triggers.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        this.bossBarHandler = bossBarHandler;
        this.bossbars = bossbars;
    }

    private int lastNegative = 0;

    @Override
    public void run() {
        final AtomicReference<Optional<Integer>> completedTriggerA = new AtomicReference<>(Optional.empty());
        final int count = countdown.count();
        triggers.forEach((lastXSeconds, commands) -> {
            if (count <= Math.abs(lastXSeconds)) {
                executeCommands(Arrays.stream(commands).map(s -> s.replace("%countdown%", String.valueOf(count))).map(s -> ChatColor.translateAlternateColorCodes('&', s)).toArray(String[]::new));
                completedTriggerA.set(Optional.of(lastXSeconds));
                return; // This is to break the forEach, one trigger per run at maximum, DO NOT REMOVE!
            }
        });

        //Bossbar
        bossbars.forEach((i, b) -> {
            if (count == i){
                bossBarHandler.updateActiveBossbar(b);
            }
        });

        /* Remove from commands to prevent recalling each method over and over as <= triggerThreshold is used.

           We could use == triggerThreshold for avoiding this but that could cause equality problems with
           the milliseconds targets as the configuration uses seconds.

           Use negative numbers such as -1 for not removing, still comparing with abs the current time.
         */

        int completedTrigger = completedTriggerA.get().orElse(0);
        if (completedTrigger >= 0) triggers.remove(completedTrigger);
        else if (completedTrigger != lastNegative && lastNegative != 0) triggers.remove(lastNegative);
        if (completedTrigger < 0) lastNegative = completedTrigger;

        if (count <= 0) {
            if (triggers.containsValue(0)) executeCommands(triggers.get(0));
            cancel();
        }
    }

    private void executeCommands(String[] commands) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        Arrays.stream(commands).forEach(cmd -> Bukkit.dispatchCommand(console, cmd));
    }
}
