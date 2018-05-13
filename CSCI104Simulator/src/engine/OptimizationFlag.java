package engine;

public enum OptimizationFlag 
{
	kPerformance ("Fast", "Optimizes all of the game's firework / explosion animations to use\nroughly 50% less resources, making both gameplay"
			+ " and transitions\nbetween levels smoother. However, the quality of the fireworks will suffer."), 
	kDefault ("Default", "Only optimize the in-game explosion animations for peformance.\nThe quality of the fireworks that appear on every end of level sequence\nwould still be retained."), 
	kQuality ("Pretty", "Retains the original quality of the game's firework / explosion animations.\nHowever, gameplay peformance may suffer (especially on lower end computers).");
	
	private String mName;
	private String mDescription;
	
	OptimizationFlag (String name, String description)
	{
		mName = name;
		mDescription = description;
	}
	
	public String getName ()
	{
		return mName;
	}
	
	public String getDescription ()
	{
		return mDescription;
	}
}
