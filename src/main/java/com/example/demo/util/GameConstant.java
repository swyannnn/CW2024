package com.example.demo.util;


/**
 * The GameConstant class holds various constants used throughout the game.
 * These constants are organized into nested static classes based on their usage context.
 * 
 * @see <a href="https://github.com/swyannnn/CW2024/blob/master/src/main/java/com/example/demo/util/GameConstant.java">Github Source Code</a>
 */
public class GameConstant {

    /**
     * General game settings.
     */
    public static class GameSettings {
        public static final String TITLE = "Sky Battle";
        public static final int SCREEN_WIDTH = 1300;
        public static final int SCREEN_HEIGHT = 750;
        public static final double COLLISION_SHRINK_PERCENTAGE = 0.5;
    }

    /**
     * Main menu settings.
     */
    public static class MainMenu {
        public static final String BACKGROUND_MUSIC = "menubgm.mp3";
    }

    /**
     * Pause button settings.
     */
    public static class PauseButton {
        public static final String IMAGE_NAME = "setting.png";
        public static final int IMAGE_WIDTH = 45;
        public static final int IMAGE_HEIGHT = 45;
        public static final int X_POSITION = 1235;
        public static final int Y_POSITION = 690;
    }

    /**
     * Game over settings.
     */
    public static class GameOver {
        public static final String IMAGE_PATH = FilePaths.IMAGE_LOCATION + "gameover.png";
        public static final int IMAGE_WIDTH = 600;
        public static final int IMAGE_HEIGHT = 450;
    }

    /**
     * You win settings.
     */
    public static class WinGame {
        public static final String IMAGE_PATH = FilePaths.IMAGE_LOCATION + "youwin.png";
        public static final int IMAGE_WIDTH = 500;
        public static final int IMAGE_HEIGHT = 400;
    }

    /**
     * User plane settings.
     */
    public static class UserPlane {
        public static final String ID1_IMAGE_NAME = "userplane1.png";
        public static final String ID2_IMAGE_NAME = "userplane2.png";
        public static final String ID1_IMAGE_NAME_DISPLAY = "userplane1_icon.png";
        public static final String ID2_IMAGE_NAME_DISPLAY = "userplane2_icon.png";
        public static final int IMAGE_HEIGHT = 100;
        public static final int INITIAL_HEALTH = 5;
        public static final int VELOCITY = 8;
        public static final double INITIAL_X_POSITION = 5.0;
        public static final double ID1_INITIAL_Y_POSITION = 200.0;
        public static final double ID2_INITIAL_Y_POSITION = 400.0;
        public static final double FIRE_RATE = 1.0;
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 250;
        public static final double Y_UPPER_BOUND = 0;
        public static final double Y_LOWER_BOUND = 600;
        public static final int VERTICAL_VELOCITY_MULTIPLIER = 0;
        public static final int HORIZONTAL_VELOCITY_MULTIPLIER = 0;
        public static final int NUMBER_OF_KILLS = 0;
        public static final int DAMAGE_FLICKER_COUNT = 3;
        public static final int PROJECTILE_X_POSITION_OFFSET = -6;
        public static final int PROJECTILE_Y_POSITION_OFFSET = -10;
    }

    /**
     * User projectile settings.
     */
    public static class UserProjectile {
        public static final String IMAGE_NAME = "userfire.png";
        public static final int IMAGE_HEIGHT = 125;
        public static final int HORIZONTAL_VELOCITY = 15;
        public static final long FIRE_INTERVAL_NANOSECONDS = 500_000_000L;
    }

    /**
     * Enemy plane settings.
     */
    public static class EnemyPlane {
        public static final String IMAGE_NAME = "enemyplane.png";
        public static final int IMAGE_HEIGHT = 150;
        public static final int INITIAL_HEALTH = 1;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 150;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
        public static final double PROJECTILE_X_POSITION_OFFSET = -70;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 50;
        public static final double FIRE_RATE = 0.01;
    }

    /**
     * Enemy plane 1 settings.
     */
    public static class EnemyPlane1 {
        public static final String IMAGE_NAME = "enemy0.png";
        public static final int IMAGE_HEIGHT = 60;
        public static final int INITIAL_HEALTH = 1;
        public static final int HORIZONTAL_VELOCITY = -5;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 150;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
        public static final double PROJECTILE_X_POSITION_OFFSET = -55;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 15;
        public static final double FIRE_RATE = 0.008;
    }

    /**
     * Enemy plane 2 settings.
     */
    public static class EnemyPlane2 {
        public static final String IMAGE_NAME = "enemy1.png";
        public static final int IMAGE_HEIGHT = 70;
        public static final int INITIAL_HEALTH = 2;
        public static final int HORIZONTAL_VELOCITY = -4;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 150;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
        public static final double PROJECTILE_X_POSITION_OFFSET = -75;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 15;
        public static final double FIRE_RATE = 0.007;
    }

    /**
     * Enemy plane 3 settings.
     */
    public static class EnemyPlane3 {
        public static final String IMAGE_NAME = "enemy2.png";
        public static final int IMAGE_HEIGHT = 80;
        public static final int INITIAL_HEALTH = 3;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 150;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = -40;
        public static final double Y_LOWER_BOUND = 600;
        public static final double PROJECTILE_X_POSITION_OFFSET = -85;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 15;
        public static final double FIRE_RATE = 0.006;
    }

    /**
     * Enemy plane 4 settings.
     */
    public static class EnemyPlane4 {
        public static final String IMAGE_NAME = "enemy3.png";
        public static final int IMAGE_HEIGHT = 70;
        public static final int INITIAL_HEALTH = 1;
        public static final int HORIZONTAL_VELOCITY = -3;
        public static final double MAXIMUM_Y_POSITION = 250.0;
        public static final double MINIMUM_Y_POSITION = -40.0;
        public static final double X_UPPER_BOUND = 150;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH - 150;
        public static final double Y_UPPER_BOUND = 10;
        public static final double Y_LOWER_BOUND = 700;
        public static final double PROJECTILE_X_POSITION_OFFSET = -80;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 15;
        public static final double FIRE_RATE = 0.005;
    }

    /**
     * Enemy projectile settings.
     */
    public static class EnemyProjectile {
        public static final String IMAGE_NAME = "enemyFire.png";
        public static final int IMAGE_HEIGHT = 50;
        public static final int HORIZONTAL_VELOCITY = -5;
        public static final long FIRE_INTERVAL_NANOSECONDS = 100_000_000L;
    }

    /**
     * Boss plane settings.
     */
    public static class BossPlane {
        public static final String IMAGE_NAME = "bossplane.png";
        public static final int IMAGE_HEIGHT = 300;
        public static final int INITIAL_HEALTH = 5;
        public static final double HORIZONTAL_VELOCITY = 4.0;
        public static final int VERTICAL_VELOCITY = 4;
        public static final double INITIAL_X_POSITION = 1000.0;
        public static final double INITIAL_Y_POSITION = 125.0;
        public static final int MOVE_FREQUENCY_PER_CYCLE = 5;
        public static final int ZERO = 0;
        public static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
        public static final int Y_POSITION_UPPER_BOUND = -40;
        public static final int Y_POSITION_LOWER_BOUND = 600;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000L;
        public static final double PROJECTILE_X_POSITION_OFFSET = -100;
        public static final double PROJECTILE_Y_POSITION_OFFSET = 120;
    }

    /**
     * Multi-phase boss plane settings.
     */
    public static class MultiPhaseBossPlane {
        public static final String IMAGE_NAME = "enemy3.png";
        public static final int IMAGE_HEIGHT = 200;
        public static final int REMAINING_HEALTH_PHASE1 = 600;
        public static final int REMAINING_HEALTH_PHASE2 = 598;
        public static final int REMAINING_HEALTH_PHASE3 = 596;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000; // 1 second
        public static final double FIRE_RATE = 0.5; // 50% chance to fire
        public static final double Y_POSITION = (GameSettings.SCREEN_HEIGHT / 2) - 100;
        public static final double X_POSITION = 400;
        public static final int PROJECTILE_X_POSITION_OFFSET = -130;
        public static final int PROJECTILE_Y_POSITION_OFFSET = 75;
        public static final double VERTICAL_VELOCITY = 150.0;
        public static final double HORIZONTAL_VELOCITY = 10.0;
        public static final int Y_UPPER_BOUND = -40;
        public static final int Y_LOWER_BOUND = 600;
        public static final int X_UPPER_BOUND = GameSettings.SCREEN_HEIGHT / 2;
        public static final int X_LOWER_BOUND = 1150;
        public static final long SUMMON_COOLDOWN = 10_000_000_000L; //1_000_000_000L
        public static final int MAX_FRAMES_WITH_SAME_MOVE = 250;
    }

    /**
     * Boss projectile settings.
     */
    public static class BossProjectile {
        public static final String IMAGE_NAME = "fireball.png";
        public static final int IMAGE_HEIGHT = 70;
        public static final double HORIZONTAL_VELOCITY = -7.5;
        public static final long FIRE_INTERVAL_NANOSECONDS = 1_000_000_000L;
        public static final double FIRE_RATE = 0.04;
    }

    /**
     * Boss shield settings.
     */
    public static class BossShield {
        public static final String IMAGE_NAME = "shield.png";
        public static final int IMAGE_HEIGHT = 200;
        public static final int X_POSITION_OFFSET = -80;
        public static final int Y_POSITION_OFFSET = 90;
        public static final double BOSS_SHIELD_PROBABILITY = 0;
        public static final int MAX_FRAMES_WITH_SHIELD = 500;
        public static final int MAX_FRAMES_WITHOUT_SHIELD = 500;
    }

    /**
     * General projectile settings.
     */
    public static class Projectile {
        public static final double X_UPPER_BOUND = 0;
        public static final double X_LOWER_BOUND = GameSettings.SCREEN_WIDTH;
    }

    /**
     * Heart settings.
     */
    public static class Heart {
        public static final String IMAGE_NAME = "heart.png";
        public static final int IMAGE_HEIGHT = 35;
    }

    /**
     * Level 001 settings.
     */
    public static class Level001 {
        public static final int TOTAL_ENEMIES = 5;
        public static final int KILLS_TO_ADVANCE = 5;
        public static final double ENEMY_SPAWN_PROBABILITY = 1;
    }

    /**
     * Level 003 settings.
     */
    public static class Level003 {
        public static final int SURVIVAL_TIME = 100; // in seconds
        public static final int ENEMY_SPAWN_INTERVAL = 1000;
    }

    /**
     * Enum representing sound effects.
     */
    public enum SoundEffect {
        PLAYER_HIT,
        EXPLOSION,
        SCREEN_TRANSITION,
        PLAYER_SHOOT
    }

    /**
     * Enum representing level backgrounds.
     */
    public enum LevelBackground {
        LEVEL_1(1, "background001.jpg"),
        LEVEL_2(2, "background002.jpeg"),
        LEVEL_3(3, "background003.jpg"),
        LEVEL_4(4, "background004.jpg");

        private final int levelNumber;
        private final String backgroundImage;

        LevelBackground(int levelNumber, String backgroundImage) {
            this.levelNumber = levelNumber;
            this.backgroundImage = backgroundImage;
        }

        public int getLevelNumber() {
            return levelNumber;
        }

        public String getBackgroundImage() {
            return backgroundImage;
        }

        /**
         * Method to get background image by level number.
         * @param level the level number
         * @return the background image for the specified level
         */
        public static String getBackgroundImageForLevel(int level) {
            for (LevelBackground bg : values()) {
                if (bg.getLevelNumber() == level) {
                    return bg.getBackgroundImage();
                }
            }
            throw new IllegalArgumentException("Invalid level number: " + level);
        }
    }

    /**
     * Enum representing level background music.
     */
    public enum LevelBGM {
        LEVEL_1(1, "bgm001.wav"),
        LEVEL_2(2, "bgm002.mp3"),
        LEVEL_3(3, "bgm003.mp3"),
        LEVEL_4(4, "bgm004.mp3");

        private final int levelNumber;
        private final String bgm;

        LevelBGM(int levelNumber, String bgm) {
            this.levelNumber = levelNumber;
            this.bgm = bgm;
        }

        public int getLevelNumber() {
            return levelNumber;
        }

        public String getBgm() {
            return bgm;
        }

        /**
         * Method to get background music by level number.
         * @param level the level number
         * @return the background music for the specified level
         */
        public static String getBGMForLevel(int level) {
            for (LevelBGM bgm : values()) {
                if (bgm.getLevelNumber() == level) {
                    return bgm.getBgm();
                }
            }
            throw new IllegalArgumentException("Invalid level number: " + level);
        }
    }

    /**
     * File paths for various resources.
     */
    public static class FilePaths {
        public static final String AUDIO_LOCATION = "/com/example/demo/audios/";
        public static final String IMAGE_LOCATION = "/com/example/demo/images/";

        /**
         * Enum representing sound effects file paths.
         */
        public enum SoundEffect {
            PLAYER_DEATH("player_death.wav"),
            ENEMY_DESTROY("enemy_destroy.wav"),
            TITLESCREEN_TRANSITION("titlescreen_transition.wav"),
            PLAYER_SHOOT("player_shoot.mp3");

            private final String fileName;

            SoundEffect(String fileName) {
                this.fileName = fileName;
            }

            public String getFileName() {
                return fileName;
            }
        }

        /**
         * Enum representing background music file paths.
         */
        public enum BackgroundMusic {
            MENU("menubgm.mp3"),
            LEVEL001("bgm001.wav"),
            LEVEL002("bgm002.mp3"),
            LEVEL003("bgm003.mp3"),
            LEVEL004("bgm004.mp3");

            private final String fileName;

            BackgroundMusic(String fileName) {
                this.fileName = fileName;
            }

            public String getFileName() {
                return fileName;
            }
        }

        /**
         * Enum representing background image file paths.
         */
        public enum BackgroundImage {
            BACKGROUND001("background001.jpg"),
            BACKGROUND002("background002.jpeg"),
            BACKGROUND003("background003.jpg"),
            BACKGROUND004("background004.jpg");

            private final String fileName;

            BackgroundImage(String fileName) {
                this.fileName = fileName;
            }

            public String getFileName() {
                return fileName;
            }
        }

        /**
         * Array of image file names.
         */
        public static final String[] IMAGES = {
            "background001.jpg",
            "background002.jpeg",
            "background003.jpg",
            "background004.jpg",
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
