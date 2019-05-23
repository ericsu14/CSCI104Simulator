package factories;

import java.io.BufferedReader;
import java.io.FileReader;

public enum FireworkStyles 
{
	/** Note: ASCII art provided by
	 * 		- http://www.chris.com/ascii/joan/www.geocities.com/SoHo/7373/food.html */
	shindler ("shindler.txt"),
	party ("party.txt"),
	donutshop ("donutshop.txt"),
	apple ("apple.txt"),
	fireworks ("fireworks.txt"),
	disney ("disney.txt"), 
	fireworks2 ("fireworks2.txt"),
	fighton ("fighton.txt"),
	fightonsymbol ("fightonsymbol.txt"),
	USC ("USC.txt"),
	fireworks3 ("fireworks3.txt"),
	aCote ("acote.txt"),
	armando ("armando.txt");
	
	private String mFileName;
	private String mFireworks;
	private final String mRootURL = "resources/fireworks/";
	
	FireworkStyles (String filename)
	{
		mFileName = mRootURL + filename;
		mFireworks = parseFireworksFile (mFileName);
	}
	
	/** Parses a file containing ASCII firework art and returns the raw text itself
	 *  as a string. */
	private String parseFireworksFile (String filename)
	{
		String result = "";
		String buffer = "";
		try
		{
			FileReader reader = new FileReader (filename);
			BufferedReader br = new BufferedReader (reader);
			
			while ((buffer = br.readLine()) != null)
			{
				result += buffer + "\r\n";
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public String getFireworks ()
	{
		return mFireworks;
	}
}
