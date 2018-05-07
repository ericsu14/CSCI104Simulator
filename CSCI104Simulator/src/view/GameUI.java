package view;

import engine.GameState;
import factories.ShindlerFactory;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.CSSConstants;

public class GameUI extends StackPane
{
	/* A reference to the game's GameView */
	private GameView mGameView;
	/* A label displaying the score */
	private Label mScoreLabel;
	/* A label displaying the player's current score */
	private Label mCurrentScore;
	/* A horizontal box displaying the amount of lives the player currently has */
	private HBox mCurrentLives;
	/* A label showing the level the player is currently in */
	private Label mCurrentLevel;
	/* A container for the game's prompt screen  */
	private VBox mPromptContainer;
	/* The text that goes into the prompt */
	private Label mPromptText;
	/* A second layer of text that goes into the prompt, if needed */
	private Label mPromptText2;
	/* Keeps track of the current lives remaining */
	private int mLivesRemaining;
	/* Text that displays game notifications */
	private Label mNotificationText;
	/* The amount of time a notification is displayed */
	private int mNotificationTime = 60;
	/* Animation timer for displaying notifications */
	private AnimationTimer mNotifications = null;
	/* True if a prev. notification timer is playing. If so, then the new notification
	 * would overwrite it. */
	private boolean mDisplayingNotification = false;
	
	public GameUI (GameView gameView)
	{
		super();
		
		mGameView = gameView;
		setupScene();
	}
	
	/** Sets up the UI components */
	public void setupScene ()
	{	
		BorderPane topCenter = new BorderPane();
		
		/* Top center header */
		VBox topCenterHeader = new VBox();
		mScoreLabel = new Label ("CURRENT SCORE");
		mScoreLabel.setStyle(CSSConstants.GAME_FONT);
		mCurrentScore = new Label ("" + mGameView.getEngine().getPlayerScore());
		mCurrentScore.setStyle(CSSConstants.GAME_FONT);
		mNotificationText = new Label ("");
		mNotificationText.setStyle(CSSConstants.GAME_FONT_UNCOLORED + CSSConstants.YELLOW_TEXT);
		
		
		topCenterHeader.getChildren().addAll(mScoreLabel, mCurrentScore, mNotificationText);
		topCenterHeader.setSpacing(4);
		topCenterHeader.setAlignment(Pos.TOP_CENTER);
		
		topCenter.setTop(topCenterHeader);
		
		/* Top left header */
		BorderPane topLeft = new BorderPane();
		VBox topLeftHeader = new VBox();
		Label labelPrompt = new Label ("LEVEL");
		labelPrompt.setStyle(CSSConstants.GAME_FONT);
		mCurrentLevel = new Label (mGameView.getEngine().getCurrentLevel() + "");
		mCurrentLevel.setStyle(CSSConstants.GAME_FONT);
		topLeftHeader.getChildren().addAll(labelPrompt, mCurrentLevel);
		topLeftHeader.setAlignment(Pos.TOP_LEFT);
		topLeftHeader.setPadding(new Insets (0, 0, 0, 10));
		topLeft.setTop(topLeftHeader);
		
		/* Center prompt text */
		mPromptContainer = new VBox();
		mPromptText = new Label ("");
		mPromptText.setStyle(CSSConstants.GAME_FONT);
		mPromptText2 = new Label ("");
		mPromptText2.setStyle (CSSConstants.GAME_FONT);
		mPromptContainer.getChildren().addAll(mPromptText, mPromptText2);
		mPromptContainer.setAlignment(Pos.CENTER);
		mPromptContainer.setSpacing(4);
		topLeft.setCenter(mPromptContainer);
		
		/* TODO: setup lives counter */
		VBox bottomLeftFooter = new VBox();
		mCurrentLives = new HBox();
		renderLives ();
		mCurrentLives.setAlignment(Pos.BOTTOM_LEFT);
		mCurrentLives.setPadding(new Insets (0, 0, 10, 10));
		mCurrentLives.setSpacing(6);
		bottomLeftFooter.getChildren().addAll(mCurrentLives);
		bottomLeftFooter.setAlignment(Pos.BOTTOM_LEFT);
		topLeft.setBottom(bottomLeftFooter);
		
		this.getChildren().addAll(topCenter, mPromptContainer, topLeft);
	}
	
	/** Updates the UI based on the current state of the game */
	public void update()
	{
		mCurrentScore.setText("" + mGameView.getEngine().getPlayerScore());
		mCurrentLevel.setText("" + mGameView.getEngine().getCurrentLevel());
		renderLives();
	}
	
	/** Renders the lives container */
	public void renderLives ()
	{
		/* Only update if there is a change on the amount of lives left */
		if (mLivesRemaining != mGameView.getEngine().getCurrentLives())
		{
			mLivesRemaining = mGameView.getEngine().getCurrentLives();
			
			/* Removes any previous added lives */
			getChildren().removeAll(mCurrentLives.getChildren());
			mCurrentLives.getChildren().clear();
			
			/* Recalculates the amount of lives the player has */
			for (int i = 0;  i < mLivesRemaining - 1; ++i)
			{
				ImageView playerIcon = createPlayerIcon();
				mCurrentLives.getChildren().add(playerIcon);
			}
		}
	}
	
	/** Displays the promt text depending on the current status of the game */
	public void showPromptText(GameState state)
	{
		switch (state)
		{
			case kGameStart:
				mPromptText.setText("GET READY");
				mPromptText2.setText("");
				break;
			case kNewLevel:
				mPromptText.setText("LEVEL " + mGameView.getEngine().getCurrentLevel());
				mPromptText2.setText("");
				break;
			case kRespawning:
				mPromptText.setText("segmentation fault (core dumped)");
				mPromptText2.setText("");
				break;
			case kGameOver:
				mPromptText.setText("Kernel panic!");
				mPromptText2.setText("Game Over");
				break;
			case kLevelEnd:
				mPromptText.setText(ShindlerFactory.getJokeOfTheDay() + "\n\t- Michael Shindler");
				mPromptText2.setText("");
				break;
			case kGameBriefing:
				mPromptText.setText("Use the left/right arrow keys to move,");
				mPromptText2.setText("space bar to shoot.");
				break;
			default:
				mPromptText.setText("");
				mPromptText2.setText("");
				break;
		}
	}
	
	/** Briefly displays a notification on the screen
	 * 		@param text - The type of notification to be displayed */
	public void showNotification (String text)
	{
	
		if (mNotifications != null && mDisplayingNotification)
		{
			mNotifications.stop();
			mNotificationText.setText("");
		}
		
		mDisplayingNotification = true;
		mNotifications = new AnimationTimer ()
		{
			/* Current lifetime of notification */
			private int mNotificationTimer = mNotificationTime;
			/* The amount of cycles the notification is active, where each cycle happens once the
			 * text flashes from on to off and off to on. */
			private int mMaxCycles = 3;
			/* Current amount of cycles */
			private int mCurrentCycles = 0;
			/* Current state */
			private boolean mState = true;
			
			/* Displays text first */
			{
				mNotificationText.setText(text);
			}
			
			@Override
			public void handle(long arg0) 
			{
				/* If the timer expired, switch states and update cycles */
				if (mNotificationTimer <= 0)
				{
					mNotificationTimer = mNotificationTime;
					
					if (mState)
					{
						mNotificationText.setText("");
					}
					else
					{
						mNotificationText.setText(text);
						++mCurrentCycles;
					}
					mState= !mState;
					
					if (mCurrentCycles >= mMaxCycles)
					{
						this.stop();
						mDisplayingNotification = false;
						mNotificationText.setText("");
					}
				}
				else
				{
					--mNotificationTimer;
				}
			}
			
		};
		
		mNotifications.start();
	}
	
	/** Constructs a new playership icon used for rendering the amount of
	 *  lives a player has left */
	private ImageView createPlayerIcon ()
	{
		double iconScale = 20.0;
		
		ImageView icon = new ImageView();
		icon.setImage(mGameView.getEngine().mPlayerShipSprite.getImage());
		icon.setFitWidth (iconScale);
		icon.setFitHeight(iconScale);
		
		return icon;
	}
}
