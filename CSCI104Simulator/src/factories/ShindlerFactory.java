package factories;
/**
 * They say that working here is a more rewarding experience than Microsoft, Google,
 * or whatever the next "Big Tech" company is out there
 * 
 * @author Eric Su
 * ITP 368, Spring 2018
 * Assignment 08
 */

import java.util.Random;

public class ShindlerFactory 
{
	private static Random mRand = new Random ();
	/** From his 170 / 356 lectures. Unfortunatly, I didn't document
	 *  everything. */
	private static String [] mDadJokes = 
		{
			"Today's prize isn't a car. Sorry.",
			"I'm going to lie and you are all going to lie with me.",
			"Enough lies and it would all fit together",
			"What the fork()?",
			"Lets play a game of Scrabble",
			"Oh hey, lets put each function into a seperate source file!",
			"If it is not in set bleh... then it is not in there at all.",
			"Don't half ass const."
		};
	
	/** Returns the next Picasso art piece */
	public static String getShindler()
	{
		String senpai = 
				"                     ''...                                  \r\n" + 
				"                  ....'  '-..........                       \r\n" + 
				"               ...    ./ ../ooo/:-.''.-..                   \r\n" + 
				"            '.-'' '/hdm//d-/ss/ohsoy+/' '--.                \r\n" + 
				"           -:ys//:/yyyy:hho:osyhdh+-s+oo-  '--              \r\n" + 
				"         .--dms:hyosyss/.-.-:/:-.:+//y+:s+/' ':'            \r\n" + 
				"        :'.dmyoso-hs:'..'''.....'''..:/:y/d/   -.           \r\n" + 
				"       -' hdh.s+/./...'              '..-:s+:'.+--          \r\n" + 
				"      .- '++'-d-:.'                      ..+h/hy-+-         \r\n" + 
				"     '-:/-:. d+/-                         '-hyhh+:/         \r\n" + 
				"     : o+.s:+ds:'                          '+ymho:/.        \r\n" + 
				"     : yo yhso/-                            -oyms+/:        \r\n" + 
				"     - dh'dNss'-                             -:s/+.:        \r\n" + 
				"     .'om/hNy:'.                     '.--    / :. .-        \r\n" + 
				"      -'myyhh-'      -+sso/'       -ssoo/....:'.-./'        \r\n" + 
				"      '-sdos.-    '':/++/-..''''....''''''   -:'' -.        \r\n" + 
				"       -:ds''-....''''''''  ''/--.       '''.-. '.-:        \r\n" + 
				"      :'--y-.-...           .:'  .-.......''  .  ../        \r\n" + 
				"      /-:'/+/- '''.'''''..../     -'          -  '':        \r\n" + 
				"      .+d':oh-     '''''   ..      ..         '   :         \r\n" + 
				"       /+++s-:             :        .-'          ..         \r\n" + 
				"       ':-'' '            '-..'  '.'' ''        '-'         \r\n" + 
				"        '-.              .    -..''    ''      .''          \r\n" + 
				"          .-..'          .           '' ''     -            \r\n" + 
				"              -         .   '''''''...'       '.            \r\n" + 
				"               -        .   '''''''           -             \r\n" + 
				"               '-       '                   ''.             \r\n" + 
				"                -'                     '   '+.              \r\n" + 
				"                '-           '''''''...-. .':.              \r\n" + 
				"                ':         '.......-....'.''++/-.'          \r\n" + 
				"               '-:.          '''''''  '.''.':ohs+.:         \r\n" + 
				"               : ':.                '.''.'  .+o+- :         \r\n" + 
				"              :'   .:-' '          .. ''   '...'  :         \r\n" + 
				"              o     :'..'-'            '..:-      .-        \r\n" + 
				"              /     -:.-'':          .-://-        :        \r\n" + 
				"              :     :...' -        './hh/          ..       \r\n" + 
				"              :     :     .-.....--oddd+          '-        \r\n" + 
				"              ':    ..     '.''..':+s::'        ...         \r\n" + 
				"                :'   -.      '-    ':':...'  ...'           \r\n" + 
				"                 -.  .-.    -.     :  .:..-..               \r\n" + 
				"                   ..      -.     :.'' :'.'                 \r\n" + 
				"                          .-     '-''                       \r\n" + 
				"                          .      ' '";
		
		return senpai;
	}
	
	public static String getJokeOfTheDay ()
	{
		return mDadJokes[mRand.nextInt(mDadJokes.length)];
	}
}
