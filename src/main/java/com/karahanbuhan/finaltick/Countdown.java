package com.karahanbuhan.finaltick;

import java.time.Duration;
import java.util.Date;

public class Countdown {
    public final long target;

    public int countdown;

    public Countdown(final long target) {
        this.target = target;
        this.countdown = Math.round(getTimeRemainingMillis() / 1000);
    }

    public Countdown(final Date target) {
        this.target = target.getTime();
    }

    public int count() {
        return --countdown;
    }

    public long getTimeRemainingMillis() {
        return target - System.currentTimeMillis();
    }

    public Duration getRemainingDuration() {
        return Duration.ofMillis(getTimeRemainingMillis());
    }

    public boolean isTimeUp() {
        return System.currentTimeMillis() >= target;
    }
}
