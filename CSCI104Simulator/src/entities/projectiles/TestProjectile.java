package entities.projectiles;

import entities.Entity;
import view.Launcher;

public class TestProjectile extends Projectile 
{

	public TestProjectile(Entity owner, Launcher controller) 
	{
		super(owner, controller);
		this.mSpriteScale = 20.0;
		this.mMovementSpeed = 1.0;
		this.setSprite(mController.mPlayerShipSprite);
		
		this.mMaxTurnRadius = -45.0;
		this.mMinTurnRadius = -135.0;
		this.setRotate(-90.0);
	}

}
