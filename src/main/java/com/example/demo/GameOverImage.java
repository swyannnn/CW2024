package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

// 	public GameOverImage(double xPosition, double yPosition) {
// 		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
// //		setImage(ImageSetUp.getImageList().get(ImageSetUp.getGameOver()));
// 		this.setVisible(false);
// 		setLayoutX(xPosition);
// 		setLayoutY(yPosition);
// 	}
	public GameOverImage(double xPosition, double yPosition) {
		try {
			Image image = new Image(getClass().getResource(IMAGE_NAME).toExternalForm(), false);
			setImage(image);
		} catch (OutOfMemoryError e) {
			System.err.println("Error loading image: Out of memory");
			e.printStackTrace();
		}
		this.setVisible(false);
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

	public void showGameOverImage() {
		this.setVisible(true);
	}

}
