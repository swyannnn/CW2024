package com.example.demo.manager;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class ResourceManager {
    private static ResourceManager instance;
    private Map<String, Image> imageCache = new HashMap<>();
    private Map<String, Media> mediaCache = new HashMap<>();

    private ResourceManager() {}

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public Image getImage(String path) {
        return imageCache.computeIfAbsent(path, p -> new Image(getClass().getResourceAsStream(p)));
    }

    public Media getMedia(String path) {
        return mediaCache.computeIfAbsent(path, p -> new Media(getClass().getResource(p).toExternalForm()));
    }

    public void unloadAll() {
        imageCache.clear();
        mediaCache.clear();
    }
}
