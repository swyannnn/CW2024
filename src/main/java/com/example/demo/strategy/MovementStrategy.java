package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;

public interface MovementStrategy {
    void move(FighterPlane plane, long now);
}
