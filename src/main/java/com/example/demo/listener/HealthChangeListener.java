package com.example.demo.listener;

import com.example.demo.UserPlane;

public interface HealthChangeListener {
    void onHealthChange(UserPlane player, int newHealth);
}
