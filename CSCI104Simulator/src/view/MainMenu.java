/** Represents the main menu of the game */
package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu 
{
	/** Stackpane used to represent the main menu */
	private StackPane mRoot;
	
	/** Scene used to store all components tied to the main menu */
	private Scene mScene;
	
	/** Starfield scene */
	private StarField mStarField;
	
	public MainMenu ()
	{
		initializeMainMenu();
		mStarField.playAnimation();
	}
	
	/** Returns a reference to the main menu's root node */
	public StackPane getRoot ()
	{
		return mRoot;
	}
	
	/** Cleans up all dynamically allocated assets from this scene */
	public void cleanUp ()
	{
		// TODO:
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
		Label gameTitle = new Label (Launcher.mGameTitle);
		gameTitle.setFont(new Font ("Comic Sans MS", 36));
		gameTitle.setStyle("-fx-text-fill: white");
		
		header.getChildren().addAll(gameTitle);
		header.setAlignment(Pos.TOP_CENTER);
		mainMenu.setTop(header);
		
		/* TODO: Create selectors for the rest of the gameplay components */
		
		mRoot.getChildren().add(mainMenu);
		mRoot.setStyle("-fx-background-color: black");
		mScene = new Scene (mRoot);
	}
	
}
