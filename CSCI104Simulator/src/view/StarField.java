package view;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import entities.visuals.ConfettiText;

import java.util.ArrayList;
import java.util.Random;

public class StarField extends Pane 
{
	/* Random generator used to randomly generate the coordinates of 
	 * each newly added star */
	private Random mRand;
	
	/* Animation timer used to randomly generate the stars based on
	 * the current dimensions of this pane */
	private AnimationTimer mStarGenerator;
	
	/* Container storing all of the spawned "star objects" */
	private ArrayList <ConfettiText> mSpawnedStars;
	
	/* Stores all despawned assets */
	private ArrayList <ConfettiText> mDespawnedAssets;
	
	/* Keeps track of the current amount of milliseconds remaining
	 * before the next star could spawn */
	private float mSpawnTimer = 0.0f; 
	
	/* Total time befor the next star could spawn */
	private static final float mSpawnTime = 7.0f;
	
	/** Constructs a new starfield object */
	public StarField ()
	{
		super ();
		
		mRand = new Random();
		mSpawnedStars = new ArrayList <ConfettiText>();
		mDespawnedAssets = new ArrayList <ConfettiText>();
		
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
		
		/* Cleans up all assets */
		mSpawnedStars.removeAll(mDespawnedAssets);
		getChildren().removeAll(FXCollections.observableArrayList(mDespawnedAssets));
		mDespawnedAssets.clear();
		
		getChildren().removeAll(FXCollections.observableArrayList(mSpawnedStars));
		mSpawnedStars.clear();
	}
	
	/** Initializes the animation timers */
	private void InitializeAnimations ()
	{
		mStarGenerator = new AnimationTimer ()
		{
			@Override
			public void handle(long arg0) 
			{
				spawnAssets();
				updateNodes ();
			}
			
			public void spawnAssets ()
			{
				mSpawnTimer -= 1.0f;
				/* Spawns a confetti piece */
				if (mSpawnTimer <= 0.0f)
				{
					mSpawnedStars.add(new ConfettiText ((int)(mRand.nextInt((int)getWidth()))));
					getChildren().addAll(mSpawnedStars.get(mSpawnedStars.size() - 1));
					mSpawnTimer = mSpawnTime;
				}
			}
			
			/** Scans the current set of alive nodes and updates each one individually.
			 *  This also checks for any nodes that are eligible for despawning. */
			public void updateNodes ()
			{
				/* Updates all stars */
				for (ConfettiText t : mSpawnedStars)
				{
					t.update();
					if (t.canDespawn())
					{
						mDespawnedAssets.add(t);
					}
				}
				
				/* Removes dead stars from the game */
				if (!mDespawnedAssets.isEmpty())
				{
					mSpawnedStars.removeAll(mDespawnedAssets);
					getChildren().removeAll(FXCollections.observableArrayList(mDespawnedAssets));
					mDespawnedAssets.clear();
				}
			}
			
		};
	}
}
