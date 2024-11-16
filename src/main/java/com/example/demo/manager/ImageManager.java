package com.example.demo.manager;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageManager handles the loading and caching of image resources.
 */
public class ImageManager {
    private static final String IMAGE_LOCATION = "/com/example/demo/images/";
    private static ImageManager instance;
    private final Map<String, Image> imageCache = new HashMap<>();

    private ImageManager() {}

    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

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

    public void clearCache() {
        imageCache.clear();
    }
}
