package entities.projectiles.boss;

import engine.GameEngine;
import entities.Entity;
import entities.projectiles.enemies.EnemyProjectile;

public class BossProjectile extends EnemyProjectile
{	
	public BossProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		
		this.mMaxTurnRadius = -45.0;
		this.mMinTurnRadius = -135.0;
		this.trackEntity(controller.getPlayer());
	}
	
}
