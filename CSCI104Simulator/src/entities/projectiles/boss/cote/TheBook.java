package entities.projectiles.boss.cote;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.boss.BossRangedProjectile;

public class TheBook extends BossRangedProjectile {

	public TheBook(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mSpriteScale = 70.0;
		this.mMovementSpeed = 8.2;
		this.setSprite(mController.mBook);
	}

}
