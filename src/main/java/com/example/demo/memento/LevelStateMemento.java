package com.example.demo.memento;

public class LevelStateMemento {
    private final int levelNumber;
    private final int activeEnemies;

    public LevelStateMemento(int levelNumber, int activeEnemies) {
        this.levelNumber = levelNumber;
        this.activeEnemies = activeEnemies;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getActiveEnemies() {
        return activeEnemies;
    }
}
