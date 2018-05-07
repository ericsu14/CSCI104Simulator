package entities.enemies;

import engine.GameEngine;
import entities.projectiles.UnguidedProjectile;
import javafx.geometry.Point2D;
import media.SoundType;

public class Bohrbug extends Enemy 
{

	public Bohrbug(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{
		super(initPosition, origin, group, controller);
		
		this.mInitialMovementSpeed = 5.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 22.0;
		this.mPointsValue = 750;
		this.mMaxAmmoPool = 5;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 70;
		this.setSprite(controller.mBohrbugSprite);
		setRotate (-90.0);
		adjustDifficulity ();
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
			mController.queueEntity(new UnguidedProjectile (this, mController));
			mCurrentAmmo--;
		}
	}
	
	/** Overrideen to play custom sounds */
	@Override
	public void die()
	{
		super.die();
		mController.playSound(SoundType.kEnemyDie);
	}
}
