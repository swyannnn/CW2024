package com.example.demo.util;

import com.example.demo.strategy.FiringStrategy;
import com.example.demo.strategy.MovementStrategy;

/**
 * The PlaneConfig class represents the configuration settings for a plane in the game.
 */
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
}
