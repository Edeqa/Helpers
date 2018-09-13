package com.edeqa.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Timer {
    private long start;
    private List<Long> ticks;

    public Timer() {
        ticks = new ArrayList<>();
    }

    public Timer start() {
        start = Calendar.getInstance().getTimeInMillis();
        ticks.add(start);
        return this;
    }

    public Long tick() {
        long now = Calendar.getInstance().getTimeInMillis();
        ticks.add(now);
        long delta = now - start;
        start = now;
        return delta;
    }

    public Long stop() {
        long now = Calendar.getInstance().getTimeInMillis();
        ticks.add(now);
        long delta = now - ticks.get(0);
        return delta;
    }

    public Timer startLog() {
        start();
        System.out.println("Timer started at: " + new Date(start).toString());
        return this;
    }

    public void tickLog() {
        System.out.println("Timer [" + ticks.size() + "]: " + tick() + " ms");
    }

    public void tickLog(String mark) {
        System.out.println("Timer [" + ticks.size() + ":" + mark + "]: " + tick() + " ms");
    }

    public void stopLog() {
        stop();
        System.out.println("Timer stopped at: " + new Date(start).toString() + " with " + (ticks.size() - 2) + " tick(s) and total time: " + (ticks.get(ticks.size() - 1) - ticks.get(0)) + " ms");
    }
}
