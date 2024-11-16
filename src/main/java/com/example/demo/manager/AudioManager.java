package com.example.demo.manager;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private static final String AUDIO_LOCATION = "/com/example/demo/audios/";
    private MediaPlayer mediaPlayer;
    private AudioClip[] sfx;

    /**
     * Initializes sound effects and background music.
     */
    public AudioManager() {
        initializeSoundEffects();
    }

    /**
     * Initializes sound effects.
     */
    private void initializeSoundEffects() {
        sfx = new AudioClip[]{
            createAudioClip("player_death.wav"),
            createAudioClip("enemy_destroy.wav"),
            createAudioClip("titlescreen_transition.wav"),
            createAudioClip("player_shoot.mp3")
        };

        if (sfx[3] != null) {
            sfx[3].setVolume(0.20);
        }
    }

    /**
     * Creates an AudioClip from a resource file.
     *
     * @param filename The name of the audio file.
     * @return An instance of AudioClip or null if not found.
     */
    private AudioClip createAudioClip(String filename) {
        String resourcePath = AUDIO_LOCATION + filename;
        try {
            String uri = getClass().getResource(resourcePath).toExternalForm();
            return new AudioClip(uri);
        } catch (NullPointerException e) {
            System.err.println("Audio file not found: " + resourcePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a MediaPlayer instance from a resource file and plays it.
     *
     * @param filename The name of the media file.
     */
    public void playMusic(String filename) {
        stopMusic(); // Stop any existing music
        Media media = createMedia(filename);
        if (media != null) {
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            mediaPlayer.setVolume(0.5); // Set volume to 50%
            mediaPlayer.play();
        }
    }

    /**
     * Stops and disposes of the current background music.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Creates a Media instance from a resource file.
     *
     * @param filename The name of the media file.
     * @return An instance of Media or null if not found.
     */
    private Media createMedia(String filename) {
        String resourcePath = AUDIO_LOCATION + filename;
        try {
            String uri = getClass().getResource(resourcePath).toExternalForm();
            return new Media(uri);
        } catch (NullPointerException e) {
            System.err.println("Media file not found: " + resourcePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Plays a specific sound effect.
     *
     * @param index The index of the sound effect in the sfx array.
     */
    public void playSoundEffect(int index) {
        if (index >= 0 && index < sfx.length && sfx[index] != null) {
            sfx[index].play();
        }
    }
}
