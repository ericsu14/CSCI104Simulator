package entities.enemies.boss;

import java.util.ArrayList;

import engine.GameEngine;
import entities.EntityState;
import entities.EntityType;
import entities.enemies.Command;
import entities.enemies.CommandType;
import entities.enemies.Enemy;
import entities.enemies.EnemyPhase;
import entities.enemies.EnemyPosition;
import javafx.geometry.Point2D;
import media.SoundType;
import util.CSSColor;
import view.Launcher;

public abstract class Boss extends Enemy 
{
	/* The amount of health this boss character has */
	protected int mHealth;
	/* The ammo type this boss character is using at the moment */
	protected BossAmmoType mAmmoType;
	/* The time it takes (in frames) before the boss makes its next move */
	protected int mMoveTime;
	/* Timer that keeps track of the boss's move time */
	protected int mMoveTimer;
	/* The ammo pool for the boss's projectile attack */
	protected int mProjectileAmmoPool;
	/* The ammo pool for the boss's ranged attack */
	protected int mRangedAmmoPool;
	/* Flag used to indicate that the boss's difficulity has been adjsuted */
	protected boolean mAdjustedBossDifficulity;
	/* The sound type used to specify the boss hit sound.
	 * Can be overridden by other instances of this class. */
	protected SoundType mBossHit = SoundType.kEnemyHit;
	/* The sound type used to specify the boss die sound.
	 * Can be overridden by other instances of this class. */
	protected SoundType mBossDie  = SoundType.kBossDie;
	/* An arraylist of taunts the boss could use while preparing a ranged attack */
	protected ArrayList <SoundType> mBossTauntList = null;
	
	
	public Boss(EnemyPosition initPosition, Point2D origin, GameEngine controller) 
	{
		super(initPosition, origin, -1, controller);
		this.setCache(true);
		
		/* Overrides the enemy's setup code in the constructor */
		this.mCommandQueue.clear();
		this.mSpawnAttackFlag = false;
		this.mSpawnPoint = new Point2D (Launcher.mWidth / 2.0, -500);
		this.setX(mSpawnPoint.getX());
		this.setY(mSpawnPoint.getY());
		mCommandQueue.add(new Command (CommandType.kMove, this, mOriginPoint));
		mNumSpawnWaypoints = mCommandQueue.size();
		
		/* Setup member variables */
		this.mType = EntityType.kBoss;
		this.mMoveTimer = this.mMoveTime;
		mAdjustedBossDifficulity = false;
		
	}
	
	@Override
	public void update()
	{
		super.update();
		
		/* If the boss's move time expired, randomly select the boss's move */
		if (this.mPhase == EnemyPhase.kIdle && mController.getPlayer().getState() != EntityState.kPlayerDead)
		{
			if (mMoveTimer <= 0)
			{
				int nextMove = this.mRand.nextInt(4);
				switch (nextMove)
				{
					/* In this case, do a ranged attack */
					case 1:
					{
						this.mPhase = EnemyPhase.kRangedAttack;
						this.mAmmoType = BossAmmoType.kRanged;
						mController.playSoundOverwritable(this.getRandomTaunt());
						break;
					}
					/* Otherwise, do a projectile attack */
					default:
					{
						this.mPhase = EnemyPhase.kAttack;
						this.mAmmoType = BossAmmoType.kProjectile;
						break;
					}
				}
				this.createAttackVectors();
				mMoveTimer = mMoveTime;
			}
			else
			{
				--mMoveTimer;
			}
		}
		else
		{
			mMoveTimer = mMoveTime;
		}
		
		/* Decreases the boss move time by 60% once all of its minions are dead
		 * and moves faster */
		if (mController.getNumEnemies() <= 0 && !mAdjustedBossDifficulity)
		{
			mMoveTime -= (int) ((double)(mMoveTime * 0.6));
			mMovementSpeed += 0.3;
			this.mMaxAmmoPool = 6;
			mAdjustedBossDifficulity = true;
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
			mController.playSound(this.mBossDie);
			mController.findCurrentBoss();
			mController.getGameView().showTextOnPoint(this.getScore() + "", CSSColor.kYellow, this.getCenter());
		}
		else
		{
			mController.playSound(this.mBossHit);
		}
	}
	
	/** @return the boss's current health */
	public int getHealth()
	{
		return mHealth;
	}
	
	/** Sets the boss's health to a new value */
	public void setHealth (int health)
	{
		mHealth = health;
	}
	
	/** @return the boss's ammo type */
	public BossAmmoType getAmmoType ()
	{
		return mAmmoType;
	}
	
	/** Sets the boss's ammo type */
	public void setAmmoType (BossAmmoType newType)
	{
		mAmmoType = newType;
	}
	
	/** Selects a random boss taunt for this entity to use */
	public SoundType getRandomTaunt ()
	{
		/* If the boss taunt arraylist is still empty (not overridden),
		 * return an empty sound taunt */
		if (this.mBossTauntList == null)
		{
			return SoundType.kNull;
		}
		
		/* Otherwise, return a random index of that list  */
		return mBossTauntList.get(this.mRand.nextInt(mBossTauntList.size()));
	}
	

}
