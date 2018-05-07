package view;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import media.SoundController;
import media.SoundType;
import entities.visuals.ConfettiText;
import factories.FireworkStyles;
import factories.FireworksFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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
	/* Contains all queued assets */
	private Queue <FireworksCommand> mQueuedFireworks;
	/* Keeps track of the current amount of milliseconds remaining
	 * before the next star could spawn */
	private float mSpawnTimer = 0.0f; 
	/* Total time befor the next star could spawn */
	private static final float mSpawnTime = 7.0f;
	/* Flag used to indicate if a fireworks show is going to happen */
	private boolean mFireworksFlag;
	/* The time it takes for each firework to spawn */
	private int mFireworksTime = 15;
	/* Timer for fireworks show */
	private int mFireworksTimer;
	/* Flag that determines if stars shoudl be spawned */
	private boolean mSpawnStars;
	/* Sound engined for playing sound assets */
	SoundController mSoundEngine;
	
	/** Nested fireworks command class */
	private class FireworksCommand
	{
		public FireworkCommandType mType;
		public int mXCoord;
		public int mYCoord;
		public FireworkStyles mStyle;
		public boolean mCustom = false;
		
		public FireworksCommand ()
		{
			mType = FireworkCommandType.kFirework;
		}
		
		public FireworksCommand (int x, int y)
		{
			mType = FireworkCommandType.kExplosion;
			mXCoord = x;
			mYCoord = y;
		}
		
		public FireworksCommand (int x, int y, FireworkStyles style)
		{
			mType = FireworkCommandType.kExplosion;
			mXCoord = x;
			mYCoord = y;
			mStyle = style;
			mCustom = true;
		}
		
	}
	
	/** Constructs a new starfield object */
	public StarField (boolean spawnStars)
	{
		super ();
		mSpawnStars = spawnStars;
		mRand = new Random();
		mSpawnedStars = new ArrayList <ConfettiText>();
		mDespawnedAssets = new ArrayList <ConfettiText>();
		mQueuedFireworks = new LinkedList <FireworksCommand> ();
		mFireworksFlag = false;
		mSoundEngine = new SoundController();
		
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
	
	/** Enables the fireworks show flag, which randomly spawns fireworks
	 *  until the flag is turned off */
	public void setFireworksFlag (boolean flag)
	{
		mFireworksFlag = flag;
		mFireworksTimer = 0;
	}
	
	/** Spawns a fireworks explosion at a point */
	public void spawnExplosion (int x, int y)
	{
		mQueuedFireworks.add(new FireworksCommand (x, y));
	}
	
	/** Spawns an explosion of a specific type */
	public void spawnExplosion (int x, int y, FireworkStyles style)
	{
		mQueuedFireworks.add(new FireworksCommand (x, y, style));
	}
	
	/** Initializes the animation timers */
	private void InitializeAnimations ()
	{
		mStarGenerator = new AnimationTimer ()
		{
			@Override
			public void handle(long arg0) 
			{
				updateNodes();
				spawnAssets();
			}
			
			public void spawnAssets ()
			{				
				mSpawnTimer -= 1.0f;
				/* Spawns a confetti piece */
				if (mSpawnTimer <= 0.0f)
				{
					if (mSpawnStars)
					{
						mSpawnedStars.add(new ConfettiText ((int)(mRand.nextInt((int)Launcher.mWidth))));
						getChildren().addAll(mSpawnedStars.get(mSpawnedStars.size() - 1));
					}
					mSpawnTimer = mSpawnTime;
				}
				
				/* Starts spawning fireworks, if the flag is true */
				if (mFireworksFlag)
				{
					if (mFireworksTimer <= 0)
					{
						mQueuedFireworks.add(new FireworksCommand ());
						mFireworksTimer = mFireworksTime;
					}
					else
					{
						--mFireworksTimer;
					}
				}
				
				/* Adds in queued assets */
				if (!mQueuedFireworks.isEmpty())
				{
					ArrayList <ConfettiText> explosion;
					FireworksCommand command = mQueuedFireworks.remove();
					if (command.mType == FireworkCommandType.kExplosion)
					{
						if (!command.mCustom)
						{
							explosion = FireworksFactory.spawnExplosion(command.mXCoord, command.mYCoord, FireworksFactory.getRandomExplosion());
						}
						else
						{
							explosion = FireworksFactory.spawnExplosion(command.mXCoord, command.mYCoord, command.mStyle);
						}
					}
					else
					{
						explosion = FireworksFactory.spawnFireworks((int)Launcher.mWidth, (int)Launcher.mHeight, FireworksFactory.getRandomStyle());
						PauseTransition soundDelay = new PauseTransition (Duration.seconds(2));
						soundDelay.setOnFinished(e -> 
						{
							mSoundEngine.playSound(SoundType.kFireworkBlast);
						});
						soundDelay.play();
					}
					mSpawnedStars.addAll(explosion);
					getChildren().addAll(FXCollections.observableArrayList(explosion));
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
					try
					{
						mSpawnedStars.removeAll(mDespawnedAssets);
						getChildren().removeAll(FXCollections.observableArrayList(mDespawnedAssets));
						mDespawnedAssets.clear();
					}
					catch (IllegalArgumentException e)
					{
						mSpawnedStars.removeAll(mDespawnedAssets);
						getChildren().removeAll(FXCollections.observableArrayList(mDespawnedAssets));
						mDespawnedAssets.clear();
					}
				}
			}
			
		};
	}
}
