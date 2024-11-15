package com.example.demo.memento;

public class GameStateMemento {
    private final int playerHealth;
    private final int score;
    private final int levelNumber;

    public GameStateMemento(int playerHealth, int score, int levelNumber) {
        this.playerHealth = playerHealth;
        this.score = score;
        this.levelNumber = levelNumber;
    }

    // Getters
    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getScore() {
        return score;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
