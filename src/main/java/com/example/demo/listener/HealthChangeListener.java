package com.example.demo.listener;

import com.example.demo.actors.planes.UserPlane;

public interface HealthChangeListener {
    void onHealthChange(UserPlane player, int newHealth);
}
