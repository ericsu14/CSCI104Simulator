package entities.projectiles;

import engine.GameEngine;
import engine.GameState;
import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import view.Launcher;

public class EnemyProjectile extends Projectile 
{
	/* True when this projectile has left the playing field  */
	private boolean mLeftField;
	
	public EnemyProjectile(Entity owner, GameEngine controller) 
	{
		super(owner, controller);
		mLeftField = false;
	}

	@Override
	public void update() 
	{
		/* Checks for any collisions with the player */
		for (Entity e : this.mController.getEntities())
		{
			if (e.getType() == EntityType.kPlayer &&  e.intersects(this.getBoundsInLocal())
					&& e.getState() == EntityState.kActive)
			{
				kill (e, true);
			}
		}
		
		/* Despawns this projectile once it exits the screen, 
		 * or if the game state is not in running anymore  */
		if (mController.getGameState() != GameState.kGameRunning || this.getY() > Launcher.mHeight)
		{
			mLeftField = true;
			die();
		}
	}
	
	@Override
	public void die()
	{
		super.die();
		if (!mLeftField)
		{
			mController.getGameView().getStarField().spawnExplosion((int)getCenterX(), (int)getCenterY());
		}
	}	

}
