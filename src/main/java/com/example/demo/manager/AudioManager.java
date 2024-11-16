package com.example.demo.manager;

import com.example.demo.util.GameConstant;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * AudioManager handles the loading and playing of sound effects and background music.
 * It also preloads audio resources to minimize latency during gameplay.
 */
public class AudioManager {
    private static final String AUDIO_LOCATION = "/com/example/demo/audios/";
    private boolean audioEnabled = true;
    private static AudioManager instance;
    private MediaPlayer mediaPlayer;
    private final List<AudioClip> soundEffects;
    private final List<Media> preloadedMedia;

    // Private constructor to enforce Singleton pattern
    private AudioManager() {
        soundEffects = new ArrayList<>();
        preloadedMedia = new ArrayList<>();
        preloadAllAudio();
    }

    /**
     * Retrieves the singleton instance of AudioManager.
     *
     * @return The singleton instance of AudioManager.
     */
    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }
    
    public void setAudioEnabled(boolean enabled) {
        this.audioEnabled = enabled;
        // Implement enabling/disabling audio playback accordingly
        if (!enabled) {
            stopMusic();
        }
    }

    /**
     * Preloads all sound effects and background music specified in GameConstant.
     */
    private void preloadAllAudio() {
        // Preload sound effects
        for (String filename : GameConstant.SOUND_EFFECT_FILES) {
            AudioClip clip = preloadAudioClip(filename);
            if (clip != null) {
                soundEffects.add(clip);
            }
        }

        // Adjust volume for specific sound effects if needed
        // Example: Set volume for the fourth sound effect
        if (soundEffects.size() > 3 && soundEffects.get(3) != null) {
            soundEffects.get(3).setVolume(0.20);
        }

        // Preload background music
        for (String filename : GameConstant.BACKGROUND_MUSIC_FILES) {
            preloadMedia(filename);
        }
    }

    /**
     * Loads and returns an AudioClip from the specified filename.
     *
     * @param filename The name of the audio file.
     * @return The AudioClip, or null if not found.
     */
    private AudioClip preloadAudioClip(String filename) {
        try {
            String uri = getClass().getResource(AUDIO_LOCATION + filename).toExternalForm();
            return new AudioClip(uri);
        } catch (NullPointerException e) {
            System.err.println("Audio file not found: " + filename);
            return null;
        }
    }

    /**
     * Preloads a Media object for background music or other media files.
     *
     * @param filename The name of the media file.
     */
    private void preloadMedia(String filename) {
        try {
            String uri = getClass().getResource(AUDIO_LOCATION + filename).toExternalForm();
            Media media = new Media(uri);
            preloadedMedia.add(media);
        } catch (NullPointerException e) {
            System.err.println("Media file not found: " + filename);
        }
    }

    /**
     * Plays background music from the specified file.
     *
     * @param filename The name of the music file.
     */
    public void playMusic(String filename) {
        stopMusic();
        Media media = loadMedia(filename);
        if (media != null) {
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        }
    }

    /**
     * Stops the current background music and releases resources.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Loads and returns a Media object from the specified filename.
     *
     * @param filename The name of the media file.
     * @return The Media object, or null if not found.
     */
    private Media loadMedia(String filename) {
        try {
            String uri = getClass().getResource(AUDIO_LOCATION + filename).toExternalForm();
            return new Media(uri);
        } catch (NullPointerException e) {
            System.err.println("Media file not found: " + filename);
            return null;
        }
    }

    /**
     * Plays a sound effect from the preloaded sound effects.
     *
     * @param index The index of the sound effect to play.
     */
    public void playSoundEffect(int index) {
        if (index >= 0 && index < soundEffects.size() && soundEffects.get(index) != null) {
            soundEffects.get(index).play();
        } else {
            System.err.println("Invalid sound effect index: " + index);
        }
    }
}
