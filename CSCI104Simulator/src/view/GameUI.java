package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
	/* A vertical box displaying the amount of lives the player currently has */
	private HBox mCurrentLives;
	/* A label showing the level the player is currently in */
	private Label mCurrentLevel;
	
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
		
		/* TODO: setup lives counter */
		
		this.getChildren().addAll(topCenter, topLeft);
	}
	
	/** Updates the UI based on the current state of the game */
	public void update()
	{
		mCurrentScore.setText("" + mGameView.getEngine().getPlayerScore());
		mCurrentLevel.setText("" + mGameView.getEngine().getCurrentLevel());
	}
}
