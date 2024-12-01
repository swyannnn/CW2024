package com.example.demo.actor.projectile;

import com.example.demo.controller.Controller;

public class BossProjectile extends Projectile {
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final double HORIZONTAL_VELOCITY = -7.5;
	private static final int INITIAL_X_POSITION = 950;

	public BossProjectile(double initialXPos, double initialYPos, Controller controller) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY, controller);
	}
}
