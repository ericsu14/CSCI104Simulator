package entities.projectiles;

import entities.Entity;
import view.Launcher;

public class Projectile extends Entity 
{
	/* A ref. to the entity that shot this projectile */
	protected Entity mOwner;
	
	/* The maximum angle in which this projectile could turn while
	 * tracking an enemy player */
	protected double mMaxTurnRadius;
	
	/** Constructor
	 * 		@param owner - The entity who shot this projectile
	 * 		@param controler - A ref. to the main controller  */
	public Projectile (Entity owner, Launcher controller)
	{
		super (owner.getX(), owner.getY(), controller);
		
		mOwner = owner;
	}
	
	/** Allows this projectile to track another entity.
	 * 		@param victim - The victim who is going to be shot */
	public void trackEntity (Entity victim)
	{
		/* TODO: */
	}
	
}
