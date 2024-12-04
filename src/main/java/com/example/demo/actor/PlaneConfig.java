package com.example.demo.actor;

import com.example.demo.strategy.FiringStrategy;
import com.example.demo.strategy.MovementStrategy;

public class PlaneConfig {
    public String imageName;
    public int imageHeight;
    public double initialXPos;
    public double initialYPos;
    public int health;
    public double fireRate;
    public long fireIntervalNanoseconds;
    public MovementStrategy movementStrategy;
    public FiringStrategy firingStrategy;
    // Add other properties as needed
}
