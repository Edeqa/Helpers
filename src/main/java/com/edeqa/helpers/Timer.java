package com.edeqa.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Timer {
    private List<Long> ticks;
    private long intermediate;
    private long start;

    public Timer() {
        ticks = new ArrayList<>();
    }

    public Timer start() {
        start = Calendar.getInstance().getTimeInMillis();
        intermediate = Calendar.getInstance().getTimeInMillis();
        ticks.add(intermediate);
        return this;
    }

    public Long tick() {
        long now = Calendar.getInstance().getTimeInMillis();
        ticks.add(now);
        long delta = now - intermediate;
        intermediate = now;
        return delta;
    }

    public Long total() {
        long now = Calendar.getInstance().getTimeInMillis();
        return now - start;
    }

    public Long stop() {
        long now = Calendar.getInstance().getTimeInMillis();
        ticks.add(now);
        long delta = now - ticks.get(0);
        return delta;
    }

    public Timer startLog() {
        start();
        System.out.println("Timer started at: " + new Date(intermediate).toString());
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
        System.out.println("Timer stopped at: " + new Date(intermediate).toString() + " with " + (ticks.size() - 2) + " tick(s) and total time: " + (ticks.get(ticks.size() - 1) - ticks.get(0)) + " ms");
    }
}
