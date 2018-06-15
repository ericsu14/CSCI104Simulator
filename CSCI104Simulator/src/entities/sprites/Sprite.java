package entities.sprites;

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
	kSpeghetti ("speghetti.png");
	
	private String mSource;
	private ImageView mImageView;
	private final String mRootURL = "assets/img/";
	
	Sprite (String source)
	{
		mSource = mRootURL + source;
		mImageView = new ImageView (new Image (getClass().getClassLoader().getResourceAsStream(mSource)));
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
