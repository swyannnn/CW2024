package com.example.demo.memento;

public class GameSettingsMemento {
    private final boolean isAudioEnabled;

    public GameSettingsMemento(boolean isAudioEnabled) {
        this.isAudioEnabled = isAudioEnabled;
    }

    public boolean isAudioEnabled() {
        return isAudioEnabled;
    }
}
