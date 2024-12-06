package com.example.demo.listeners;

public interface StateTransitioner {
    void setNumberOfPlayers(int numberOfPlayers);
    int getNumberOfPlayers();
    void goToLevel(int levelNumber);
    void goToMainMenu();
    void goToWinState();
    void goToLoseState();
}
