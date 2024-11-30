package com.example.demo.util;

public class Phase {
    private final int startTimeInSeconds;
    private final double intervalReductionFactor;

    public Phase(int startTimeInSeconds, double intervalReductionFactor) {
        this.startTimeInSeconds = startTimeInSeconds;
        this.intervalReductionFactor = intervalReductionFactor;
    }

    public int getStartTimeInSeconds() {
        return startTimeInSeconds;
    }

    public double getIntervalReductionFactor() {
        return intervalReductionFactor;
    }
}
