package com.example.demo.manager;

import com.example.demo.util.GameConstant;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageManager handles the loading, caching, and preloading of image resources.
 */
public class ImageManager {
    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private static ImageManager instance;
    private final Map<String, Image> imageCache = new HashMap<>();

    // Private constructor to enforce Singleton pattern
    private ImageManager() {
        preloadImages();
    }

    /**
     * Retrieves the singleton instance of ImageManager.
     *
     * @return The singleton instance of ImageManager.
     */
    public static synchronized ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    /**
     * Loads and caches an image from the specified filename.
     *
     * @param filename The name of the image file.
     * @return The Image object, or null if the file is not found.
     */
    public Image getImage(String filename) {
        return imageCache.computeIfAbsent(filename, key -> {
            try {
                return new Image(getClass().getResourceAsStream(IMAGE_LOCATION + key));
            } catch (NullPointerException e) {
                System.err.println("Image file not found: " + key);
                return null;
            }
        });
    }

    /**
     * Preloads all images specified in GameConstant.
     */
    private void preloadImages() {
        for (String filename : GameConstant.IMAGE_FILES) {
            getImage(filename); // Load and cache each image
        }
    }

    /**
     * Clears the image cache.
     */
    public void clearCache() {
        imageCache.clear();
    }
}
