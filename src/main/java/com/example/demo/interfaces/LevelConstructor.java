package com.example.demo.interfaces;

import com.example.demo.level.LevelConfig;
import com.example.demo.level.LevelParent;

public interface LevelConstructor {
    LevelParent create(LevelConfig config);
}