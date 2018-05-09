package media;

public enum MusicType 
{
	kNull (MusicStyle.kNone, ""),
	kBossMusic1 (MusicStyle.kBoss, "src/assets/BGM/BossBGM1.mp3"),
	kGameOver (MusicStyle.kGameOver, "src/assets/BGM/GameOver.mp3"),
	kMenuMusic1 (MusicStyle.kMenu, "src/assets/BGM/MenuMusic1.mp3"),
	kGameBGM1 (MusicStyle.kGame, "src/assets/BGM/GameBGM1.mp3"),
	kGameBGM2 (MusicStyle.kGame, "src/assets/BGM/GameBGM2.mp3");
	
	private MusicStyle mStyle;
	private String mDirectory;
	
	MusicType (MusicStyle style, String source)
	{
		mStyle = style;
		mDirectory = source;
	}
	
	public MusicStyle getStyle()
	{
		return mStyle;
	}
	
	public String getDirectory ()
	{
		return mDirectory;
	}
}
