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
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/manager/ActorManager.java">Github Source Code</a>
 */
public class AudioManager {
    private static final String AUDIO_LOCATION = GameConstant.FilePaths.AUDIO_LOCATION;
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
     * Preloads all audio files used in the game.
     * 
     * This method iterates through all sound effects and background music files defined in 
     * the GameConstant.FilePaths enumeration, preloading each audio file and setting the 
     * volume for specific sound effects.
     * 
     * Preloaded sound effects are added to the soundEffects list.
     */
    private void preloadAllAudio() {
        for (GameConstant.FilePaths.SoundEffect soundEffect : GameConstant.FilePaths.SoundEffect.values()) {
            String audioName = soundEffect.getFileName();
            AudioClip clip = preloadAudioClip(audioName);
            switch (audioName) {
                case "player_shoot.mp3":
                    clip.setVolume(0.1);
                    break;
                case "enemy_destroy.wav":
                    clip.setVolume(0.35);
                    break;
                default:
                    clip.setVolume(0.5); 
                    break;
            }
            soundEffects.add(clip);
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
