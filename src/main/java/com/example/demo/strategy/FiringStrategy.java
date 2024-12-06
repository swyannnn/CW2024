package com.example.demo.strategy;

import com.example.demo.actor.plane.FighterPlane;

public interface FiringStrategy {
    void fire(FighterPlane plane, long now);
}
