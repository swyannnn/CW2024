package com.example.demo.manager;

import com.example.demo.util.GameConstant;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;


/**
 * The ImageManager class is responsible for managing images.
 * It provides methods to retrieve individual images and sequences of images
 * from a specified location.
 */
public class ImageManager {
    private static final String IMAGE_LOCATION = GameConstant.FilePaths.IMAGE_LOCATION;
    private final static Map<String, Image> imageCache = new HashMap<>();

    /**
     * Private constructor to prevent instantiation of the ImageManager class.
     * This class is intended to be used as a utility class with static methods.
     */
    private ImageManager() {}

    /**
     * Retrieves an image from the cache or loads it from the specified file if not already cached.
     *
     * @param filename the name of the image file to retrieve
     * @return the Image object corresponding to the specified filename, or null if the image file is not found
     */
    public static Image getImage(String filename) {
        return imageCache.computeIfAbsent(filename, key -> {
        try {
            return new Image(ImageManager.class.getResourceAsStream(IMAGE_LOCATION + key));
        } catch (NullPointerException e) {
            System.err.println("Image file not found: " + key);
            return null;
        }
        });
    }

    public static Image[] getImageSequence(String baseFilename, int count) {
        Image[] images = new Image[count];
        for (int i = 0; i < count; i++) {
            String filename = baseFilename + i + ".png"; // Construct filenames like explosion0.png
            images[i] = getImage(filename);

            if (images[i] == null) {
                System.err.println("Missing image in sequence: " + filename);
            }
        }
        return images;
    }

    public static void cleanup() {
        imageCache.clear();
    }
}
