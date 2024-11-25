package com.example.demo.actor.projectile;

import com.example.demo.controller.Controller;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -5;

	public EnemyProjectile(double initialXPos, double initialYPos, Controller controller) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY, controller);
	}
}
