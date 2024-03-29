package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;
import entities.sprites.Sprite;

public class Speghetti extends BossProjectile {
	
	/* Custom rotate */
	protected double mProjectileRotate;
	
	public Speghetti(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mProjectileRotate = this.getRotate();
		this.mSpriteScale = 100.0;
		this.mMovementSpeed = 4.4;
		this.setSprite(Sprite.kSpeghetti);
	}
	
	@Override
	public void update () 
	{
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
