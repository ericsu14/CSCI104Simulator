/** Represents the main menu of the game */
package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import util.CSSConstants;

public class MainMenu 
{
	/** Stackpane used to represent the main menu */
	private StackPane mRoot;
	
	/** Scene used to store all components tied to the main menu */
	private Scene mScene;
	
	/** Starfield scene */
	private StarField mStarField;
	
	/** Reference to the launcher object */
	private Launcher mLauncher;
	
	public MainMenu (Launcher launcher)
	{
		mLauncher = launcher;
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
		mStarField = new StarField();
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
		
		/* Play game button */
		Button playGame = new Button("     Play Game");
		playGame.setFont(new Font ("Comic Sans MS", 24));
		playGame.setBackground(Background.EMPTY);
		playGame.setStyle(CSSConstants.WHITE_TEXT);
		
		/* Button action propetries */
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
			/* TODO: Switch to the game scene here */
			cleanUp();
			mLauncher.switchGameScene();
		});
		
		menuOptions.getChildren().add(playGame);
		
		
		menuOptions.setAlignment(Pos.CENTER);
		mainMenu.setCenter(menuOptions);
		
		/* TODO: Create selectors for the rest of the gameplay components */
		
		/* Footer */
		VBox footer = new VBox();
		Label author = new Label ("Created by Eric Su");
		author.setStyle(CSSConstants.AUTHOR_FONT);
		Label version = new Label ("V. 0.28a");
		version.setStyle(CSSConstants.AUTHOR_FONT);
		
		footer.getChildren().addAll(version, author);
		footer.setAlignment(Pos.BOTTOM_CENTER);
		
		mainMenu.setBottom(footer);
		
		mRoot.getChildren().add(mainMenu);
		mRoot.setStyle("-fx-background-color: black");
		mScene = new Scene (mRoot);
	}
	
}
