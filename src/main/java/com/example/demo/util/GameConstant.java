package com.example.demo.util;

public class GameConstant {
    // Screen settings
    public static final String TITLE = "Sky Battle";
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    public static final double SCREEN_HEIGHT_ADJUSTMENT = 200;
    public static double ENEMYPLANE_HORIZONTAL_VELOCITY = -3;
    public static double USERPLANE_HORIZONTAL_VELOCITY = 8;
    public static double USERPLANE_VERTICAL_VELOCITY = 8;
    public static long USERPLANE_FIRE_INTERVAL_NANOSECONDS = 500_000_000;
    public static long ENEMYPLANE_FIRE_INTERVAL_NANOSECONDS = 100_000_000;
    public static final int MILLISECOND_DELAY = 50;
    public static double X_UPPER_BOUND = 0;
    public static double ENEMY_MAXIMUM_Y_POSITION = 250;
    public static double ENEMY_MINIMUM_Y_POSITION = -40;
    public static double USERPLANE_FIRE_RATE = 1.0;

    // Audio file paths
    public static final String[] SOUND_EFFECT_FILES = {
        "player_death.wav",
        "enemy_destroy.wav",
        "titlescreen_transition.wav",
        "player_shoot.mp3"
        // Add more sound effect filenames here as needed
    };

    public static final String[] BACKGROUND_MUSIC_FILES = {
        "menubgm.mp3"
        // Add more background music filenames here as needed
    };

    // Image file paths
    public static final String[] IMAGE_FILES = {
        "background1.jpg",
        "background2.jpg",
        "bossplane.png",
        "enemyFire.png",
        "enemyplane.png",
        "fireball.png",
        "gameover.png",
        "heart.png",
        "shield.png",
        "userfire.png",
        "userplane.png",
        "youwin.png"
        // Add more image filenames here as needed
    };
}
