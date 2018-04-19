package entities.enemies;

import entities.Entity;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import view.Launcher;
import java.util.Random;

public class TestEnemy extends Entity {
	
	/* Scale of the images */
	private final static double kSpriteScale = 20.0;
	private final static double kSpriteHeightOffset = 0.0;
	
	private AnimationTimer mTestMovement;
	private Random mRand = new Random();

	public TestEnemy(double x, double y, Launcher controller) 
	{
		super(x, y, controller);
		
		/* Initializes the enemy's sprite */
		setImage(mController.mTestEnemy.getImage());
		
		setFitHeight(kSpriteScale + kSpriteHeightOffset);
		setFitWidth(kSpriteScale);
		
		mRand = new Random ();
		
		testMovement();
	}
	
	
	public void testMovement ()
	{
		this.moveEntity(new Point2D (150.0, 300.0));
	}
}
