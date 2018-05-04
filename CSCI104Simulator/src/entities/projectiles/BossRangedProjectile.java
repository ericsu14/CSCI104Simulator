package entities.projectiles;

import engine.GameEngine;
import entities.Entity;

public class BossRangedProjectile extends EnemyProjectile 
{

	public BossRangedProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 70.0;
		this.mMovementSpeed = 8.2;
		this.setSprite(mController.mBook);
		
		this.mMaxTurnRadius = -30.0;
		this.mMinTurnRadius = -150.0;
		this.trackEntity(controller.getPlayer());
	}

}
