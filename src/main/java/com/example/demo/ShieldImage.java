package com.example.demo;

import com.example.demo.manager.ImageManager;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
    
    private static final String IMAGE_PATH = "shield.png"; // Updated path
    private static final int SHIELD_SIZE = 200;
    
    public ShieldImage() {
        // Load the image using the correct resource path
		this.setImage(ImageManager.getInstance().getImage(IMAGE_PATH));
        this.setVisible(false);
        this.setFitHeight(SHIELD_SIZE);
        this.setFitWidth(SHIELD_SIZE);
    }

    public void showShield() {
        this.setVisible(true);
    }
    
    public void hideShield() {
        this.setVisible(false);
    }
}
