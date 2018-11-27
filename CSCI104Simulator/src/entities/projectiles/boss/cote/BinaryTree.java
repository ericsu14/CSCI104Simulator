package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;
import entities.sprites.Sprite;

public class BinaryTree extends BossProjectile {
	
	/* Custom rotate */
	protected double mProjectileRotate;
	
	public BinaryTree(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mProjectileRotate = this.getRotate();
		this.mSpriteScale = 40.0;
		this.mMovementSpeed = 7.0;
		this.setSprite(Sprite.kBinaryTree);
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
