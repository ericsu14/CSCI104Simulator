package entities.projectiles.boss;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.enemies.EnemyProjectile;

public class BossRangedProjectile extends EnemyProjectile 
{

	public BossRangedProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mMaxTurnRadius = -30.0;
		this.mMinTurnRadius = -150.0;
		this.trackEntity(controller.getPlayer());
	}

}
