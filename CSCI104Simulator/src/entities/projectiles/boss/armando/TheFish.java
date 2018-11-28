package entities.projectiles.boss.armando;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;
import entities.sprites.Sprite;

public class TheFish extends BossProjectile {
	
	/* Custom rotate */
	protected double mProjectileRotate;

	public TheFish(Entity owner, GameEngine controller) {
		super(owner, controller);
		
		this.mProjectileRotate = this.getRotate();
		this.mSpriteScale = 50.0;
		this.mMovementSpeed = 9.0;
		this.setSprite(Sprite.kTheFish);
	}
	
	public void update () 
	{
		super.update();
		// Spins the projectile around and around
		this.setRotate(this.getRotate() + 20.0);
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
