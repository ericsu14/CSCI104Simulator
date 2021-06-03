package entities.projectiles.enemies;

import engine.GameEngine;
import entities.Entity;
import entities.sprites.Sprite;

public class GuidedProjectile extends EnemyProjectile 
{

	public GuidedProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 15.0;
		this.mMovementSpeed = 5.0;
		
		/* Slightly increases the movement speed of this projectile when
		 * the game is set to hard mode */
		if (mController.isHardMode())
		{
			this.mMovementSpeed += 0.2;
		}
		
		this.setSprite(Sprite.kPlayerBullet);
		
		this.mMaxTurnRadius = -60.0;
		this.mMinTurnRadius = -120.0;
		this.trackEntity(controller.getPlayer());
	}

	@Override
	public void update() 
	{
		super.update();
		
	}

}
