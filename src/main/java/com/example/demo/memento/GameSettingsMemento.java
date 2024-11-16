package com.example.demo.memento;

import java.util.Map;

import com.example.demo.manager.AudioManager;
import com.example.demo.manager.ImageManager;

public class GameSettingsMemento {
    private final boolean isAudioEnabled;

    public GameSettingsMemento(boolean isAudioEnabled) {
        this.isAudioEnabled = isAudioEnabled;
    }

    public boolean isAudioEnabled() {
        return isAudioEnabled;
    }

    /**
     * Creates a GameSettingsMemento from current settings.
     *
     * @param audioManager The AudioManager instance.
     * @param imageManager The ImageManager instance.
     * @return A GameSettingsMemento with the current settings.
     */
    public static GameSettingsMemento createFromSettings(AudioManager audioManager, ImageManager imageManager) {
        boolean audioEnabled = audioManager.isAudioEnabled(); // Assuming AudioManager has this method

        return new GameSettingsMemento(audioEnabled);
    }

    /**
     * Applies the settings from this memento to the managers.
     *
     * @param audioManager The AudioManager instance.
     * @param imageManager The ImageManager instance.
     */
    public void applySettings(AudioManager audioManager, ImageManager imageManager) {
        audioManager.setAudioEnabled(this.isAudioEnabled()); // Assuming AudioManager has this method
    }
}
