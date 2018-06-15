package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;
import entities.sprites.Sprite;

public class BinaryTree extends BossProjectile {
	
	public BinaryTree(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mSpriteScale = 40.0;
		this.mMovementSpeed = 7.0;
		this.setSprite(Sprite.kBinaryTree);
	}
	
	@Override
	public void update () 
	{
		super.update();
	}
	
}
