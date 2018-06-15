package entities.enemies;

import engine.GameEngine;
import entities.projectiles.enemies.GuidedProjectile;
import entities.sprites.Sprite;
import javafx.geometry.Point2D;
import media.SoundType;

public class Heisenbug extends Enemy {

	public Heisenbug(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{
		super(initPosition, origin, group, controller);
		
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 22.0;
		this.mPointsValue = 1000;
		this.mMaxAmmoPool = 2;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 60;
		this.setSprite(Sprite.kHeisenBug);
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
			mController.queueEntity(new GuidedProjectile (this, mController));
			mCurrentAmmo--;
		}
	}
	
	/** Overrideen to play custom sounds */
	@Override
	public void die()
	{
		super.die();
		mController.playSound(SoundType.kEnemyDie2);
	}
}
