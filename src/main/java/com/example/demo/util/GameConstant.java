package com.example.demo.util;

/**
 * Contains all game-related constants organized into nested static classes for better structure.
 */
public class GameConstant {

    // General Game Settings
    public static class GameSettings {
        public static final String TITLE = "Sky Battle";
        public static final int SCREEN_WIDTH = 1300;
        public static final int SCREEN_HEIGHT = 750;
    }

    // UserPlane Settings
    public static class UserPlane {
        public static final String IMAGE_NAME = "userplane.png";
        public static final int IMAGE_HEIGHT = 150;
        public static final int INITIAL_HEALTH = 5;
        public static final double HORIZONTAL_VELOCITY = 8.0;
        public static final double VERTICAL_VELOCITY = 8.0;
        public static final double INITIAL_X_POSITION = 5.0;
        public static final double INITIAL_Y_POSITION = 300.0;
        public static final double FIRE_RATE = 1.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 250;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
        public static final int VERTICAL_VELOCITY_MULTIPLIER = 0;
        public static final int HORIZONTAL_VELOCITY_MULTIPLIER = 0;
        public static final int NUMBER_OF_KILLS = 0;
    }

    // UserProjectile Settings
    public static class UserProjectile {
        public static final String IMAGE_NAME = "userfire.png";
        public static final int IMAGE_HEIGHT = 125;
        public static final int HORIZONTAL_VELOCITY = 15;
        public static final int PROJECTILE_X_POSITION_OFFSET = 90;
        public static final int PROJECTILE_Y_POSITION_OFFSET = 20;
        public static final long FIRE_INTERVAL_NANOSECONDS = 500_000_000L;
    }

    // EnemyPlane Settings
    public static class EnemyPlane {
        public static final String IMAGE_NAME = "enemyplane.png";
        public static final int IMAGE_HEIGHT = 150;
        public static final int INITIAL_HEALTH = 1;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
    }

    // EnemyProjectile Settings
    public static class EnemyProjectile {
        public static final String IMAGE_NAME = "enemyFire.png";
        public static final int IMAGE_HEIGHT = 50;
        public static final int HORIZONTAL_VELOCITY = -5;
        public static final long FIRE_INTERVAL_NANOSECONDS = 100_000_000L;
        public static final double PROJECTILE_X_POSITION_OFFSET = -100;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 50;
        public static final double FIRE_RATE = 0.1;
    }

    // BossPlane Settings
    public static class BossPlane {
        public static final String IMAGE_NAME = "bossplane.png";
        public static final int IMAGE_HEIGHT = 300;
        public static final int INITIAL_HEALTH = 1;
        public static final double HORIZONTAL_VELOCITY = 4.0;
        public static final int VERTICAL_VELOCITY = 4;
        public static final double INITIAL_X_POSITION = 1000.0;
        public static final double INITIAL_Y_POSITION = 125.0;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 120.0;
        public static final int MOVE_FREQUENCY_PER_CYCLE = 5;
        public static final int ZERO = 0;
        public static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
        public static final int Y_POSITION_UPPER_BOUND = -40;
        public static final int Y_POSITION_LOWER_BOUND = 600;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000L;
    }

    // BossProjectile Settings
    public static class BossProjectile {
        public static final String IMAGE_NAME = "fireball.png";
        public static final int IMAGE_HEIGHT = 100;
        public static final int HORIZONTAL_VELOCITY = -5;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000L;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 120;
        public static final double FIRE_RATE = 1;
    }

    // BossShield Settings
    public static class BossShield {
        public static final String IMAGE_NAME = "shield.png";
        public static final int IMAGE_HEIGHT = 200;
        public static final int X_POSITION_OFFSET = 930;
        public static final int Y_POSITION_OFFSET = 200;
        public static final double BOSS_SHIELD_PROBABILITY = 1;
        public static final int MAX_FRAMES_WITH_SHIELD = 500;
        public static final int MAX_FRAMES_WITHOUT_SHIELD = 500;
    }

    // All projectile types
    public static class Projectile {
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH;
    }

    // Heart 
    public static class Heart {
        public static final String IMAGE_NAME = "heart.png";
        public static final int IMAGE_HEIGHT = 50;
    }
    
    // Level 001 Settings
    public static class Level001 {
        public static final String BACKGROUND_IMAGE_NAME = "background1.jpg";
        public static final int TOTAL_ENEMIES = 1;
        public static final int KILLS_TO_ADVANCE = 10;
        public static final double ENEMY_SPAWN_PROBABILITY = 1;
        public static final int PLAYER_INITIAL_HEALTH = 5;
    }

    // Level 002 Settings
    public static class Level002 {
        public static final String BACKGROUND_IMAGE_NAME = "background2.jpg";
        public static final int PLAYER_INITIAL_HEALTH = 5;
    }

    // File Paths
    public static class FilePaths {
        public static final String AUDIO_LOCATION = "/com/example/demo/audios/";
        public static final String IMAGE_LOCATION = "/com/example/demo/images/";

        // Sound Effects
        public static final String[] SOUND_EFFECTS = {
            "player_death.wav",
            "enemy_destroy.wav",
            "titlescreen_transition.wav",
            "player_shoot.mp3"
            // Add more sound effect filenames here as needed
        };

        // Background Music
        public static final String[] BACKGROUND_MUSIC = {
            "menubgm.mp3"
            // Add more background music filenames here as needed
        };

        // Images
        public static final String[] IMAGES = {
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
            "youwin.png",
            "setting.png"
            // Add more image filenames here as needed
        };
    }
}
