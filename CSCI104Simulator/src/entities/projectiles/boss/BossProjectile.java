package entities.projectiles.boss;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.enemies.EnemyProjectile;

public class BossProjectile extends EnemyProjectile
{
	/* Custom rotate */
	protected double mProjectileRotate;
	
	public BossProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mMaxTurnRadius = -45.0;
		this.mMinTurnRadius = -135.0;
		mProjectileRotate = this.getRotate();
		this.trackEntity(controller.getPlayer());
	}
	
	@Override
	public void update () {
		super.update();
		
		// Spins the projectile around and around
		this.setRotate(this.getRotate() + 10.0);
	}
	@Override
	protected double getRotationAngle () {
		return this.mProjectileRotate;
	}
	
	@Override
	protected void setRotationAngle (double theta) {
		this.mProjectileRotate = theta;
	}
	
}
