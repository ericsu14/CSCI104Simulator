package entities.sprites;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Sprite {
	
	kTestEnemy ("testEnemy.png"),
	kPlayerShip ("playerShip.png"),
	kBohrBug ("testEnemy.png"),
	kHeisenBug ("heisenbug.png"),
	kMandelBug ("mandelBug.png"),
	kPlayerBullet ("playerBullet.png"),
	kLinkedList ("linkedList.png"),
	kBinaryTree ("binaryTree.png"),
	kCote ("cote.png"),
	KBook ("theBook.jpeg"),
	kEnemyLaser ("enemyLaser.png"),
	kDamagedBug ("damagedBug.png"),
	kSpeghetti ("speghetti.png"),
	kArmando ("armando.png"),
	kTheFish ("theFish.png");
	
	/* The name of the image file */
	private String mSource;
	/* The image-view object associated with this sprite */
	private ImageView mImageView;
	/* The root directory where this asset file is located */
	private final String mRootURL = "resources\\img\\";
	
	Sprite (String source)
	{
		mSource = mRootURL + source;
		System.out.println("Source: " + mSource);

		File f = new File (this.mSource);
		mImageView = new ImageView (new Image (f.toURI().toString()));
	}
	
	public String getSource ()
	{
		return mSource;
	}
	
	public ImageView getImageView()
	{
		return mImageView;
	}
}
