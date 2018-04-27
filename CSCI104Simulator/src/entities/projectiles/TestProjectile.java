package entities.projectiles;

import engine.GameEngine;
import entities.Entity;

public class TestProjectile extends EnemyProjectile 
{

	public TestProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 15.0;
		this.mMovementSpeed = 5.0;
		this.setSprite(mController.mBook);
		
		this.mMaxTurnRadius = -45.0;
		this.mMinTurnRadius = -135.0;
		this.setRotate(-90.0);
		this.trackEntity(controller.getPlayer());
	}

	@Override
	public void update() 
	{
		super.update();
		
	}

}
