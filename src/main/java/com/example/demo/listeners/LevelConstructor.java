package com.example.demo.listeners;

import com.example.demo.level.LevelConfig;
import com.example.demo.level.LevelParent;

public interface LevelConstructor {
    LevelParent create(LevelConfig config);
}