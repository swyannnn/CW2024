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
        public static final double COLLISION_SHRINK_PERCENTAGE = 0.5;
    }

    // Main Menu Settings
    public static class MainMenu {
        public static final String BACKGROUND_MUSIC = "menubgm.mp3";
    }

    // pause button settings
    public static class PauseButton {
        public static final String IMAGE_NAME = "setting.png";
        public static final int IMAGE_WIDTH = 45;
        public static final int IMAGE_HEIGHT = 45;
        public static final int X_POSITION = 1235;
        public static final int Y_POSITION = 690;
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
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
    }

    // EnemyPlane1 Settings
    public static class EnemyPlane1 {
        public static final String IMAGE_NAME = "enemy0.png";
        public static final int IMAGE_HEIGHT = 50;
        public static final int INITIAL_HEALTH = 1;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
    }

    // EnemyPlane2 Settings
    public static class EnemyPlane2 {
        public static final String IMAGE_NAME = "enemy1.png";
        public static final int IMAGE_HEIGHT = 60;
        public static final int INITIAL_HEALTH = 2;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
    }

    // EnemyPlane3 Settings
    public static class EnemyPlane3 {
        public static final String IMAGE_NAME = "enemy2.png";
        public static final int IMAGE_HEIGHT = 70;
        public static final int INITIAL_HEALTH = 3;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
    }
    
    // EnemyPlane4 Settings
    public static class EnemyPlane4 {
        public static final String IMAGE_NAME = "enemy3.png";
        public static final int IMAGE_HEIGHT = 70;
        public static final int INITIAL_HEALTH = 1;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = 10;
        public static final double Y_LOWER_BOUND = 700;
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
        public static final int INITIAL_HEALTH = 0;
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

    public static class MultiPhaseBossPlane {
        public static final String IMAGE_NAME = "enemy3.png";
        public static final int MINI_SIZE = 4;
        public static final int GIANT_SIZE = 1;
        public static final int IMAGE_HEIGHT = 200;
        public static final int REMAINING_HEALTH_PHASE1 = 60;
        public static final int REMAINING_HEALTH_PHASE2 = 59;
        public static final int REMAINING_HEALTH_PHASE3 = 58;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000; // 1 second
        public static final double FIRE_RATE = 0.1; // 50% chance to fire
        public static final double Y_POSITION = (GameSettings.SCREEN_HEIGHT / 2) - 100;
        public static final double X_POSITION = 400;
        public static final double HORIZONTAL_VELOCITY_PHASE1 = 3.0;
        public static final double HORIZONTAL_VELOCITY_PHASE2 = 4.0;
        public static final double HORIZONTAL_VELOCITY_PHASE3 = 5.0;
        public static final double Y_UPPER_BOUND = 0;
        public static final double Y_LOWER_BOUND = 600;
        public static final double X_UPPER_BOUND = GameSettings.SCREEN_HEIGHT / 2;
        public static final double X_LOWER_BOUND = 1150;
        public static final long AREA_ATTACK_COOLDOWN_PHASE1 = 0;
        public static final long AREA_ATTACK_COOLDOWN_PHASE2 = 5_000_000_000L; // 5 seconds
        public static final long AREA_ATTACK_COOLDOWN_PHASE3 = 4_000_000_000L; // 4 seconds
        public static final long SUMMON_COOLDOWN = 1_000_000_000L; //1_000_000_000L
        public static final int PROJECTILE_X_POSITION_OFFSET = -130;
        public static final int PROJECTILE_Y_POSITION_OFFSET = 75;
    }


    // BossProjectile Settings
    public static class BossProjectile {
        public static final String IMAGE_NAME = "fireball.png";
        public static final int IMAGE_HEIGHT = 70;
        public static final double HORIZONTAL_VELOCITY = -7.5;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000L;
        public static final double PROJECTILE_X_POSITION_OFFSET = -100;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 120;
        public static final double FIRE_RATE = 0;
    }

    // BossShield Settings
    public static class BossShield {
        public static final String IMAGE_NAME = "shield.png";
        public static final int IMAGE_HEIGHT = 200;
        public static final int X_POSITION_OFFSET = 930;
        public static final int Y_POSITION_OFFSET = 200;
        public static final double BOSS_SHIELD_PROBABILITY = 0;
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
        public static final int IMAGE_HEIGHT = 25;
    }
    
    // Level 001 Settings
    public static class Level001 {
        public static final String BACKGROUND_IMAGE_NAME = "background001.jpg";
        public static final int TOTAL_ENEMIES = 3;
        public static final int KILLS_TO_ADVANCE = 300;
        public static final double ENEMY_SPAWN_PROBABILITY = 1;
        public static final int PLAYER_INITIAL_HEALTH = 5;
        public static final String BACKGROUND_MUSIC = "bgm001.wav";
    }

    // Level 002 Settings
    public static class Level002 {
        public static final String BACKGROUND_IMAGE_NAME = "background002.jpeg";
        public static final int PLAYER_INITIAL_HEALTH = 5;
        public static final String BACKGROUND_MUSIC = "bgm002.mp3";
    }

    // Level 003 Settings
    public static class Level003 {
        public static final String BACKGROUND_IMAGE_NAME = "background001.jpg";
        public static final int PLAYER_INITIAL_HEALTH = 5;
        public static final String BACKGROUND_MUSIC = "bgm003.mp3";
        public static final int SURVIVAL_TIME = 0; // in seconds
        public static final int ENEMY_SPAWN_INTERVAL = 1000;
    }

    // Level 004Settings
    public static class Level004 {
        public static final String BACKGROUND_IMAGE_NAME = "background002.jpeg";
        public static final String BACKGROUND_MUSIC = "bgm004.mp3";
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
            "menubgm.mp3",
            "bgm001.wav",
            "bgm002.mp3",
            "bgm003.mp3"
            // Add more background music filenames here as needed
        };

        // Images
        public static final String[] IMAGES = {
            "background001.jpg",
            "background002.jpeg",
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
