package media;

public enum SoundType 
{
	kNull (""),
	kPlayerShoot ("src/assets/sounds/playerShoot.mp3"), 
	kUnguidedProjectile ("src/assets/sounds/unguidedProjectile.wav"),
	kEnemyExplode ("src/assets/sounds/enemyExplosion.wav"),
	kEnemyHit ("src/assets/sounds/enemyHit.wav"),
	kPlayerExplode ("src/assets/sounds/playerExplosion.wav"),
	kEnemyPrepare ("src/assets/sounds/enemyPrepare.mp3"),
	kShieldHit ("src/assets/sounds/shieldHit.wav"),
	kShieldDie ("src/assets/sounds/shieldDie.wav"),
	kEnemyDie ("src/assets/sounds/enemyDie.wav"),
	kEnemyDie2 ("src/assets/sounds/enemyDie2.wav"),
	kExtraLife ("src/assets/sounds/extraLife.mp3"),
	kFireworkBlast ("src/assets/sounds/fireworkBlast.wav");
	
	private String mDirectory;
	
	/** Constructs a new sound type with a passed in directory */
	SoundType (String dir)
	{
		mDirectory = dir;
	}
	
	/** @return the directory where this sound file is located */
	String getDirectory()
	{
		return mDirectory;
	}
}
