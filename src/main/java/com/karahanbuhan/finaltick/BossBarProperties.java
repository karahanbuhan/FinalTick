package com.karahanbuhan.finaltick;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public class BossBarProperties {
    private String title;
    private double progress;
    private BarColor color;
    private BarStyle style;

    // Constructor
    public BossBarProperties(String title, double progress, BarColor color, BarStyle style) {
        this.title = title;
        this.progress = progress;
        this.color = color;
        this.style = style;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public double getProgress() {
        return progress;
    }

    public BarColor getColor() {
        return color;
    }

    public BarStyle getStyle() {
        return style;
    }
}

