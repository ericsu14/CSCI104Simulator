package entities.enemies;

import entities.Entity;
import javafx.animation.AnimationTimer;
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
		setRotate (Math.toRadians(180));
		
		mRand = new Random ();
		
		testMovement();
	}
	
	
	public void testMovement ()
	{
		mTestMovement = new AnimationTimer () {

			@Override
			public void handle(long arg0) 
			{
				// TODO Auto-generated method stub
				setX (getX() + mRand.nextInt(3));
				setY (getY() + 1);
			}
			
		};
		
		mTestMovement.start();
		mRotationAnimation.start();
		
	}
}
