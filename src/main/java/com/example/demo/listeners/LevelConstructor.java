package com.example.demo.listeners;

import com.example.demo.level.LevelConfiguration;
import com.example.demo.level.LevelParent;

public interface LevelConstructor {
    LevelParent create(LevelConfiguration config);
}