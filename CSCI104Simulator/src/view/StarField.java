package view;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import entities.visuals.ConfettiText;
import java.util.Random;

public class StarField extends Pane 
{
	/* Random generator used to randomly generate the coordinates of 
	 * each newly added star */
	private Random mRand;
	
	/* Animation timer used to randomly generate the stars based on
	 * the current dimensions of this pane */
	private AnimationTimer mStarGenerator;
	
	/** Constructs a new starfield object */
	public StarField ()
	{
		super ();
		
		mRand = new Random();
		InitializeAnimations();
	}
	
	/** Plays the falling star animation */
	public void playAnimation()
	{
		mStarGenerator.start();
	}
	
	/** Stops the falling star animation and cleans up all spawned assets */
	public void stopAnimation()
	{
		mStarGenerator.stop();
	}
	
	/** Initializes the animation timers */
	private void InitializeAnimations ()
	{
		mStarGenerator = new AnimationTimer ()
		{
			@Override
			public void handle(long arg0) 
			{
				// TODO:
				
			}
			
		};
	}
}
