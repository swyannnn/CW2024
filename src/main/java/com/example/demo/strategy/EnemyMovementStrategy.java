package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.util.GameConstant;


public class EnemyMovementStrategy implements MovementStrategy {
    private final double speed;

    public EnemyMovementStrategy() {
        this.speed = GameConstant.EnemyPlane.HORIZONTAL_VELOCITY;
    }

    @Override
    public void move(FighterPlane plane, long now) {
        double newX = plane.getLayoutX() - speed;

        // Destroy the plane if it goes out of bounds
        if (newX + plane.getBoundsInParent().getWidth() < 0) {
            plane.destroy();
            return;
        }

        plane.setLayoutX(newX);
    }
}

