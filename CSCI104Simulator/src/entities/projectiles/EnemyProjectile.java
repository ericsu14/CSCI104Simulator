package entities.projectiles;

import engine.GameEngine;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import view.Launcher;

public class EnemyProjectile extends Projectile 
{

	public EnemyProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() 
	{
		/* Checks for any collisions with the player */
		for (Entity e : this.mController.getEntities())
		{
			if (e.getType() == EntityType.kPlayer &&  e.intersects(this.getBoundsInLocal()))
			{
				e.setState(EntityState.kPlayerDead);
				setState(EntityState.kDead);
			}
		}
		
		/* Despawns this projectile once it exits the screen */
		if (this.getY() > Launcher.mHeight)
		{
			setState (EntityState.kDead);
		}
	}
	

}
