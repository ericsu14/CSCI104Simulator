package media;

public enum MusicType 
{
	kNull (MusicStyle.kNone, ""),
	kBossMusic1 (MusicStyle.kBoss, "BossBGM1.mp3"),
	kGameOver (MusicStyle.kGameOver, "GameOver.mp3"),
	kMenuMusic1 (MusicStyle.kMenu, "MenuMusic1.mp3"),
	kGameBGM1 (MusicStyle.kGame, "GameBGM1.mp3"),
	kGameBGM2 (MusicStyle.kGame, "GameBGM2.mp3"),
	kGameBGM3 (MusicStyle.kGame, "GameBGM3.mp3"),
	kGameBGM4 (MusicStyle.kGame, "GameBGM4.mp3");
	
	private MusicStyle mStyle;
	private String mDirectory;
	private final String mRootURL = "src/assets/BGM/";
	
	MusicType (MusicStyle style, String source)
	{
		mStyle = style;
		mDirectory = mRootURL + source;
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
