package com.example.demo.manager;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * ButtonManager class provides utility methods to create and style buttons.
 */
public class ButtonManager {

    /**
     * Creates a styled button with specified text, width, and height.
     *
     * @param text The text to display on the button.
     * @param width The preferred width of the button.
     * @param height The preferred height of the button.
     * @return A styled Button instance.
     */
    public static Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setFont(Font.font("Arial", 20)); // Set default font style and size
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
}
