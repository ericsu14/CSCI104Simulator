package entities.enemies;

import engine.GameEngine;
import entities.projectiles.TestProjectile;
import javafx.geometry.Point2D;

public class TestEnemy extends Enemy 
{
	public TestEnemy(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{	
		super (initPosition, origin, group, controller);
		this.mInitialMovementSpeed = 4.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 100.0;
		this.mPointsValue = 1000;
		this.mMaxAmmoPool = 10;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 60;
		this.mOffset = (int)(mSpriteScale / 10);
		this.setSprite(controller.mCote);
		setRotate (-90.0);
	}

	public void update ()
	{
		super.update();
	}

	@Override
	public void createAttackVectors() 
	{
		this.mPhase = EnemyPhase.kAttack;
		
		/* Creates the attack vector based on the player's position */
		this.addCommand(CommandType.kPrepareAttack);
		this.addCommand(CommandType.kAttack);
		/* Creates the retreat vector */
		this.addCommand(CommandType.kRetreat);
		
	}
	
	@Override
	public void fire()
	{
		if (mCurrentAmmo > 0)
		{
			mController.queueEntity(new TestProjectile (this, mController));
			mCurrentAmmo--;
		}
	}
}
