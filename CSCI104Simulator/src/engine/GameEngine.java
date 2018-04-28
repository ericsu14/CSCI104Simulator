package engine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import entities.Entity;
import entities.EntityState;
import entities.EntityType;
import entities.enemies.Bohrbug;
import entities.enemies.Enemy;
import entities.enemies.EnemyPhase;
import entities.enemies.EnemyPosition;
import entities.enemies.Heisenbug;
import entities.enemies.Mandelbug;
import entities.enemies.TestEnemy;
import entities.player.Player;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.GameView;
import view.Launcher;
public class GameEngine 
{
	/* SPRITE ASSETS*/
	/* Stores the image file for the dummy test enemy */
	public ImageView mTestEnemySprite;
	/* Stores the image file for the playership */
	public ImageView mPlayerShipSprite;
	/* Stores the image file for the bohrbug, formally known as the test enemy sprite */
	public ImageView mBohrbugSprite;
	/* Stores the image file for the heisenbug */
	public ImageView mHeisenbugSprite;
	/* Stores the image file for the mandelbug */
	public ImageView mMandelBugSprite;
	/* Stores the image file for the player bullet */
	public ImageView mPlayerBulletSprite;
	/* Stores the image file for the linked list projectile */
	public ImageView mLinkedList;
	/* The second in command dark lord aCote */
	public ImageView mCote;
	/* The book that the dark lord aCote loves more than life itself */
	public ImageView mBook;
	
	/* The current level the player is in */
	private int mCurrentLevel;
	/* A vector of game enemies */
	private ArrayList <Entity> mGameEntities;
	/* Vector of dead game entities */
	private ArrayList <Entity> mDeadEntities;
	/* A queue of entities that are going to be added into the next game tick */
	private Queue <Entity> mQueuedEntities;
	/* The player's current score */
	private long mPlayerScore;
	/* The game's player character */
	private Player mPlayer;
	/* The amount of lives the player has remaining */
	private int mCurrentLives;
	/* A reference to the game's gameview */
	private GameView mGameView;
	/* Defines the game's left border */
	private int mLeftBorder;
	/* Defines the game's right border */
	private int mRightBorder;
	/* Defines the maximum height an entity could go before being elligible for despawning */
	private int mMaxHeight;
	/* Defines the maximum width of the playing field */
	private int mMaxWidth;
	/* The total number of enemy groupings in this current level */
	private int mMaxGroups;
	/* The random number generator used for calculating groups */
	private Random mRand;
	/* Animation timer used for the game loop */
	private AnimationTimer mGameLoop;
	/* Flag that ensures that the player ship is only spawned once throughout the entire lifespan of this application */
	private boolean spawnedPlayerFlag = false;
	/* Time it takes (in frames) before the next attack wave could start */
	private int mAttackWaveTime;
	/* Timer for attack waves */
	private int mAttackWaveTimer;
	/* The number of enemies on the game world */
	private int mNumEnemies;
	/* Y offset that dictates where the player would be fixed to */
	private int mPlayerYOffset = 100;
	/* The current state of the game */
	private GameState mGameState;
	/* Pause transition used to display a prompt to the player 
	 * before the game or new level starts */
	private PauseTransition mPromptTimer;
	/* Time it takes for the pause transition to display the prompt before starting the game */
	private int mPromptTime = 3;
	/* Flag that determines if the prompt is currently being displayed */
	private boolean mPromptFlag = false;
	
	public GameEngine (GameView gameView)
	{	
		mGameView = gameView;
		/* Initializes the game's sprite content */
		mTestEnemySprite = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mPlayerShipSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerShip.png")));
		mBohrbugSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/testEnemy.png")));
		mHeisenbugSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/heisenbug.png")));
		mMandelBugSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/mandelBug.png")));
		mPlayerBulletSprite = new ImageView (new Image(getClass().getClassLoader().getResourceAsStream("assets/img/playerBullet.png")));
		mLinkedList = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/linkedList.png")));
		mCote = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/cote.png")));
		mBook = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/theBook.jpeg")));
		
		/* Initializes member variables */
		setCurrentLevel(1);
		setPlayerScore(0);
		mGameEntities = new ArrayList <Entity>();
		mDeadEntities = new ArrayList <Entity>();
		mQueuedEntities = new LinkedList <Entity> ();
		mGameState = GameState.kGameStart;
		mRand = new Random ();
		
		/* Attack wave timer */
		mAttackWaveTime = 650;
		mAttackWaveTimer = 0;
		
		/* Sets up the game's borders */
		mMaxWidth = (int)Launcher.mWidth;
		mLeftBorder = 50;
		mRightBorder = mMaxWidth - mLeftBorder - 24;
		
		mPlayer = new Player (Launcher.mWidth / 2, Launcher.mHeight - mPlayerYOffset, this);
		
		mGameLoop = new AnimationTimer ()
		{
			/* Variables used for calculating the initial spawn animation for game enemies */
			private int mSpawnIntervals = 0;
			private int mMaxSpawnInterval = 15;
			private Entity mFocusedEnemy = null;
			
			/* Number of attack groups that could attack at a time */
			private int mNumAttackGroups = 3;		
			
			@Override
			public void handle(long now) 
			{	
				update (now);
				checkDeadEntities();
				checkGameStatus();
				/* Updates the UI component */
				mGameView.refreshUI();
				
				/* Adds all queued entities into the game */
				if (!mQueuedEntities.isEmpty())
				{
					addChild (mQueuedEntities.remove());
				}
			}
			
			/** The main game loop */
			public void update (long now)
			{
				/* Randomly selects groups of enemies to attack the player once they
				 * are ready */
				if (canAttack())
				{
					for (int i = 0; i < mNumAttackGroups; ++i)
					{
						int currentGroup;
						ArrayList <Enemy> currentEnemies;
						
						do
						{
							currentGroup = mRand.nextInt(mMaxGroups);
							currentEnemies = this.getEnemies(currentGroup);
						} while (currentEnemies.isEmpty());
						
						for (Enemy e : currentEnemies)
						{
							e.createAttackVectors();
						}
					}
					
				}
				
				/* Updates each individual entity */
				for (Entity e : mGameEntities)
				{
					/* Sets the current focused enemy to the first enemy that has just been spawned in */
					if (mFocusedEnemy == null)
					{
						if (e.getState() == EntityState.kJustSpawned)
						{
							mFocusedEnemy = e;
						}
					}
					
					/* Ensures that only one (just spawned in enemy) could move to their location once every 
					 * mMaxSpawnInterval frames */
					if (e.getState() == EntityState.kJustSpawned && e == mFocusedEnemy)
					{
						if (mSpawnIntervals < mMaxSpawnInterval)
						{
							mSpawnIntervals++;
						}
						else
						{
							mFocusedEnemy.update();
							mFocusedEnemy.setState(EntityState.kActive);
							mFocusedEnemy = null;
							mSpawnIntervals = 0;
						}
					}
					/* If this entity is NOT a "just spawned entity" (and still alive), continue to update it as normal */
					else if (e.getState() == EntityState.kActive)
					{
						e.update();
					}
				}
			}
			
			
			/** Removes all of the dead entities from the game */
			public void checkDeadEntities ()
			{
				for (Entity e : mGameEntities)
				{
					if (e.getState() == EntityState.kDead)
					{
						mDeadEntities.add(e);
						
						/* Dec. number of enemies if an enemy just died */
						if (e.getType() == EntityType.kEnemy)
						{
							--mNumEnemies;
						}
					}
				}
				
				/* Removes all dead game entities */
				if (!mDeadEntities.isEmpty())
				{
					try
					{
						// System.out.println("Removing " + mDeadEntities.size() + " entities from the game...");
						removeChildren (mDeadEntities);
						mDeadEntities.clear();
					}
					
					catch (IllegalArgumentException e)
					{
						// Does nothing (for now)
					}
				}
			}
			
			/** Checks if all enemies in the game is ready to attack */
			private boolean canAttack ()
			{
				boolean result = true;
				/* First check if the attack wave timer has expired */
				if (mAttackWaveTimer > 0 || mNumEnemies <= 0)
				{
					--mAttackWaveTimer;
					return false;
				}
				
				/* If the current game state is not running, then return false */
				if (mGameState != GameState.kGameRunning)
				{
					return false;
				}
				
				/* Enemies can attack once all of their phases are currently
				 * in the idle phase */
				for (Entity e : mGameEntities)
				{
					if (e.getType() == EntityType.kEnemy)
					{
						Enemy enemy = (Enemy) e;
						result &= (enemy.getPhase() != EnemyPhase.kSpawning);
					}
				}
				
				if (result)
				{
					mAttackWaveTimer = mAttackWaveTime;
				}
				
				return result;
			}
			
			/** @return the set of enemies who are in the desginated attack group */
			public ArrayList <Enemy> getEnemies (int attackGroup)
			{
				ArrayList <Enemy> found = new ArrayList <Enemy>();
				
				for (Entity e : mGameEntities)
				{
					if (e.getType() == EntityType.kEnemy && e.getState() == EntityState.kActive)
					{
						Enemy enemy = (Enemy) e;
						
						if (enemy.getAttackGroup() == attackGroup)
						{
							found.add(enemy);
						}
					}
				}
				
				return found;
			}
			
			public void checkGameStatus()
			{
				/* Checks if a prompt is not currently being played */
				if (!mPromptFlag)
				{
					/* Checks if the player has died */
					if (mPlayer.getState() == EntityState.kPlayerDead)
					{
						--mCurrentLives;
						mGameState = GameState.kRespawning;
						mPromptTimer.playFrom(Duration.seconds(0));
						mPromptFlag = true;
					}
					
					if (mPromptFlag)
					{
						mGameView.getGameUI().showPromptText(mGameState);
					}
				}
			}
		};
		
		/* Sets up the prompt animation timer */
		mPromptTimer = new PauseTransition (Duration.seconds(mPromptTime));
		
		mPromptTimer.setOnFinished(e -> 
		{
			/* Once the prompt timer ends, perform an action */
			switch (mGameState)
			{
				case kGameStart:
				{
					mGameState = GameState.kGameRunning;
					spawnEnemies();
					mGameLoop.start();
					mPlayer.respawn();
					break;
				}
				case kNewLevel:
				{
					mGameState = GameState.kGameRunning;
					spawnEnemies();
					break;
				}
				case kRespawning:
				{
					mGameState = GameState.kGameRunning;
					mAttackWaveTimer = mAttackWaveTime;
					mPlayer.respawn();
					break;
				}
				case kLevelEnd:
				{
					++mCurrentLevel;
					mGameState = GameState.kNewLevel;
					break;
				}
				case kGameOver:
				{
					// TODO: Switch back to main menu
					break;
				}
				default:
				{
					break;
				}
			}
			
			mGameView.getGameUI().showPromptText(mGameState);
			mPromptFlag = false;
		});
	}
	
	/** Starts a new level */
	public void startLevel()
	{
		/* TODO: Ensure that the player is only spawned once throughout this game */
		if (!spawnedPlayerFlag)
		{
			spawnedPlayerFlag = true;
			addChild (mPlayer);
			/* Hides the player for now */
			mPlayer.setState(EntityState.kPlayerDead);
			mPlayer.setOpacity(0.0);
		}
		mGameView.getGameUI().showPromptText(mGameState);
		mPromptTimer.playFrom(Duration.seconds(0));
		mPromptFlag = true;
	}
	
	/** Queues an entity into the game, which would be added before the next tick starts */
	public void queueEntity (Entity e)
	{
		mQueuedEntities.add(e);
	}
	
	/** Reads a level data file from a text file and spawns in
	 *  the appropriate enemies */
	public void spawnEnemies ()
	{
		/* Defines the current iterators for enemy placement */
		double currentX, currentY;
		double xSpacing = 50.0;
		double ySpacing = 40.0;
		
		/* Curent group of the current entity */
		int currentGroup = 0;
		int currentGroupCount = 0;
		int currentGroupLimit = 1;
		
		/* Width and height of the container */
		int armyWidth = 0;
		/* Vector of strings used to copy the contents of the text file */
		ArrayList <String> contents = new ArrayList <String>();
		/* A vector of enemies would be spawned in later */
		ArrayList <Entity> enemyContainer = new ArrayList <Entity> ();
		
		/* Reads from a text file and uses that data to spawn enemies into the game */
		String fileName = "src/assets/data/testEnemyLayout.txt";
		String currentLine = null;
		try
		{
			FileReader reader = new FileReader (fileName);
			BufferedReader br = new BufferedReader (reader);
			
			/* First calculate the dimensions of the enemy army */
			while ((currentLine = br.readLine()) != null)
			{
				currentLine = currentLine.trim();
				
				for (int i = 0; i < currentLine.length(); ++i)
				{
					if (armyWidth < (i + 1))
					{
						armyWidth = (i + 1);
					}
				}
				contents.add(currentLine);
			}
			br.close();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		/* Now, construct the enemies */
		currentX = this.mLeftBorder + xSpacing;
		currentY = this.mLeftBorder + ySpacing;
		
		char currentChar;
		EnemyPosition currentPosition;
		for (String line : contents)
		{
			for (int i = 0; i < line.length(); ++i)
			{
				currentChar = line.charAt(i);
				
				/* Calculates the initial spawn point based on currentX */
				if (currentX <= (Launcher.mWidth / 2.0))
				{
					currentPosition = EnemyPosition.kLeft;
				}
				else
				{
					currentPosition = EnemyPosition.kRight;
				}
				switch (currentChar)
				{
					/* Spawn test enemy */
					case 'T':
						enemyContainer.add(new TestEnemy (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;
					/* Spawn Bohrbug */
					case 'B':
						enemyContainer.add(new Bohrbug (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;
					/* Spawns heisenbug */
					case 'H':
						enemyContainer.add(new Heisenbug (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;
					/* Spawns mandelbug */
					case 'M':
						enemyContainer.add(new Mandelbug (currentPosition, new Point2D (currentX, currentY), currentGroup, this));
						break;	
					default:
						break;
				}
				++currentGroupCount;
				if (currentGroupCount >= currentGroupLimit)
				{
					++currentGroup;
					currentGroupCount = 0;
				}
				
				currentX += xSpacing;
			}
			++currentGroupLimit;
			currentX = this.mLeftBorder + xSpacing;
			currentY += ySpacing;
		}
		
		addChildren (enemyContainer);
		mNumEnemies = enemyContainer.size();
		mMaxGroups = currentGroup + 1;
	}
	
	/** Adds a new entity into the game.
	 * 		@param child - The new entity being added */
	public void addChild (Entity child)
	{
		mGameEntities.add(child);
		mGameView.addChild(child);
	}
	
	/** Adds an arraylist of entities into the game
	 * 		@param children - The container of children being added */
	public void addChildren (ArrayList <Entity> children)
	{
		mGameEntities.addAll(children);
		mGameView.addChildren(children);
	}
	
	public void removeChildren (ArrayList<Entity> children)
	{
		mGameEntities.removeAll(children);
		mGameView.removeChildren(children);
	}
	
	public void removeChild (Entity child)
	{
		mGameEntities.remove(child);
		mGameView.removeChild(child);
	}
	
	/** Returns the center region of the playing field */
	public int getCenterRegion()
	{
		return (mMaxWidth / 2) + mLeftBorder;
	}

	/** @return the game's max. border */
	public int getMaxHeight() 
	{
		return mMaxHeight;
	}

	/** @return the game's right border */
	public int getRightBorder() 
	{
		return mRightBorder;
	}
	
	/** @return the game's left border */
	public int getLeftBorder()
	{
		return mLeftBorder;
	}

	/**
	 * @return the mCurrentLives
	 */
	public int getCurrentLives() 
	{
		return mCurrentLives;
	}

	/**
	 * @param mCurrentLives the mCurrentLives to set
	 */
	public void setCurrentLives(int mCurrentLives) 
	{
		this.mCurrentLives = mCurrentLives;
	}

	/**
	 * @return the mPlayer
	 */
	public Player getPlayer() 
	{
		return mPlayer;
	}

	/**
	 * @param mPlayer the mPlayer to set
	 */
	public void setPlayer(Player mPlayer) 
	{
		this.mPlayer = mPlayer;
	}

	/**
	 * @return the mCurrentLevel
	 */
	public int getCurrentLevel() 
	{
		return mCurrentLevel;
	}

	/**
	 * @param mCurrentLevel the mCurrentLevel to set
	 */
	public void setCurrentLevel(int mCurrentLevel) 
	{
		this.mCurrentLevel = mCurrentLevel;
	}

	/**
	 * @return the mPlayerScore
	 */
	public long getPlayerScore() 
	{
		return mPlayerScore;
	}

	/**
	 * @param mPlayerScore the mPlayerScore to set
	 */
	public void setPlayerScore(long mPlayerScore) 
	{
		this.mPlayerScore = mPlayerScore;
	}
	
	/** Sets the game's view component to a new view
	 * 		@param gameView - The new gameView component */
	public void setGameView (GameView gameView)
	{
		mGameView = gameView;
	}
	
	/** @return the game's view component */
	public GameView getGameView ()
	{
		return mGameView;
	}
	
	/** @return the ArrayList of entities currently in this game */
	public ArrayList <Entity> getEntities()
	{
		return this.mGameEntities;
	}
	
	/** @return the number of enemies on the game world */
	public int getNumEnemies ()
	{
		return this.mNumEnemies;
	}
	
	/** @return the current state of the game */
	public GameState getGameState()
	{
		return mGameState;
	}
	
	/** @returns the centerized player coordinates */
	public Point2D getPlayerCenterCoordinates()
	{
		return new Point2D (Launcher.mWidth / 2, Launcher.mHeight - mPlayerYOffset);
	}
	
}
