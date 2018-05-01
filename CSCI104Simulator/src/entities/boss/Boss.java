package entities.boss;

import engine.GameEngine;
import entities.enemies.Enemy;
import entities.enemies.EnemyPosition;
import javafx.geometry.Point2D;

public class Boss extends Enemy 
{
	/* The amount of health this boss character has */
	protected int mHealth;
	
	
	public Boss(EnemyPosition initPosition, Point2D origin, int group, GameEngine controller) 
	{
		super(initPosition, origin, group, controller);
	}
	
	@Override
	public void update()
	{
		
	}

	@Override
	public void fire() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void createAttackVectors() 
	{
		// TODO Auto-generated method stub

	}
	
	@Override
	public void die()
	{
		
	}
	
	/** @return the boss's current health */
	public int getHealth()
	{
		return mHealth;
	}
	
	/** Sets the boss's health to a new value */
	public void setHealth (int health)
	{
		mHealth = health;
	}

}
