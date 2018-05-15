package entities.boss;

import java.util.ArrayList;

import engine.GameEngine;
import entities.enemies.CommandType;
import entities.enemies.EnemyPhase;
import entities.enemies.EnemyPosition;
import javafx.geometry.Point2D;
import media.SoundType;

public class CoteBoss extends Boss {

	public CoteBoss(EnemyPosition initPosition, Point2D origin, GameEngine controller) 
	{
		super(initPosition, origin, controller);

		this.mInitialMovementSpeed = 6.5;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 100.0;
		this.mPointsValue = 200000;
		this.mMaxAmmoPool = 5;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 42;
		this.mAmmoType = BossAmmoType.kProjectile;
		this.mHealth = 40;
		this.mMoveTime = 300;
		this.setSprite(this.mController.mCote);
		this.setRotate(-90.0);
		
		// Sets up boss sound files
		this.mBossHit = SoundType.kCoteHit;
		this.mBossTauntList = new ArrayList <SoundType> ();
		this.mBossTauntList.add(SoundType.kCoteTaunt1);
		this.mBossTauntList.add(SoundType.kCoteTaunt2);
		this.mBossTauntList.add(SoundType.kCoteTaunt3);
		
		/* The boss attacks 20% faster,
		 * and has 20% more health,
		 * and moves 20% faster when hard mode is enabled */
		if (mController.isHardMode())
		{
			mMoveTime -= (int) ((double)(mMoveTime * 0.2));
			mHealth += (mHealth * 0.2);
			mMovementSpeed += mMovementSpeed * 0.2;
		}
		
		mController.playSoundOverwritable(this.getRandomTaunt());
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
