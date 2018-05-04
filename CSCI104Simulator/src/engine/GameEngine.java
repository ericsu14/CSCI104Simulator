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
import entities.boss.CoteBoss;
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
	/* The enemy's unguided projectile */
	public ImageView mEnemyLaser;
	/* The sprite that represents a damaged variant of the mandel bug */
	public ImageView mDamagedBugSprite;
	/* Binary trees */
	public ImageView mBinaryTree;
	
	/* MEMBER VARIABLES */
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
	/* The value that the attack wave timer is initially set to */
	private int mInitAttackWaveTime;
	/* Timer for attack waves */
	private int mAttackWaveTimer;
	/* The number of enemies on the game world */
	private int mNumEnemies;
	/* Number of bosses in this game world */
	private int mNumBosses;
	/* Y offset that dictates where the player would be fixed to */
	private int mPlayerYOffset = 100;
	/* The current state of the game */
	private GameState mGameState;
	/* Pause transition used to display a prompt to the player 
	 * before the game or new level starts */
	private PauseTransition mPromptTimer;
	/* Time it takes for the pause transition to display the prompt before starting the game */
	private int mPromptTime = 4;
	/* Flag that determines if the prompt is currently being displayed */
	private boolean mPromptFlag = false;
	/* Keeps track of the amount of extra lives given */
	private int mExtraLivesGiven = 0;
	/* The score required to gain an extra life bonus */
	private long mExtraLifeScore = 200000;
	/* True if the game is about to end */
	private boolean isGameOver = false;
	/* True if this game is set to hard mode. Thus,
	 * the world's progressional difficulity would be set to the maximum */
	private boolean mHardModeFlag = false;
	
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
		mBinaryTree = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/binaryTree.png")));
		mCote = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/cote.png")));
		mBook = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/theBook.jpeg")));
		mEnemyLaser = new ImageView  (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/enemyLaser.png")));
		mDamagedBugSprite = new ImageView  (new Image (getClass().getClassLoader().getResourceAsStream("assets/img/damagedBug.png")));
		
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
		mInitAttackWaveTime = mAttackWaveTime;
		mAttackWaveTimer = 0;
		
		/* Sets up the game's borders */
		mMaxWidth = (int)Launcher.mWidth;
		mLeftBorder = 50;
		mRightBorder = mMaxWidth - mLeftBorder - 24;
		
		mPlayer = new Player (Launcher.mWidth / 2, Launcher.mHeight - mPlayerYOffset, this);
		
		/* Sets up the game loop animation timer */
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
				/* If the game is just restarting from a prev. ended
				 * game, clean up its previous state. */
				if (isGameOver)
				{
					cleanup();
					isGameOver = false;
				}
				
				update (now);
				checkDeadEntities();
				checkGameStatus();
				
				/* Extra life bonus code */
				if (mPlayerScore / mExtraLifeScore > mExtraLivesGiven)
				{
					++mCurrentLives;
					++mExtraLivesGiven;
					// TODO: Play a sound when awarded an extra life
				}
				
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
							if (e.getPhase() != EnemyPhase.kAttack)
							{
								e.createAttackVectors();
							}
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
						if (e.getType() == EntityType.kBoss)
						{
							--mNumBosses;
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
						/* Attempts to remove children again */
						removeChildren (mDeadEntities);
						mDeadEntities.clear();
					}
				}
			}
			
			/** Checks if all enemies in the game is ready to attack */
			private boolean canAttack ()
			{
				boolean result = true;
				
				/* If the current game state is not running, then return false */
				if (mGameState != GameState.kGameRunning)
				{
					return false;
				}
				
				/* First check if the attack wave timer has expired */
				if (mAttackWaveTimer > 0 || mNumEnemies <= 0)
				{
					--mAttackWaveTimer;
					return false;
				}
				
				
				/* Enemies can attack once all of their phases are currently
				 * in the idle phase */
				int idleEnemiesCount = 0;
				for (Entity e : mGameEntities)
				{
					if (e.getType() == EntityType.kEnemy)
					{
						Enemy enemy = (Enemy) e;
						result &= (enemy.getPhase() != EnemyPhase.kSpawning);
						if (enemy.getPhase() != EnemyPhase.kSpawning)
						{
							++idleEnemiesCount;
						}
					}
				}
				
				/* If roughly 95% of all spawned entities are in place,
				 * enemies can start attacking */
				double canSpawnThreshold = (mNumEnemies * 0.95);
				
				if (idleEnemiesCount >= (int) canSpawnThreshold)
				{
					result = true;
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
						/* Checks if the player still has n remaining lives */
						if (mCurrentLives > 0)
						{
							mGameState = GameState.kRespawning;
						}
						else
						{
							mGameState = GameState.kGameOver;
						}
						
						mPromptTimer.playFrom(Duration.seconds(0));
						mPromptFlag = true;
					}
					
					/* Checks if there are no more enemies on the game.
					 * Thus, we can advance to the next level */
					else if (mNumEnemies <= 0 && mNumBosses <= 0)
					{
						mGameState = GameState.kLevelEnd;
						mPromptTimer.playFrom(Duration.seconds(0));
						mPromptFlag = true;
						/* Starts small fireworks show */
						mGameView.getStarField().setFireworksFlag(true);
					}
					
					if (mPromptFlag)
					{
						mGameView.getGameUI().showPromptText(mGameState);
					}
				}
			}
			
			/** Cleans up all internal variables */
			private void cleanup ()
			{
				this.mFocusedEnemy = null;
				this.mSpawnIntervals = 0;
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
					adjustGlobalDifficulty();
					
					mGameState = GameState.kGameRunning;
					spawnEnemies();
					mGameLoop.start();
					mPlayer.respawn();
					break;
				}
				case kGameBriefing:
				{
					mGameState = GameState.kGameStart;
					mPromptTimer.playFrom(Duration.seconds(0));
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
					mAttackWaveTimer = mAttackWaveTime / 3;
					mPlayer.respawn();
					break;
				}
				case kLevelEnd:
				{
					++mCurrentLevel;
					mNumBosses = 0;
					adjustGlobalDifficulty();
					
					mGameState = GameState.kNewLevel;
					mPromptTimer.playFrom(Duration.seconds(0));
					/* Disable small fireworks show */
					mGameView.getStarField().setFireworksFlag(false);
					break;
				}
				case kGameOver:
				{
					mGameLoop.stop();
					cleanup();
					spawnedPlayerFlag = false;
					mExtraLivesGiven = 0;
					isGameOver = true;
					mGameView.getLauncher().switchMainMenu();
					break;
				}
				default:
				{
					break;
				}
			}
			
			mGameView.getGameUI().showPromptText(mGameState);
			
			if (mGameState != GameState.kNewLevel)
			{
				mPromptFlag = false;
			}
		});
	}
	
	/** Starts a new game */
	public void startGame()
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
		mGameState = GameState.kGameBriefing;
		mGameView.getGameUI().showPromptText(mGameState);
		mGameView.getGameUI().update();
		mPromptTimer.playFrom(Duration.seconds(0));
		mExtraLivesGiven = 0;
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
		String fileName = getSpawnLayout();
		String currentLine = null;
		try
		{
			FileReader reader = new FileReader (fileName);
			BufferedReader br = new BufferedReader (reader);
			
			/* First calculate the dimensions of the enemy army */
			while ((currentLine = br.readLine()) != null)
			{
				// currentLine = currentLine.trim();
				
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
					/* Spawns the test boss */
					case 'Z':
						enemyContainer.add(new CoteBoss (currentPosition, new Point2D (currentX, currentY), this));
						++mNumBosses;
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
		mNumEnemies = enemyContainer.size() - mNumBosses;
		mMaxGroups = currentGroup + 1;
	}
	
	/** @return the directory of the enemy spawn layout depending on
	 *  the player's current level.
	 *  
	 *  Each spawn layout level indicates the overall difficulity of this
	 *  setup, from lowest to highest. */
	public String getSpawnLayout ()
	{
		/* Checks if the level is a multiple of ten. If
		 * so, do the boss battle */
		if (this.mCurrentLevel % 10 == 0)
		{
			// TODO: Tell the view that a boss battle is going to occur
			// and do the things such as prompt / music change
			return "src/assets/data/bossLayout.txt";
		}
		if (inRange (this.mCurrentLevel, 1, 2))
		{
			return "src/assets/data/layout1.txt";
		}
		else if (inRange (this.mCurrentLevel, 3, 7))
		{
			return "src/assets/data/layout2.txt";
		}
		
		else if (inRange  (this.mCurrentLevel, 8, 9))
		{
			return "src/assets/data/layout3.txt";
		}
		else if (inRange (this.mCurrentLevel, 10, 20))
		{
			return "src/assets/data/layout4.txt";
		}
		else if (inRange (this.mCurrentLevel, 21, 30))
		{
			return "src/assets/data/layout5.txt";
		}
		else if (inRange (this.mCurrentLevel, 31, 40))
		{
			return "src/assets/data/layout6.txt";
		}
		else
		{
			return "src/assets/data/layout7.txt";
		}
		
	}
	
	/** Adjusts variables of the game's global settings based on the
	 *  level the player is currently in */
	public void adjustGlobalDifficulty()
	{
		double attackWaveThreshold = this.mInitAttackWaveTime - (this.mInitAttackWaveTime * 0.40);
		/* Adjusts the attack group timer to scale with current
		 * difficulity level. This timer decreases by 1% each level passed, at
		 * a maximum of 40%. */
		double changeOfTime = this.mInitAttackWaveTime * (double)((mCurrentLevel - 1) / 100.0);
		this.mAttackWaveTime = (int)(this.mInitAttackWaveTime - (int)changeOfTime);
		if (this.mAttackWaveTime < (int)attackWaveThreshold || this.isHardMode())
		{
			this.mAttackWaveTime = (int)attackWaveThreshold;
		}
	}
	
	/** Cleans up assets */
	public void cleanup ()
	{		
		/* Cleans up all containers */
		mDeadEntities.clear();
		mGameEntities.clear();
		mQueuedEntities.clear();
		
		mGameView.cleanup();
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
	
	/** Sets the player's current score */
	public void setCurrentScore (long score)
	{
		this.mPlayerScore = score;
	}
	
	/** Sets the difficulty to the game to either normal or hard */
	public void setHardMode (boolean mode)
	{
		this.mHardModeFlag = mode;
	}
	
	/** @return true if the game's difficulty is set to hard mode */
	public boolean isHardMode ()
	{
		return this.mHardModeFlag;
	}
	
	/** @return True if target is between min and max
	 * 		@param target - Value being compared
	 * 		@param min - Min. value of selected range
	 * 		@param max - Max. value of selected range */
	protected boolean inRange(int target, int min, int max)
	{
		return (min <= target && target <= max);
	}
}
