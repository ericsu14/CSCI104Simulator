package view;

import engine.GameState;
import factories.ShindlerFactory;
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
		
		topCenterHeader.getChildren().addAll(mScoreLabel, mCurrentScore);
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
