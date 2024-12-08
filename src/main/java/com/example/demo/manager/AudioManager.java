package com.example.demo.manager;

import com.example.demo.util.GameConstant;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;


/**
 * The AudioManager class is responsible for managing audio playback in the application.
 * It follows the Singleton design pattern to ensure only one instance of the manager exists.
 * The class handles preloading of audio files, playing background music, and sound effects.
 * 
 * @see GameConstant
 */
public class AudioManager {
    private static final String AUDIO_LOCATION = GameConstant.FilePaths.AUDIO_LOCATION;
    private boolean audioEnabled = true;
    private static AudioManager instance;
    private MediaPlayer mediaPlayer;
    private final List<AudioClip> soundEffects;
    private final List<Media> preloadedMedia;

    /**
     * Private constructor for the AudioManager class.
     * Initializes the soundEffects and preloadedMedia lists,
     * and preloads all audio files.
     */
    private AudioManager() {
        soundEffects = new ArrayList<>();
        preloadedMedia = new ArrayList<>();
        preloadAllAudio();
    }

    /**
     * Returns the singleton instance of the AudioManager.
     * This method is synchronized to ensure thread safety.
     * If the instance is null, it initializes a new AudioManager instance.
     *
     * @return the singleton instance of AudioManager
     */
    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Checks if the audio is enabled.
     *
     * @return true if audio is enabled, false otherwise.
     */
    public boolean isAudioEnabled() {
        return audioEnabled;
    }
    
    /**
     * Enables or disables the audio playback.
     * 
     * @param enabled a boolean value where true enables audio playback and false disables it.
     *                If set to false, any currently playing music will be stopped.
     */
    public void setAudioEnabled(boolean enabled) {
        this.audioEnabled = enabled;
        // Implement enabling/disabling audio playback accordingly
        if (!enabled) {
            stopMusic();
        }
    }


    /**
     * Preloads all audio assets used in the game.
     * <p>
     * This method iterates through all sound effects and background music defined
     * in the {@code GameConstant.FilePaths} enum and preloads them. For sound effects,
     * it sets the volume to 0.5 and adds them to the {@code soundEffects} list.
     * Background music files are preloaded without additional configuration.
     * </p>
     */
    private void preloadAllAudio() {
        for (GameConstant.FilePaths.SoundEffect soundEffect : GameConstant.FilePaths.SoundEffect.values()) {
            AudioClip clip = preloadAudioClip(soundEffect.getFileName());
            if (clip != null) {
                clip.setVolume(0.5);
                soundEffects.add(clip);
            }
        }

        for (GameConstant.FilePaths.BackgroundMusic backgroundMusic : GameConstant.FilePaths.BackgroundMusic.values()) {            
            preloadMedia(backgroundMusic.getFileName());
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
            System.out.println("Playing music: " + filename);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        }
    }

    /**
     * Stops the currently playing music if any, disposes of the media player, 
     * and sets the media player reference to null.
     * 
     * This method checks if the media player is not null, stops the music, 
     * disposes of the media player resources, and then sets the media player 
     * reference to null to ensure it can be garbage collected.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Pauses the currently playing music if the media player is not null.
     */
    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Resumes the playback of the music if the media player is not null.
     */
    public void resumeMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
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
