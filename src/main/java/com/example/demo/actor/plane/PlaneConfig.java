package com.example.demo.actor.plane;

import com.example.demo.strategy.firing.FiringStrategy;
import com.example.demo.strategy.movement.MovementStrategy;

/**
 * The PlaneConfig class represents the configuration settings for a plane in the game.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/actor/plane/PlaneConfig.java">Github Source Code</a>
 */
public class PlaneConfig {
    public String imageName;
    public int imageHeight;
    public double initialXPos;
    public double initialYPos;
    public int health;
    public double fireRate;
    public long fireIntervalNanoseconds;
    public double projectileOffsetX;
    public double projectileOffsetY;
    public int speed;
    public MovementStrategy movementStrategy;
    public FiringStrategy firingStrategy;
}
