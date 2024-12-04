package com.example.demo.manager;

import com.example.demo.util.GameConstant;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageManager handles the loading, caching, and preloading of image resources.
 */
public class ImageManager {
    private static final String IMAGE_LOCATION = GameConstant.FilePaths.IMAGE_LOCATION;
    private final static Map<String, Image> imageCache = new HashMap<>();
    
        // Private constructor to enforce Singleton pattern
        private ImageManager() {
            preloadImages();
        }
    
        /**
         * Loads and caches an image from the specified filename.
         *
         * @param filename The name of the image file.
         * @return The Image object, or null if the file is not found.
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

    /**
     * Loads and caches a series of images based on a pattern.
     *
     * @param baseFilename The base name of the file, e.g., "explosion".
     * @param count        The number of images in the sequence, e.g., 7 for explosion0.png to explosion6.png.
     * @return An array of Images, or null if any file in the sequence is missing.
     */
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

    /**
     * Preloads all images specified in GameConstant.
     */
    private void preloadImages() {
        for (String filename : GameConstant.FilePaths.IMAGES) {
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
