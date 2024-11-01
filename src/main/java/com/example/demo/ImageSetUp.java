package com.example.demo;
import javafx.scene.image.Image;
import java.util.EnumMap;
import java.util.Map;


/**
 * Singleton class responsible for managing and providing access to application images.
 */
public class ImageSetUp {

    // Enum to define unique identifiers for each image
    public enum ImageType {
        BACKGROUND1,
        BACKGROUND2,
        BOSSPLANE,
        ENEMYFIRE,
        ENEMYPLANE,
        FIREBALL,
        GAMEOVER,
        HEART,
        SHIELD,
        USERFIRE,
        USERPLANE,
        YOUWIN
    }

    // Map to store images with their corresponding ImageType
    private final Map<ImageType, Image> imageMap;

    // Singleton instance
    private static ImageSetUp instance = null;

    /**
     * Private constructor to prevent external instantiation.
     * Loads all necessary images into the imageMap.
     */
    private ImageSetUp() {
        imageMap = new EnumMap<>(ImageType.class);
        loadImages();
    }

    /**
     * Retrieves the singleton instance of ImageSetUp.
     *
     * @return ImageSetUp instance
     */
    public static ImageSetUp getInstance() {
        if (instance == null) {
            instance = new ImageSetUp();
        }
        return instance;
    }

    /**
     * Loads all images and stores them in the imageMap.
     * Modify this method to include all images your application uses.
     */
    private void loadImages() {
        imageMap.put(ImageType.BACKGROUND1, new Image(getClass().getResource("/com/example/demo/images/background1.jpg").toExternalForm()));
        imageMap.put(ImageType.BACKGROUND2, new Image(getClass().getResource("/com/example/demo/images/background2.jpg").toExternalForm()));
        imageMap.put(ImageType.BOSSPLANE, new Image(getClass().getResource("/com/example/demo/images/bossplane.png").toExternalForm()));
        imageMap.put(ImageType.ENEMYFIRE, new Image(getClass().getResource("/com/example/demo/images/enemyFire.png").toExternalForm()));
        imageMap.put(ImageType.ENEMYPLANE, new Image(getClass().getResource("/com/example/demo/images/enemyplane.png").toExternalForm()));
        imageMap.put(ImageType.FIREBALL, new Image(getClass().getResource("/com/example/demo/images/fireball.png").toExternalForm()));
        imageMap.put(ImageType.GAMEOVER, new Image(getClass().getResource("/com/example/demo/images/gameover.png").toExternalForm()));
        imageMap.put(ImageType.HEART, new Image(getClass().getResource("/com/example/demo/images/heart.png").toExternalForm()));
        imageMap.put(ImageType.SHIELD, new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
        imageMap.put(ImageType.USERFIRE, new Image(getClass().getResource("/com/example/demo/images/userfire.png").toExternalForm()));
        imageMap.put(ImageType.USERPLANE, new Image(getClass().getResource("/com/example/demo/images/userplane.png").toExternalForm()));
        imageMap.put(ImageType.YOUWIN, new Image(getClass().getResource("/com/example/demo/images/youwin.png").toExternalForm()));
    }

    /**
     * Retrieves the image corresponding to the specified ImageType.
     *
     * @param type The ImageType identifier
     * @return The corresponding Image object
     */
    public Image getImage(ImageType type) {
        return imageMap.get(type);
    }
}
