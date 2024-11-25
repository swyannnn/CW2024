package com.example.demo.actor.projectile;

import com.example.demo.controller.Controller;
import com.example.demo.util.GameConstant;

public class EnemyProjectile extends Projectile {
	
	private static final String imageName = GameConstant.EnemyProjectile.IMAGE_NAME;
	private static final int imageHeight = GameConstant.EnemyProjectile.IMAGE_HEIGHT;
	private static final int horizontalVelocity = GameConstant.EnemyProjectile.HORIZONTAL_VELOCITY;

	public EnemyProjectile(double initialXPos, double initialYPos, Controller controller) {
		super(imageName, imageHeight, initialXPos, initialYPos, horizontalVelocity, controller);
	}
}
