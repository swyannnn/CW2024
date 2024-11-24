package com.example.demo.actors.projectile;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -5;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		System.out.println("i am being called");
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


}
