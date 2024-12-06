package com.example.demo.listeners;

public interface PauseHandler {
    void pauseGame();
    void resumeGame();
    boolean isPaused();
}