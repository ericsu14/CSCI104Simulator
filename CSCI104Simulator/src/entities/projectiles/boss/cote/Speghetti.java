package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossProjectile;

public class Speghetti extends BossProjectile {

	public Speghetti(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		this.mMaxTurnRadius = -360.0;
		this.mMinTurnRadius = 360.0;
		this.mSpriteScale = 40.0;
		this.mMovementSpeed = 10.4;
		this.setSprite(mController.mSpeghetti);
	}
	
}
