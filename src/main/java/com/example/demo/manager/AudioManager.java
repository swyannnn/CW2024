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
    private MediaPlayer mediaPlayer;
    private final AudioClip[] soundEffects;
    private final List<Media> preloadedMedia;

    /**
     * Initializes sound effects and preloads media resources.
     */
    public AudioManager() {
        // Initialize sound effects using GameConstants
        soundEffects = new AudioClip[GameConstant.SOUND_EFFECT_FILES.length];
        for (int i = 0; i < GameConstant.SOUND_EFFECT_FILES.length; i++) {
            soundEffects[i] = loadAudioClip(GameConstant.SOUND_EFFECT_FILES[i]);
        }

        // Example: Adjust the volume for a specific sound effect
        if (soundEffects.length > 3 && soundEffects[3] != null) {
            soundEffects[3].setVolume(0.20);
        }

        // Preload background music or other media
        preloadedMedia = new ArrayList<>();
        preloadAllAudio();
    }

    /**
     * Preloads all media resources required for the game.
     */
    private void preloadAllAudio() {
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
    private AudioClip loadAudioClip(String filename) {
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
        if (index >= 0 && index < soundEffects.length && soundEffects[index] != null) {
            soundEffects[index].play();
        }
    }
}
