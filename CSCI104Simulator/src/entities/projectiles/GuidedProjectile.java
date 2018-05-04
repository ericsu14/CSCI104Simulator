package entities.projectiles;

import engine.GameEngine;
import entities.Entity;

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
		
		this.setSprite(mController.mBook);
		
		this.mMaxTurnRadius = -45.0;
		this.mMinTurnRadius = -135.0;
		this.trackEntity(controller.getPlayer());
	}

	@Override
	public void update() 
	{
		super.update();
		
	}

}
