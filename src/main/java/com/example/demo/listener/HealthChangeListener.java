package com.example.demo.listener;

import com.example.demo.actor.plane.UserPlane;

public interface HealthChangeListener {
    void onHealthChange(UserPlane player, int newHealth);
}
