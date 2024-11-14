package com.example.demo.level;

// Memento class to store the state of a level or game
public class LevelMemento {
    private final int playerHealth;
    private final int score;
    private final int currentLevelNumber;

    public LevelMemento(int playerHealth, int score, int currentLevelNumber) {
        this.playerHealth = playerHealth;
        this.score = score;
        this.currentLevelNumber = currentLevelNumber;
    }

    // Getters to retrieve the state
    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }
}
