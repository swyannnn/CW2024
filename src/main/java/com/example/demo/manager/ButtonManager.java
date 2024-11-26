package com.example.demo.manager;

import com.example.demo.util.GameConstant;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * ButtonManager class provides utility methods to create and style buttons.
 */
public class ButtonManager {
     /**
     * Creates a styled button with specified text, width, height, and font size.
     *
     * @param text The text to display on the button.
     * @param width The preferred width of the button.
     * @param height The preferred height of the button.
     * @param fontSize The font size for the button text.
     * @return A styled Button instance.
     */
    public static Button createButton(String text, double width, double height, double fontSize) {
        Button button = new Button(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setFont(Font.font("Arial", fontSize)); // Set font style and size
        return button;
    }


    /**
     * Applies additional styling or behavior to a button.
     * You can add more methods here if needed.
     *
     * @param button The button to style.
     * @param style A CSS style string to apply to the button.
     */
    public static void styleButton(Button button, String style) {
        button.setStyle(style);
    }

        /**
     * Creates a button with an image icon, specified width, and height.
     *
     * @param imagePath The relative path to the image resource.
     * @param width The preferred width of the button.
     * @param height The preferred height of the button.
     * @return A Button instance with an image icon.
     */
    public static Button createImageButton(String imagePath, double width, double height) {
        Button button = new Button();
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle("-fx-background-color: transparent;"); // Optional: Make background transparent

        Image image = ImageManager.getInstance().getImage(imagePath);
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(true);
            button.setGraphic(imageView);
        } else {
            System.err.println("Failed to load image for button: " + imagePath);
            // Optionally, set default text or a placeholder
            button.setText("Button");
        }

        return button;
    }
}
