/** Represents the main menu of the game */
package view;

import engine.OptimizationFlag;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import media.MusicStyle;
import util.CSSConstants;

public class MainMenu 
{
	/* Stackpane used to represent the main menu */
	private StackPane mRoot;
	/* Scene used to store all components tied to the main menu */
	private Scene mScene;
	/* Starfield scene */
	private StarField mStarField;
	/* Reference to the launcher object */
	private Launcher mLauncher;
	/* Stores the current difficulty of the game as a string */
	private String mDifficultyString;
	/* Stores the current graphical quality of the game */
	private OptimizationFlag mGraphics = OptimizationFlag.kDefault;
	/* Flag used to determine if a test firework animation is playing */
	private boolean mIsTestingFireworks = false;
	/* Keeps track of the amount of active test animations playing */
	private int mActiveTestAnimations = 0;
	
	public MainMenu (Launcher launcher)
	{
		mLauncher = launcher;
		mDifficultyString = "Normal";
		initializeMainMenu();
		playAnimations();
	}
	
	/** Returns a reference to the main menu's root node */
	public StackPane getRoot ()
	{
		return mRoot;
	}
	
	/** Plays all of the animations for this scene */
	public void playAnimations ()
	{
		mStarField.playAnimation();
		mLauncher.getGameView().getSoundEngine().setPlaylist(MusicStyle.kMenu);
	}
	
	/** Cleans up all dynamically allocated assets from this scene */
	public void cleanUp ()
	{
		// TODO:
		mStarField.stopAnimation();
	}
	
	/** Returns the main menu's scene object */
	public Scene getScene ()
	{
		return mScene;
	}
	/** Initializes the main menu assets */
	private void initializeMainMenu ()
	{
		mRoot = new StackPane ();
		
		/* Sets the first background layer to a starfield animation  */
		mStarField = new StarField(true);
		mRoot.getChildren().add(mStarField);
		
		/* Main menu borderpane */
		BorderPane mainMenu = new BorderPane();
		
		/* Header */
		VBox header = new VBox();
		/* Title of the game */
		Label gameTitle = new Label (mLauncher.mGameTitle);
		gameTitle.setFont(new Font ("Comic Sans MS", 36));
		gameTitle.setStyle(CSSConstants.WHITE_TEXT);
		
		header.getChildren().addAll(gameTitle);
		header.setAlignment(Pos.TOP_CENTER);
		mainMenu.setTop(header);
		
		/* Main menu options */
		VBox menuOptions = new VBox ();
		
		/* Difficulty button */
		Button diffButton = new Button ("     Difficulty: " + mDifficultyString);
		diffButton.setFont(new Font ("Comic Sans MS", 18));
		diffButton.setBackground(Background.EMPTY);
		diffButton.setStyle(CSSConstants.WHITE_TEXT);
		
		diffButton.setOnMouseEntered(e -> 
		{
			diffButton.setText("--> Difficulty: " + mDifficultyString);
			if (mLauncher.getGameEngine().isHardMode())
			{
				diffButton.setStyle(CSSConstants.RED_TEXT + CSSConstants.UNDERLINE_TEXT);
			}
			else
			{
				diffButton.setStyle(CSSConstants.WHITE_TEXT + CSSConstants.UNDERLINE_TEXT);
			}
		});
		
		diffButton.setOnMouseExited(e -> 
		{
			diffButton.setText("     Difficulty: " + mDifficultyString);
			if (mLauncher.getGameEngine().isHardMode())
			{
				diffButton.setStyle(CSSConstants.RED_TEXT);
			}
			else
			{
				diffButton.setStyle(CSSConstants.WHITE_TEXT);
			}
		});
		
		diffButton.setOnAction(e -> 
		{
			mLauncher.getGameEngine().setHardMode(!mLauncher.getGameEngine().isHardMode());
			
			if (mLauncher.getGameEngine().isHardMode())
			{
				mDifficultyString = "Impossibru";
				diffButton.setStyle(CSSConstants.RED_TEXT + CSSConstants.UNDERLINE_TEXT);
			}
			else
			{
				mDifficultyString = "Normal";
				diffButton.setStyle(CSSConstants.WHITE_TEXT + CSSConstants.UNDERLINE_TEXT);
			}
			diffButton.setText("--> Difficulty: " + mDifficultyString);
		});
		
		/* Graphics quality button */
		Button graphicsButton = new Button ("     Animation Quality: " + mGraphics.getName());
		graphicsButton.setBackground (Background.EMPTY);
		graphicsButton.setStyle(CSSConstants.WHITE_TEXT);
		graphicsButton.setFont(new Font ("Comic Sans MS", 18));
		
		graphicsButton.setOnMouseEntered(e -> 
		{
			graphicsButton.setText("-->  Animation Quality: " + mGraphics.getName());
			graphicsButton.setStyle(CSSConstants.WHITE_TEXT + CSSConstants.UNDERLINE_TEXT);
		});
		
		graphicsButton.setOnMouseExited(e -> 
		{
			graphicsButton.setText("     Animation Quality: " + mGraphics.getName());
			graphicsButton.setStyle(CSSConstants.WHITE_TEXT);
		});
		
		graphicsButton.setOnMouseClicked(e -> 
		{
			/* Only switch quality when we are not testing the fireworks */
			if (mIsTestingFireworks)
			{
				return;
			}
			
			switch (mGraphics)
			{
				case kPerformance:
				{
					mGraphics = OptimizationFlag.kDefault;
					break;
				}
				case kQuality:
				{
					mGraphics = OptimizationFlag.kPerformance;
					break;
				}
				case kDefault:
				{
					mGraphics = OptimizationFlag.kQuality;
					break;
				}
			}
			
			mLauncher.getGameEngine().setOptimizationFlag(mGraphics);
			graphicsButton.setText("-->  Animation Quality: " + mGraphics.getName());
			graphicsButton.setStyle(CSSConstants.GRAY_TEXT + CSSConstants.UNDERLINE_TEXT);
			mIsTestingFireworks = true;
			
			graphicsButton.setDisable(mIsTestingFireworks);
			
			/* Spawns test fireworks */
			mStarField.setOptimizationFlag(mGraphics);
			PauseTransition fireworksSpawner = new PauseTransition (Duration.millis(500));
			fireworksSpawner.setOnFinished(a -> 
			{
				mStarField.spawnExplosion();
				mStarField.spawnFireworks();
				if (mActiveTestAnimations > 0)
				{
					mActiveTestAnimations--;
				}
				
				if (mActiveTestAnimations <= 0)
				{
					mIsTestingFireworks = false;
					graphicsButton.setDisable(mIsTestingFireworks);
				}
			});
			fireworksSpawner.play();
			mActiveTestAnimations++;
		});
		
		/* Play game button */
		Button playGame = new Button("     Play Game");
		playGame.setFont(new Font ("Comic Sans MS", 18));
		playGame.setBackground(Background.EMPTY);
		playGame.setStyle(CSSConstants.WHITE_TEXT);
		
		/* Button action properties */
		playGame.setOnMouseEntered(e -> 
		{
			playGame.setText("--> Play Game");
			playGame.setStyle(CSSConstants.WHITE_TEXT + CSSConstants.UNDERLINE_TEXT);
		});
		playGame.setOnMouseExited(e -> 
		{
			playGame.setText("     Play Game");
			playGame.setStyle(CSSConstants.WHITE_TEXT);
		});
		playGame.setOnAction(e -> 
		{
			cleanUp();
			mLauncher.switchGameScene();
		});
		
		menuOptions.getChildren().addAll(diffButton, graphicsButton, playGame);
		
		
		menuOptions.setAlignment(Pos.CENTER);
		menuOptions.setSpacing(6);
		mainMenu.setCenter(menuOptions);
		
		
		/* Footer */
		VBox footer = new VBox();
		Label author = new Label ("Created by Eric Su");
		author.setStyle(CSSConstants.AUTHOR_FONT);
		Label version = new Label ("V. 0.95a");
		version.setStyle(CSSConstants.AUTHOR_FONT);
		
		footer.getChildren().addAll(version, author);
		footer.setAlignment(Pos.BOTTOM_CENTER);
		
		mainMenu.setBottom(footer);
		
		mRoot.getChildren().add(mainMenu);
		mRoot.setStyle("-fx-background-color: black");
		mScene = new Scene (mRoot);
	}
	
}
