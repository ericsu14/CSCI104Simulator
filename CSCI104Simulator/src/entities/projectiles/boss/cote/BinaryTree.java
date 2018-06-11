package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;

public class BinaryTree extends BossProjectile {
	
	public BinaryTree(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mSpriteScale = 40.0;
		this.mMovementSpeed = 7.0;
		this.setSprite(mController.mBinaryTree);
	}
	
	@Override
	public void update () 
	{
		super.update();
	}
	
}
