package entities.enemies;

import engine.GameEngine;
import entities.projectiles.enemies.GuidedProjectile;
import entities.sprites.Sprite;
import javafx.geometry.Point2D;
import media.SoundType;
import util.CSSColor;

public class Mandelbug extends Enemy
{
	/*  The amount of health this enemy has */
	private int mHealth;
	
	public Mandelbug(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{
		super(initPosition, origin, group, controller);
		
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 22.0;
		this.mPointsValue = 1500;
		this.mMaxAmmoPool = 4;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 60;
		this.mWaypointOffset = (int)(mSpriteScale / 10);
		this.setSprite(Sprite.kMandelBug);
		mHealth = 2;
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
		this.addCommand(CommandType.kFalseRetreat);
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
	
	@Override
	public void die()
	{
		--mHealth;
		
		/* Only die once its health has been depleted */
		if (mHealth <= 0)
		{
			super.die();
			mController.playSound(SoundType.kShieldDie);
			mController.getGameView().showTextOnPoint(this.getScore() + "", CSSColor.kMagenta, this.getPosition());
		}
		/* Otherwise, it becomes very anger */
		else
		{	
			/* Because this enemy is very anger,
			 * its stats are upgraded */
			this.mShotsPerFrame = 50;
			this.mInitialMovementSpeed += 0.2;
			this.mMaxAmmoPool = 5;
			this.mSpriteScale = 25.0;
			this.setSprite(Sprite.kDamagedBug);
			mController.getGameView().getSoundEngine().playSound(SoundType.kShieldHit);
		}
	}
	
}
