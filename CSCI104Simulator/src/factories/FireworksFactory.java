package factories;

/**
 * Factory used to create fireworks! Yaaay!
 * 
 * It also contains various ASCII art used to represent the fireworks.
 * My algorithm is designed to parse any arbitary ASCII art (that is automatically
 * formatted by Eclipse using Ctrl + C on any art you find in the internet).
 * 
 * Just remember to update the ENUMs so the RNG can select your beautiful ART.
 * 
 * I miss Disneyland.
 * 
 * @author Eric Su
 * ITP 368, Spring 2018
 * Assignment 09
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import engine.OptimizationFlag;
import entities.visuals.ConfettiText;
import javafx.scene.paint.Color;

public class FireworksFactory 
{
	private static Random rand = new Random();
	
	/** Returns a randomly selected firework style we could use */
	public static FireworkStyles getRandomStyle ()
	{
		return FireworkStyles.values()[rand.nextInt(FireworkStyles.values().length)];
	}
	
	/** Returns a randomly selected fireworks explosion */
	public static FireworkStyles getRandomExplosion ()
	{
		int type = rand.nextInt(25);
		
		switch (type)
		{
			case 7:
				return FireworkStyles.USC;
			case 15:
				return FireworkStyles.fightonsymbol;
			default:
				return FireworkStyles.fireworks;
		}
	}
	/** Returns an arraylist of ConfettiText entities ready to display the selected ASCII
	 *  art in a firework-like manner. */
	public static ArrayList <ConfettiText> spawnFireworks (int paneWidth, int paneHeight, FireworkStyles style, OptimizationFlag optFlag)
	{
		int originX, originY;
		int centerX, centerY;
		int spacing = 4;
		int margin = (int)(paneWidth * 0.2);
		char it;
		
		Random rand = new Random();
		
		ArrayList <ConfettiText> confettiObjects = new ArrayList <ConfettiText>();
		
		/* Selects the ascii art based on the passed firework style */
		String asciiArt = style.getFireworks();
		
		/* First, pick a random point to spawn the firework based on the dimensions 
		 * of the screen */
		originX = rand.nextInt(paneWidth - margin);
		originY = rand.nextInt(paneHeight / 2);
		
		/* Secondly, iterate through the ASCII art and map each valid character to a color */
		char nextIt;
		String combined;
		int artWidth = 0, artHeight = 0, tmpWidth = 0;
		Hashtable <String, Color> colorMapping = new Hashtable <String, Color>();
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			it = asciiArt.charAt(i);
			
			if (it != '\n' || it != '\r' || it != ' ')
			{
				/* If the mapping does not have the current character, assign that character a color */
				if (!colorMapping.contains(it))
				{
					colorMapping.put(it + "", new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
				}
				
				if (optFlag == OptimizationFlag.kPerformance && i < asciiArt.length() - 1)
				{
					nextIt = asciiArt.charAt(i + 1);
					if (nextIt != '\n' || nextIt != '\r' || nextIt != ' ')
					{
						combined = it + "" + nextIt + "";
						if (!colorMapping.contains(combined))
						{
							colorMapping.put(combined, new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
						}
					}
				}
				
				
			}
			
			/* Used for calculating the center of the art */
			if (it == '\n')
			{
				++artHeight;
				if (tmpWidth > artWidth)
				{
					artWidth =  tmpWidth;
				}
				tmpWidth = 0;
			}
			else
			{
				++tmpWidth;
			}
			
		}
		
		/* Calculates the center of the shape */
		centerX = originX + ((artWidth * spacing) / 2);
		centerY = originY + ((artHeight * (spacing + spacing)) / 2);
		
		
		/* Thirdly, read through the asciiArt again and construct the fireworks based on its origin point */
		String buffer = "";
		boolean compressFlag = false;
		int currX = originX, currY = originY;
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			it = asciiArt.charAt(i);
			/* If empty space, skip */
			if (it == ' ')
			{
				currX += spacing;
			}
			/* If we reach a return or newline, goto new line */
			else if (it == '\r' || it == 'n')
			{
				currX = originX;
				currY += (spacing + spacing);
			}
			/* Otherwise, construct a new "firework" */
			else
			{
				/* Determines if we should compress the firework size in order to reduce the amount of nodes
				 * generated during gameplay. */
				if (optFlag == OptimizationFlag.kPerformance)
				{
					if (!compressFlag)
					{
						// Compresses the ASCII firework string if the next character happens to be a valid character 
						if (i < asciiArt.length() - 1 && (asciiArt.charAt(i + 1) != ' ' || asciiArt.charAt(i + 1) != '\r' || asciiArt.charAt(i + 1) != '\n'))
						{
							buffer = (char)it + "" + (char)asciiArt.charAt(i + 1) + "";
							compressFlag = true;
						}
						else
						{
							buffer = (char)it + "";
						}
						confettiObjects.add(new ConfettiText (centerX, centerY, currX, currY, buffer ,colorMapping.get(buffer), true));
					}
					else
					{
						compressFlag = false;
					}
					currX += spacing;
				}
				else
				{
					confettiObjects.add(new ConfettiText (centerX, centerY, currX, currY, it + "" ,colorMapping.get(it + ""), true));
					currX += spacing;
				}
			}
			
		}
		
		return confettiObjects;
	}
	
	/** Spawns a fireworks explosion at the point (x, y) */
	public static ArrayList <ConfettiText> spawnExplosion (int x, int y, FireworkStyles style, OptimizationFlag optFlag)
	{
		int originX, originY;
		int centerX, centerY;
		int spacing = 4;
		
		Random rand = new Random();
		
		ArrayList <ConfettiText> confettiObjects = new ArrayList <ConfettiText>();
		
		/* Selects the ascii art based on the passed firework style */
		String asciiArt = style.getFireworks();
		
		/* Secondly, iterate through the ASCII art and map each valid character to a color */
		char it, nextIt;
		String combined;
		int artWidth = 0, artHeight = 0, tmpWidth = 0;
		Hashtable <String, Color> colorMapping = new Hashtable <String, Color>();
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			it = asciiArt.charAt(i);
			
			if (it != '\n' || it != '\r' || it != ' ')
			{
				/* If the mapping does not have the current character, assign that character a color */
				if (!colorMapping.contains(it))
				{
					colorMapping.put(it + "", new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
				}
				
				if (optFlag != OptimizationFlag.kQuality && i < asciiArt.length() - 1)
				{
					nextIt = asciiArt.charAt(i + 1);
					if (nextIt != '\n' || nextIt != '\r' || nextIt != ' ')
					{
						combined = it + "" + nextIt + "";
						if (!colorMapping.contains(combined))
						{
							colorMapping.put(combined, new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
						}
					}
				}
				
				
			}
			
			/* Used for calculating the center of the art */
			if (it == '\n')
			{
				++artHeight;
				if (tmpWidth > artWidth)
				{
					artWidth =  tmpWidth;
				}
				tmpWidth = 0;
			}
			else
			{
				++tmpWidth;
			}
			
		}
		
		/* Calculates the center and origin of the shape */
		centerX = x;
		centerY = y;
		
		originX = centerX - ((artWidth * spacing) / 2);
		originY = centerY - ((artHeight * (spacing * 2)) / 2);
		
		/* Thirdly, read through the asciiArt again and construct the fireworks based on its origin point */
		String buffer = "";
		boolean compressFlag = false;
		int currX = originX, currY = originY;
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			it = asciiArt.charAt(i);
			/* If empty space, skip */
			if (it == ' ')
			{
				currX += spacing;
			}
			/* If we reach a return or newline, goto new line */
			else if (it == '\r' || it == 'n')
			{
				currX = originX;
				currY += (spacing + spacing);
			}
			/* Otherwise, construct a new "firework" */
			else
			{
				/* Determines if we should compress the firework size in order to reduce the amount of nodes
				 * generated during gameplay. */
				if (optFlag != OptimizationFlag.kQuality)
				{
					if (!compressFlag)
					{
						// Compresses the ASCII firework string if the next character happens to be a valid character 
						if (i < asciiArt.length() - 1 && (asciiArt.charAt(i + 1) != ' ' || asciiArt.charAt(i + 1) != '\r' || asciiArt.charAt(i + 1) != '\n'))
						{
							buffer = (char)it + "" + (char)asciiArt.charAt(i + 1) + "";
							compressFlag = true;
						}
						else
						{
							buffer = (char)it + "";
						}
						confettiObjects.add(new ConfettiText (centerX, centerY, currX, currY, buffer ,colorMapping.get(buffer), false));
					}
					else
					{
						compressFlag = false;
					}
					currX += spacing;
				}
				else
				{
					confettiObjects.add(new ConfettiText (centerX, centerY, currX, currY, it + "" ,colorMapping.get(it + ""), false));
					currX += spacing;
				}
			}
			
		}
		return confettiObjects;
	}
	
}
