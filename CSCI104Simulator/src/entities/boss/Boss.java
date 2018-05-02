package entities.boss;

import engine.GameEngine;
import entities.Entity;
import entities.EntityType;
import entities.enemies.Command;
import entities.enemies.CommandType;
import entities.enemies.Enemy;
import entities.enemies.EnemyPhase;
import entities.enemies.EnemyPosition;
import entities.projectiles.BossProjectile;
import javafx.geometry.Point2D;
import view.Launcher;

public class Boss extends Enemy 
{
	/* The amount of health this boss character has */
	protected int mHealth;
	/* The ammo type this boss character is using at the moment */
	protected BossAmmoType mAmmoType;
	/* The time it takes (in frames) before the boss makes its next move */
	protected int mMoveTime = 300;
	/* Timer that keeps track of the boss's move time */
	protected int mMoveTimer;
	/* The ammo pool for the boss's projectile attack */
	protected int mProjectileAmmoPool;
	/* The ammo pool for the boss's ranged attack */
	protected int mRangedAmmoPool;
	/* The projectile used for the boss's projectile attack */
	protected BossProjectile mBossProjectile;
	/* The projectile used for the boss's ranged attack */
	protected BossProjectile mRangedProjectile;
	
	public Boss(EnemyPosition initPosition, Point2D origin, GameEngine controller) 
	{
		super(initPosition, origin, -1, controller);
		
		/* Overrides the enemy's setup code in the constructor */
		this.mCommandQueue.clear();
		this.mSpawnAttackFlag = false;
		this.mSpawnPoint = new Point2D (Launcher.mWidth / 2.0, -100);
		this.setX(mSpawnPoint.getX());
		this.setY(mSpawnPoint.getY());
		mCommandQueue.add(new Command (CommandType.kMove, this, mOriginPoint));
		mNumSpawnWaypoints = mCommandQueue.size();
		
		/* Setup member variables */
		this.mType = EntityType.kBoss;
		this.mMoveTimer = this.mMoveTime;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		/* If the boss's move time expired, randomly select the boss's move */
		if (this.mPhase == EnemyPhase.kIdle)
		{
			if (mMoveTimer <= 0)
			{
				int nextMove = Entity.mRand.nextInt(2);
				
				switch (nextMove)
				{
					/* In this case, do a projectile attack */
					case 0:
					{
						this.mPhase = EnemyPhase.kAttack;
						break;
					}
					/* In this case, do a ranged attack */
					case 1:
					{
						this.mPhase = EnemyPhase.kRangedAttack;
						break;
					}
					/* If random fails for some reason, do a projectile attack */
					default:
					{
						this.mPhase = EnemyPhase.kAttack;
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
	}

	@Override
	public void fire() 
	{
	}

	@Override
	public void createAttackVectors() 
	{
		if (this.mPhase == EnemyPhase.kAttack)
		{
			this.mCurrentAmmo = this.mMaxAmmoPool;
			addCommand(CommandType.kPrepareAttack);
			addCommand(CommandType.kAttack);
			addCommand(CommandType.kRetreat);
		}
		
		else if (this.mPhase == EnemyPhase.kRangedAttack)
		{
			addCommand (CommandType.kBossPrepareRangedAttack);
			addCommand (CommandType.kBossRangedAttack);
			addCommand(CommandType.kRetreat);
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
	

}
