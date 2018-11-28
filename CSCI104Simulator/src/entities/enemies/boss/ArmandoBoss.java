package entities.enemies.boss;

import java.util.ArrayList;

import engine.GameEngine;
import entities.enemies.CommandType;
import entities.enemies.EnemyPhase;
import entities.enemies.EnemyPosition;
import entities.projectiles.boss.armando.TheFish;
import entities.projectiles.boss.cote.TheBook;
import entities.sprites.Sprite;
import javafx.geometry.Point2D;
import media.SoundType;

public class ArmandoBoss extends Boss {
	
	/* Stores the actual rotation angle this boss character uses for movement */
	private double mActualRotation;
	/* Stores armando's battle cries */
	private ArrayList <SoundType> mArmandoCry;
	
	public ArmandoBoss(EnemyPosition initPosition, Point2D origin, GameEngine controller) 
	{
		super(initPosition, origin, controller);

		this.mInitialMovementSpeed = 6.5;
		this.mMovementSpeed = mInitialMovementSpeed;
		this.mSpriteScale = 150.0;
		this.mPointsValue = 200000;
		this.mMaxAmmoPool = 5;
		this.mCurrentAmmo = mMaxAmmoPool;
		this.mShotsPerFrame = 38;
		this.mAmmoType = BossAmmoType.kProjectile;
		this.mHealth = 40;
		this.mMoveTime = 300;
		this.setSprite(Sprite.kArmando);
		this.setRotate(-90.0);
		this.mActualRotation = this.getRotate();
		
		// Sets up boss sound files
		this.mBossHit = SoundType.kCoteHit;
		this.mBossTauntList = new ArrayList <SoundType> ();
		this.mBossTauntList.add(SoundType.kArmandoTaunt1);
		
		// Sets up armando's cries
		this.mArmandoCry = new ArrayList <SoundType> ();
		this.mArmandoCry.add(SoundType.kArmandoAttack1);
		
		/* The boss attacks 20% faster,
		 * and has 20% more health,
		 * and moves 20% faster when hard mode is enabled */
		if (mController.isHardMode())
		{
			mMoveTime -= (int) ((double)(mMoveTime * 0.2));
			mHealth += (mHealth * 0.2);
			mMovementSpeed += mMovementSpeed * 0.2;
			this.mAdjustedRotationSpeed += 0.2;
			this.mRotationSpeed = this.mAdjustedRotationSpeed;
		}
		
		mController.playSoundOverwritable(SoundType.kArmandoTaunt1);
	}
	
	@Override
	public void update () {
		super.update();
		
		// Only rotate Armando when he is moving
		if (this.mPhase != EnemyPhase.kIdle) {
			this.setRotate(this.getRotate() + 20.0);
		}
		else {
			this.setRotate(-90.0);
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
			
			// Armando attacks more aggressively in hard mode
			if (mController.isHardMode())
			{
				addCommand(CommandType.kFalseRetreat);
				addCommand(CommandType.kAttack);
				addCommand(CommandType.kFalseRetreat);
				addCommand(CommandType.kAttack);
			}
			
			addCommand(CommandType.kRetreat);
		}
		
		else if (this.mPhase == EnemyPhase.kRangedAttack)
		{
			addCommand (CommandType.kBossPrepareRangedAttack);
			addCommand (CommandType.kBossRangedAttack);
			addCommand (CommandType.kFalseRetreat);
			addCommand (CommandType.kBossPrepareRangedAttack);
			addCommand (CommandType.kBossRangedAttack);
			addCommand(CommandType.kRetreat);
		}
	}
	
	@Override
	public void fire() 
	{
		if (this.mPhase == EnemyPhase.kAttack)
		{
			if (mCurrentAmmo > 0 && !this.mFalseRetreatFlag)
			{
				mController.queueEntity(new TheFish (this, mController));
				mController.playSound(this.getBattleCry());
				mCurrentAmmo--;
			}
		}
		else
		{
			mController.queueEntity(new TheBook (this, mController));
		}
	}
	
	@Override
	protected double getRotationAngle () {
		return this.mActualRotation;
	}
	
	@Override
	protected void setRotationAngle (double theta) {
		this.mActualRotation = theta;
	}
	
	/** Returns a random armando battle cry */
	private SoundType getBattleCry () {
		return this.mArmandoCry.get(this.mRand.nextInt(this.mArmandoCry.size()));
	}
	
}
