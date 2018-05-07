package entities.boss;

import engine.GameEngine;
import entities.enemies.CommandType;
import entities.enemies.EnemyPhase;
import entities.enemies.EnemyPosition;
import javafx.geometry.Point2D;

public class CoteBoss extends Boss {

	public CoteBoss(EnemyPosition initPosition, Point2D origin, GameEngine controller) 
	{
		super(initPosition, origin, controller);

		this.mInitialMovementSpeed = 7.0;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 100.0;
		this.mPointsValue = 200000;
		this.mMaxAmmoPool = 5;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 42;
		this.mAmmoType = BossAmmoType.kProjectile;
		this.mHealth = 30;
		this.mMoveTime = 200;
		this.setSprite(this.mController.mCote);
		this.setRotate(-90.0);
		
		/* The boss attacks 10% faster when hard mode is enabled */
		if (mController.isHardMode())
		{
			mMoveTime -= (int) ((double)(mMoveTime * 0.1));
		}
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
			addCommand (CommandType.kAttackMove);
			addCommand (CommandType.kBossPrepareRangedAttack);
			addCommand (CommandType.kBossRangedAttack);
			addCommand(CommandType.kRetreat);
		}
	}

}
