package com.example.demo.memento;

public class PlayerStateMemento {
    private final int health;
    private final int score;
    private final double positionX;
    private final double positionY;

    public PlayerStateMemento(int health, int score, double positionX, double positionY) {
        this.health = health;
        this.score = score;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
