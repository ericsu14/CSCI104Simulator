package entities.enemies;

import engine.GameEngine;
import entities.projectiles.enemies.GuidedProjectile;
import javafx.geometry.Point2D;

public class TestEnemy extends Enemy 
{
	public TestEnemy(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{	
		super (initPosition, origin, group, controller);
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 22.0;
		this.mPointsValue = 1700;
		this.mMaxAmmoPool = 5;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 60;
		this.setSprite(controller.mTestEnemySprite);
		setRotate (-90.0);
		adjustDifficulity ();
	}

	public void update ()
	{
		super.update();
	}

	@Override
	public void createAttackVectors() 
	{
		this.mPhase = EnemyPhase.kAttack;
		this.mCurrentAmmo = this.mMaxAmmoPool;
		
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
			mController.queueEntity(new GuidedProjectile (this, mController));
			mCurrentAmmo--;
		}
	}
}
