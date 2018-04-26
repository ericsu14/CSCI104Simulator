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

import entities.visuals.ConfettiText;
import javafx.scene.paint.Color;

public class FireworksFactory 
{
	private static Random rand = new Random();
	
	/** Defines a list of styles each firework could use */
	public static enum FireworkStyles 
	{
		shindler, party, donutshop, apple, fireworks, disney, fireworks2,
		fighton, fightonsymbol, USC, fireworks3, bulb, cote
	};
	
	/** Note: ASCII art provided by
	 * 		- http://www.chris.com/ascii/joan/www.geocities.com/SoHo/7373/food.html */
	
	public static String mParty = 
			"                               (\r\n" + 
			"\r\n" + 
			"    *                           )   *\r\n" + 
			"                  )     *      (\r\n" + 
			"        )        (                   (\r\n" + 
			"       (          )     (             )\r\n" + 
			"        )    *           )        )  (\r\n" + 
			"       (                (        (      *\r\n" + 
			"        )          H     )        )\r\n" + 
			"                  [ ]            (\r\n" + 
			"           (  *   |-|       *     )    (\r\n" + 
			"     *      )     |_|        .          )\r\n" + 
			"           (      | |    .  \r\n" + 
			"     )           /   \\     .    ' .        *\r\n" + 
			"    (           |_____|  '  .    .  \r\n" + 
			"     )          | ___ |  \\~~~/  ' .   (\r\n" + 
			"            *   | \\ / |   \\_/  \\~~~/   )\r\n" + 
			"                | _Y_ |    |    \\_/   (\r\n" + 
			"    *           |-----|  __|__   |      *\r\n" + 
			"                `-----`        __|__";
	
	public static String mDonutShop =
			"     _..------.._\r\n" + 
			"   .'   .-\"\"-.   '.\r\n" + 
			"   |\\   '----'   /|\r\n" + 
			"   \\ `'--------'` /\r\n" + 
			"    '._        _.'\r\n" + 
			"       `\"\"\"\"\"\"`";
	
	public static String mAppleComputer = 
			"           .:'\r\n" + 
			"       __ :'__\r\n" + 
			"    .'`  `-'  ``.\r\n" + 
			"   :          .-'\r\n" + 
			"   :         :  \r\n" + 
			"    :         `-;\r\n" + 
			"     `.__.-.__.'";
	
	public static String mFireworks =
			"               *    *\r\n" + 
			"   *         '       *       .  *   '     .           * *\r\n" + 
			"                                                               '\r\n" + 
			"       *                *'          *          *        '\r\n" + 
			"   .           *               |               /\r\n" + 
			"               '.         |    |      '       |   '     *\r\n" + 
			"                 \\*        \\   \\             /\r\n" + 
			"       '          \\     '* |    |  *        |*                *  *\r\n" + 
			"            *      `.       \\   |     *     /    *      '\r\n" + 
			"  .                  \\      |   \\          /               *\r\n" + 
			"     *'  *     '      \\      \\   '.       |\r\n" + 
			"        -._            `                  /         *\r\n" + 
			"  ' '      ``._   *                           '          .      '\r\n" + 
			"   *           *\\*          * .   .      *\r\n" + 
			"*  '        *    `-._                       .         _..:='        *\r\n" + 
			"             .  '      *       *    *   .       _.:--'\r\n" + 
			"          *           .     .     *         .-'         *\r\n" + 
			"   .               '             . '   *           *         .\r\n" + 
			"  *       ___.-=--..-._     *                '               '\r\n" + 
			"                                  *       *\r\n" + 
			"                *        _.'  .'       `.        '  *             *\r\n" + 
			"     *              *_.-'   .'            `.               *\r\n" + 
			"                   .'                       `._             *  '\r\n" + 
			"   '       '                        .       .  `.     .\r\n" + 
			"       .                      *                  `\r\n" + 
			"               *        '             '                          .\r\n" + 
			"     .                          *        .           *  *\r\n" + 
			"             *        .                                    '";
	
	public static String mDisney =
			"\r\n" + 
			"                                                                                                     \r\n" + 
			"                                                                                                    \r\n" + 
			"                             .:/+sso+/-`                    -/+ssssso/-`                            \r\n" + 
			"                         `:shyo+:-.-:/oyh+`              .ods+:-...-:+ohy/`                         \r\n" + 
			"                       `+dy/`           .sd/            /mo`           `-sds.                       \r\n" + 
			"                      :dy.                /m/          :m+                `sm/                      \r\n" + 
			"                     +m/                   sm`         yd                   :ds                     \r\n" + 
			"                    /m/                    :m-         hh                    -ds                    \r\n" + 
			"                    yd                     :No+ossssso+mh                     +m`                   \r\n" + 
			"                    ds                     -s+:-.....-/os                     +m`                   \r\n" + 
			"                    yd                                                        yd                    \r\n" + 
			"                    -m+                                                      :m+                    \r\n" + 
			"                     :ds.                                                  `/ds`                    \r\n" + 
			"                      `oyy+-.` `..                                ......-/syy:                      \r\n" + 
			"                        `-/ossssmd`                               omhssso+:.                        \r\n" + 
			"                             ``/m+                                `hy                               \r\n" + 
			"                               yd                                  /m.                              \r\n" + 
			"                               ds                                  -m/                              \r\n" + 
			"                               mo                                  -m:                              \r\n" + 
			"                               hy                                  +m.                              \r\n" + 
			"                               /m-                                `dy                               \r\n" + 
			"                               `yd`                              `ym.                               \r\n" + 
			"                                `yh.                            `sm-                                \r\n" + 
			"                                 `od+`                         .yd-                                 \r\n" + 
			"                                   -hh/`                     .odo`                                  \r\n" + 
			"                                     :ydo:`              `./sdo.                                    \r\n" + 
			"                                       `/shyo/:-.```.-/+shho:                                       \r\n" + 
			"                                           `-/+osyyyys+/-`                                          ";
	
	public static String mFireworks2 =
			"                        :                                  \r\n" + 
			"                         `                                 \r\n" + 
			"                          ,      '                         \r\n" + 
			"                           ,     :                         \r\n" + 
			"                           .     `                         \r\n" + 
			"                            `                              \r\n" + 
			"                   .        '                              \r\n" + 
			"                     ,      `                '.,,          \r\n" + 
			"                      ,         `          :               \r\n" + 
			"                       `     :  .         '                \r\n" + 
			"                             ,  :  : '   '                 \r\n" + 
			"                        :       ;       :                  \r\n" + 
			"               .,.       ,      ' ,    `                   \r\n" + 
			"             '     ,.         . : ,  : .        '.    '    \r\n" + 
			"            ;        .    '   : .   ; .      `,        :   \r\n" + 
			"     `',`  `.;,        ;      . `. . :      ,              \r\n" + 
			"   ,.           `'      :  ;'   `:        '                \r\n" + 
			"  '                `,    .  `'    : `    `    ::`    ,;    \r\n" + 
			" ;     .;'''':`       ;  ;` `      ; ' :   :`          .`  \r\n" + 
			"    `:           .;     ,  ; `,``,,   :  ,`;;:;;`          \r\n" + 
			"   '                 ,`   . : .```.   .,,        ,.        \r\n" + 
			"  '            .,     ::,`,. ,` ``   ,`.           '       \r\n" + 
			"              ;           ,. .```  .   '.       `;.        \r\n" + 
			"             :              .      ..  `           `,      \r\n" + 
			"            '      ::,...````       `.;,,,,`         :     \r\n" + 
			"               ., ;         `       ``,,`     `'      .    \r\n" + 
			"             ;           ``,`              :     `;    ,   \r\n" + 
			"           ``    ,     .  ,.       `    .    .`     ;   '  \r\n" + 
			"          ;      :   ,  . ,   `      .  ;      ,     ,     \r\n" + 
			"         '         .   : ,  `    ` `  ,          :     `   \r\n" + 
			"        '       ` .   . .` `    ` ` `  .          ;        \r\n" + 
			"       .        ;      :   `      `     `,         ,    .  \r\n" + 
			"       `       .;  `  , : `  `     ` `   '          :    ' \r\n" + 
			"      ;      .  . `     ` `  .   . .  ,   `          '     \r\n" + 
			"      `     :    `   ,   .   ,   `     ,   .          ;    \r\n" + 
			"     .     ;        .  `     :      ,       ;              \r\n" + 
			"     :    ;     `   `  ;.    :      `   `    :         ,   \r\n" + 
			"         :     :   .   :`    :    .      :              :  \r\n" + 
			"    `   `     '    .   ,     ,    ,  ,        .         .  \r\n" + 
			"    ;   .    `    `    `     .    `  ,    .    :         : \r\n" + 
			"       '     :    '   `      .             .             . \r\n" + 
			"            ,         ;      .                  ;         .\r\n" + 
			"      '     .    .    .      .     .  :     :   `          \r\n" + 
			"      `    ,     '   ':      .     :  :          .         \r\n" + 
			"     .     ,     .    ;      ,     .  `      ;   ;         \r\n" + 
			"     '              . '      ,                             \r\n" + 
			"     :          .   , '      :         `      :   .        \r\n" + 
			"     .          '     ,      ;         :      `   '        \r\n" + 
			"                ,  :         '      .  '       `  ;        \r\n" + 
			"                   ,         '      :  :       ;  .        \r\n" + 
			"                             :      :  .          `        \r\n" + 
			"                  ,          ,      .  `        ,          \r\n" + 
			"                  '          `                  ;          \r\n" + 
			"                  `                             `          \r\n" + 
			"                                                           \r\n" + 
			"                 ,                   `           :         \r\n" + 
			"                 '                   ,           '         \r\n" + 
			"                 ;                   '           ,         \r\n" + 
			"                 ,                   :           `         \r\n" + 
			"                 `                   .                     \r\n" + 
			"                                     `                     \r\n" + 
			"                                                           \r\n" + 
			"                                                           \r\n" + 
			"                                                           \r\n" + 
			"                                      `                    \r\n" + 
			"                                      ,                    \r\n" + 
			"                                      ;                    \r\n" + 
			"                                      '                    \r\n" + 
			"                                      ;                    \r\n" + 
			"                                      ,                    \r\n" + 
			"                                      .                    \r\n" + 
			"                                      `                    \r\n" + 
			"                                                           \r\n" + 
			"                                                           \r\n" + 
			"                                                           \r\n" + 
			"                                       `                   \r\n" + 
			"                                       `                   \r\n" + 
			"                                       .                   \r\n" + 
			"                                       ,                   \r\n" + 
			"                                       :                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       '                   \r\n" + 
			"                                       '                   \r\n" + 
			"                                       '                   \r\n" + 
			"                                       '                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       ;                   \r\n" + 
			"                                       '    ";
	
	public static String mFightOn =
			"                                                                                                    \r\n" + 
			"                                                                                                    \r\n" + 
			"        ..........``....   `....... ....`  ....``..........`      `....`   `...`  `....  ...        \r\n" + 
			"       `yhhhhhhhhh:-hhhs -shhhhhhhy`ohhh:  ohhh:-hhhhhhhhhh.    .shhhhhhy: /hhhs` :hhho .hhh`       \r\n" + 
			"        +hho----//. shh: shho:::/++`-hhy`  -hhy`.//-shho://`    shho::/hhy``hhhhy-`yhh- .hhh`       \r\n" + 
			"        +hhysss/    shh: shh:  :///`-hhysssshhy`    ohh+        shh/  `hhh``hhhyhy/yhh- .hhh`       \r\n" + 
			"        +hhysss/    shh: shh:  +hhy`-hhysssshhy`    ohh+        shh/  `hhh``hhh/yhhyhh- .hhh`       \r\n" + 
			"        +hho        shh: shho::+hhy`-hhy`  -hhy`    ohh+        shho::/hhh``hhh..shhhh- `///`       \r\n" + 
			"       `yhhy`      -yhhs -shhhhhhhy`ohhh:  ohhh:   `yhhy`       .syhhhhhy/ /hhh+ `+hhho .hhh`       \r\n" + 
			"        ....       `....   `....... ....`  ....`    ....          `....`   `...`   ....  ...        \r\n" + 
			"                                                                                                    \r\n" + 
			"                                                                                                    \r\n" + 
			"                                                                                                    \r\n" + 
			"";
	
	public static String mFightOnSymbol =
			"             .:://::-.        .:::::-.  \r\n" + 
			"            /:-.` `.::-      /:-..`.:::.\r\n" + 
			"           .:--`    -::`    ::-      +-:\r\n" + 
			"           `/-:     ./-.    +-.      o--\r\n" + 
			"            :--      /--   ./:`     ./-`\r\n" + 
			"            -:-`     ::-`  :--      /:: \r\n" + 
			"            `/--     `+-.  /-.      o-- \r\n" + 
			"             :--      /-- .:-`     ./-. \r\n" + 
			"             -::      -::`:--      /:-  \r\n" + 
			"             `:-.     .+-:/-:     `+--  \r\n" + 
			"           `../--      +-/:-.     -/-`  \r\n" + 
			"        -::::::--      -:---      /:-   \r\n" + 
			"       ::-.```.::`     `/--`     `+--   \r\n" + 
			"  `----/-.     ./-       `       :/-.   \r\n" + 
			" :/-----:`   `-+::/::::///:-`    -/-`   \r\n" + 
			"/:-`   `/-   -/.          `.::-` -/-`   \r\n" + 
			"/--     ::.  /:.             `.::-+--   \r\n" + 
			":-.     `/.  `-::-..``          .-:--.  \r\n" + 
			":::      :-`    `./:-://.         `-::. \r\n" + 
			"`/--     ./-      o:` o-            :-: \r\n" + 
			" :--      /:      /--//-            /:: \r\n" + 
			" -::`    -+:.    `+:/-./:`          +-- \r\n" + 
			"  -:::::/:..-/:-:/:-.  `::-.       ./-. \r\n" + 
			"   `.:-.     `````+.     `.-:::.   :-:  \r\n" + 
			"     /--         `+-          ``  `+--  \r\n" + 
			"     ::-`        `/:.             ::-`  \r\n" + 
			"      /-.          .-`            /-:   \r\n" + 
			"      :-:                        `+-.   \r\n" + 
			"      .::-......................-/::`   \r\n" + 
			"       .-:::::::::::::::::::::::::-`    ";
	
	public static String mUSC =
			"\r\n" + 
			"     `/oooooooooooooooooo+`              \r\n" + 
			"  `/s+::::::::::::::::::/s+`            \r\n" + 
			"`/s+::::::::::::::::::::::/s+`          \r\n" + 
			"+o:::::::////////////:::::::+s          \r\n" + 
			"+o::::::+s//////////oo::::::/s          \r\n" + 
			"+o::::::o+          :s::::::/s          \r\n" + 
			"+o::::::o+          :yooooooos          \r\n" + 
			"+o::::::oo..........----.`````          \r\n" + 
			"+o::::::/+++++++++++++++o:              \r\n" + 
			"-oo/::::::::::::::::::::/+o:            \r\n" + 
			" `:o+/::::::::::::::::::::/+o-          \r\n" + 
			"   `:oo++++++++++++++/::::::+y----`     \r\n" + 
			"`````.:::-...-+y++++ss::::::/y///+o/`   \r\n" + 
			"+o++++++o+  :o+/::::os::::::/y:::::+o/` \r\n" + 
			"+o::::::o+.o+/::::::os::::::/y:::::::+o/\r\n" + 
			"+o::::::+soo::::::/oso::::::/y++::::::/y\r\n" + 
			"+o:::::://so::::::+y//::::::+s/s::::::/y\r\n" + 
			"`+o/::::::oo::::::+y::::::/o+.:s///////y\r\n" + 
			"  .+o/::::oo::::::+y::::/o+.  -o+++++++o\r\n" + 
			"    .+o+++so::::::+y+++o+.              \r\n" + 
			"      ````+o::::::+s````                \r\n" + 
			"          /o::::::+o                    \r\n" + 
			"          /o::::::+o           `````````\r\n" + 
			"          /o::::::+o          :yoooooooy\r\n" + 
			"          /o::::::+o          :s::::::/y\r\n" + 
			"          /o::::::+y//////////os::::::/y\r\n" + 
			"          /s/:::::://///////////::::::/y\r\n" + 
			"           :s+::::::::::::::::::::::/s+`\r\n" + 
			"             /s+::::::::::::::::::/s+`  \r\n" + 
			"               /oooooooooooooooooo+`    ";
	
	public static String mFireworks3 =
			"\r\n" + 
			"                            `    . `  ` `                              \r\n" + 
			"                           ``   . ` ```` ``   ``                      \r\n" + 
			"                    `. `` ` :.  - . `--`..` `.-  `                    \r\n" + 
			"              `  `   `.`.`` `-  :```.-``.. `.-` ..` `                 \r\n" + 
			"             ` ```.````.``.` .. .. `.. `.``..`  .```  ``              \r\n" + 
			"            `` .```-``.``-`.``-`````..``.`-````.``-`.-.  ` `          \r\n" + 
			"          ````````.``` `  . ```` ````-. ````... ..``.` ..`            \r\n" + 
			"           ``..`````.` `` ````   `. `-.  `..` ``. `.. ````````        \r\n" + 
			"        .``   .-..``.--.``  `-. . `  .` `-.  `.``...``-.-..``         \r\n" + 
			"          .-``  `.`  `````   `.`. `.`.  ``   ` `.`` ..` ````` `       \r\n" + 
			"      ` `.``....``..   ` ``  .``   .`` ``   ..` ``.``````.`..````     \r\n" + 
			"       ````.`.`....````..` `  . `.  .` `  ..` ``.`````.```.`...``     \r\n" + 
			"     ` `.`````   `````  ````` `. `  `    ..   ` ``.`.`.`````.`..```   \r\n" + 
			"     ` ``.........`.``      `  .`  `    ..` ``` ``` ``````````````    \r\n" + 
			"   ` ``...`.`.`...` ``.--.`        `   `   ``````         `````````   \r\n" + 
			"      ````.```          ````  ``  .   `.  ````.``   ````` .`.`.```    \r\n" + 
			"   `   ` `    `.`` ````.     `.`              ````.`...`.`..``....``  \r\n" + 
			"  ```-.--.....``  ```` `  `` ` ``  `` `..  `.    `````..-```````````  \r\n" + 
			"  `````````.``  ```` `  `````` `    `   `   ``.`  ````` ````.`.`````  \r\n" + 
			"    ``..```  ` ``` ``````  `.``.  `.    `  `  `..```..-.` ``.`.`.```  \r\n" + 
			"   .`.``  .....-.```   `` ..  ``  -.    `. `` .  `````..-..`.````` `  \r\n" + 
			"    ``....`.`.``  ``  `` ` `  .   ``  `  `.`   `   `.``   `...-`. `   \r\n" + 
			"   `` ``...```    ``  `.```` `` `.`   .   ..``` ``` ``.`  `` `...``   \r\n" + 
			"    ```.`    `````` `..` `  .`      `    `  ` ``...`. `..``````  `    \r\n" + 
			"    ``   `.-..`.````````  ``.`   `` .`    `  ``  ` ``..``..`.````     \r\n" + 
			"       `.---``````  `.`  .``.``  .` ..  ```   ````.`  . - ``.`.````   \r\n" + 
			"      ````  ```.`````.  ..  .`. `` ``-  .`. .```.```.` ` ```.   `     \r\n" + 
			"       `    `..``.``.````  ``.` `` .`.  .`.``. ``.` `..```  ` `       \r\n" + 
			"           `..`--` `.-   `` `-  .. .`. `.-````` .`.`` . ...   `       \r\n" + 
			"           . ````` `-``  `` .` `.. .`````. ..`.`` -``.``.  ``         \r\n" + 
			"           ``` `` `- .  `.`````.`. `. ` .` ````.`` ..`.`.  `          \r\n" + 
			"              ``  .`.-```.``.``- - `- .`-```-` ..`. .``` ``           \r\n" + 
			"              `  `  -``.`.`````.`. `. `...  ..  - `` `` `             \r\n" + 
			"                    ```  .``` ```. .` `..`  `.      `                 \r\n" + 
			"                   `  `  `.`` ` `` .`  ```   `.  `` `                 \r\n" + 
			"                          `        `     `                            ";
	
	public static String mBulb =
			"                                                                      \r\n" + 
			"                                                                      \r\n" + 
			"                         ````..............```                        \r\n" + 
			"                     ```.........................`                    \r\n" + 
			"                  ``................................`                 \r\n" + 
			"                ``.......```...........................`              \r\n" + 
			"              ```.....`      ........................---.`            \r\n" + 
			"            ````....`         ......................------.           \r\n" + 
			"           `````..`            .....................-------.`         \r\n" + 
			"          `````.`           ``.....................----------`        \r\n" + 
			"         `````.`          `.......................------------`       \r\n" + 
			"        `````.`         `.........................------------.       \r\n" + 
			"        ``````         `.........................--------------.      \r\n" + 
			"       `````.`        `.........................----------------      \r\n" + 
			"       ````.`        `.........................-----------------`     \r\n" + 
			"       ```..`        ..........................-----------------`     \r\n" + 
			"       ```..`       `.........................------------------`     \r\n" + 
			"       ``...```````..........................-------------------`     \r\n" + 
			"       `.....................................-------------------      \r\n" + 
			"       `....................................-------------------.      \r\n" + 
			"        `..................................--------------------`      \r\n" + 
			"         .................................--------------------`       \r\n" + 
			"          ................................-------------------.        \r\n" + 
			"          `..............................-------------------.         \r\n" + 
			"            `............................------------------`          \r\n" + 
			"             `..............................-------------.            \r\n" + 
			"               `...............................--------.`             \r\n" + 
			"                `...............................------.               \r\n" + 
			"                 `...............................----.                \r\n" + 
			"                  ..............................-----`                \r\n" + 
			"                  `.............................----.                 \r\n" + 
			"                   ............................-----`                 \r\n" + 
			"                   ....................-------------`                 \r\n" + 
			"                   ......---------------------------`                 \r\n" + 
			"                   ---------------------------------.                 \r\n" + 
			"                   ---------------------------------`                 \r\n" + 
			"                   `-------------------------------.                  \r\n" + 
			"                     `.:::::////////////////////-.`                   \r\n" + 
			"                       +ssssssssyyyyyyyyyyyyhhhy`                     \r\n" + 
			"                       :syyyyyyyyyyyyyyyyyyyyyy+                      \r\n" + 
			"                       `-ssyyyyyyyyyyyyyyyyyyyo`                      \r\n" + 
			"                       -+sssssssssssyyyyyyyyyys/                      \r\n" + 
			"                       -+yyyyyyyyyyyyyyyyyyyyyo:                      \r\n" + 
			"                       .:ssossssyyyyyyyyyyyyyyo.                      \r\n" + 
			"                       :oyyyyyssssssssyyyyyyyys/                      \r\n" + 
			"                       `:sssyyyyyyyyyyyyyyyyyy+                       \r\n" + 
			"                       -/ossssoooooossssssssss:                       \r\n" + 
			"                          :ooooooooooooooooo/`                        \r\n" + 
			"                           -hdddddddddddddh+                          \r\n" + 
			"                            `/ydddddddddh+.                           \r\n" + 
			"                               .+hdddds:                              \r\n" + 
			"                                  ...                                 \r\n" + 
			"                                                                      ";
	
	public static String aCote = "\r\n" + 
			"                                     `` ```                            \r\n" + 
			"                            ```````````````````````                   \r\n" + 
			"                      ````````````````````````````.``.````            \r\n" + 
			"                  ``````````````````````````````````..`..`````        \r\n" + 
			"              ``````````````````````````````````````..`....``````     \r\n" + 
			"           ``````````````````     ```````````````````.`..`.````.`     \r\n" + 
			"          `````````````             `````````````````.`....``````     \r\n" + 
			"         ````````````              ``````````````````.`.....``````    \r\n" + 
			"       `````````````````  ` `     ````````````````.``.`.````````````  \r\n" + 
			"       ``````````````````````````````````````.`.``.``.`.``..````````` \r\n" + 
			"      ```````````````````````````````````````.`.``.`..`.`...........``\r\n" + 
			"     ..----...`````````````````````````````````.``.`..```....-.......`\r\n" + 
			"   `-/++++++++/:.```````````.........`````.``.`.``.`..```....-.......`\r\n" + 
			"  ./+++o+o++o+o+/-.......-//+++++++///::-..`..`.``.`..`..-:::/::-....`\r\n" + 
			" `.+o++o+o+oo+oo++++++++++o+o+o+ooo++o+o+/-.....--:::///+++++++/-`.`` \r\n" + 
			" ``:/++o+o+oo+o+o++:::/+++o+o+o+ooo++o+o+++++//////:::-:::----..```.` \r\n" + 
			"    `..::///////:-.```.:/+++o+o+ooo++o+++/-......`.`..`.......``.`````\r\n" + 
			"      ```````````````````...---:::/:/:--.``````.``.`..```````..`````` \r\n" + 
			"       `````````` ````````````````````````.``.`..`.`..`.``..`.````.`` \r\n" + 
			"      ``````.```````````````.``.```````.``.`..`..`.`..`.....`.`..``   \r\n" + 
			"       ``.`````.````......`````.`..`..`....`..`.......`....``.`..`    \r\n" + 
			"       ``.``.`.``````````..`.`..``.`..`....`..`.......`..`.``````     \r\n" + 
			"        `.``.`..```````.````.`..`````.`....`..`.......`..```          \r\n" + 
			"        `....```````````````.``.```````....`..`..`.`..`..`..          \r\n" + 
			"         `...``..:-`.......`.....````.`.`..`..`..`.`..`.`````         \r\n" + 
			"          ````..`..````.`````````.```````````.`..`.`..`.....`         \r\n" + 
			"           ````.`..`....````.......`.``````````.......`.....``        \r\n" + 
			"             `..`````````````......`..```````.`.......`......````.-:` \r\n" + 
			"              `.`.`````````````.`..`..`````````.......`.......`.``..  \r\n" + 
			"               .````.``````````.`..```````.```````.`..`..`....``````  \r\n" + 
			"               ```.`.````..`.``.````````````````````..`.......`..``   \r\n" + 
			"                ``````````````````````````````````.`..`..`.`..`..     \r\n" + 
			"                 ```````````````````````````````......`.``.`..`..     \r\n" + 
			"                 ``````````````````````````````.......`....``.```     \r\n" + 
			"                 `.``......``````````````````.`.......`.....``````";
	
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
			case 0:
				return FireworkStyles.fighton;
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
	public static ArrayList <ConfettiText> spawnFireworks (int paneWidth, int paneHeight, FireworkStyles style)
	{
		int originX, originY;
		int centerX, centerY;
		int spacing = 4;
		int margin = (int)(paneWidth * 0.2);
		
		Random rand = new Random();
		
		ArrayList <ConfettiText> confettiObjects = new ArrayList <ConfettiText>();
		
		/* Selects the ascii art based on the passed firework style */
		String asciiArt;
		switch (style)
		{
			case shindler:
				asciiArt = ShindlerFactory.getShindler();
				break;
			case party:
				asciiArt = mParty;
				break;
			case donutshop:
				asciiArt = mDonutShop;
				break;
			case apple:
				asciiArt = mAppleComputer;
				break;
			case fireworks:
				asciiArt = mFireworks;
				break;
			case disney:
				asciiArt = mDisney;
				break;
			case fireworks2:
				asciiArt = mFireworks2;
				break;
			case fighton:
				asciiArt = mFightOn;
				break;
			case fightonsymbol:
				asciiArt = mFightOnSymbol;
				break;
			case USC:
				asciiArt = mUSC;
				break;
			case fireworks3:
				asciiArt = mFireworks3;
				break;
			case bulb:
				asciiArt = mBulb;
				break;
			case cote:
				asciiArt = mFireworks2;
				break;
			default:
				asciiArt = "NULL";
				break;
		}
		
		/* First, pick a random point to spawn the firework based on the dimensions 
		 * of the screen */
		originX = rand.nextInt(paneWidth - margin);
		originY = rand.nextInt(paneHeight / 2);
		
		/* Secondly, iterate through the ASCII art and map each valid character to a color */
		int artWidth = 0, artHeight = 0, tmpWidth = 0;
		Hashtable <Character, Color> colorMapping = new Hashtable <Character, Color>();
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			char it = asciiArt.charAt(i);
			
			if (it != '\n' || it != '\r' || it != ' ')
			{
				/* If the mapping does not have the current character, assign that character a color */
				if (!colorMapping.contains(it))
				{
					colorMapping.put(it, new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
				}
			}
			
			/* Used for calculating the center of the art */
			if (it == 'r' || it == '\n')
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
		int currX = originX, currY = originY;
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			char it = asciiArt.charAt(i);
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
				confettiObjects.add(new ConfettiText (centerX, centerY, currX, currY, it + "" ,colorMapping.get(it), true));
				currX += spacing;
			}
			
		}
		
		return confettiObjects;
	}
	
	/** Spawns a fireworks explosion at the point (x, y) */
	public static ArrayList <ConfettiText> spawnExplosion (int x, int y, FireworkStyles style)
	{
		int originX, originY;
		int centerX, centerY;
		int spacing = 4;
		// int margin = (int)(paneWidth * 0.2);
		
		Random rand = new Random();
		
		ArrayList <ConfettiText> confettiObjects = new ArrayList <ConfettiText>();
		
		/* Selects the ascii art based on the passed firework style */
		String asciiArt;
		switch (style)
		{
			case shindler:
				asciiArt = ShindlerFactory.getShindler();
				break;
			case party:
				asciiArt = mParty;
				break;
			case donutshop:
				asciiArt = mDonutShop;
				break;
			case apple:
				asciiArt = mAppleComputer;
				break;
			case fireworks:
				asciiArt = mFireworks;
				break;
			case disney:
				asciiArt = mDisney;
				break;
			case fireworks2:
				asciiArt = mFireworks2;
				break;
			case fighton:
				asciiArt = mFightOn;
				break;
			case fightonsymbol:
				asciiArt = mFightOnSymbol;
				break;
			case USC:
				asciiArt = mUSC;
				break;
			case fireworks3:
				asciiArt = mFireworks3;
				break;
			case bulb:
				asciiArt = mBulb;
				break;
			case cote:
				asciiArt = mFireworks2;
				break;
			default:
				asciiArt = "NULL";
				break;
		}
		
		/* Secondly, iterate through the ASCII art and map each valid character to a color */
		int artWidth = 0, artHeight = 0, tmpWidth = 0;
		Hashtable <Character, Color> colorMapping = new Hashtable <Character, Color>();
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			char it = asciiArt.charAt(i);
			
			if (it != '\n' || it != '\r' || it != ' ')
			{
				/* If the mapping does not have the current character, assign that character a color */
				if (!colorMapping.contains(it))
				{
					colorMapping.put(it, new Color (rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
				}
			}
			
			/* Used for calculating the center of the art */
			if (it == 'r' || it == '\n')
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
		originY = centerY - ((artHeight * (spacing + spacing)) / 2);
		
		/* Thirdly, read through the asciiArt again and construct the fireworks based on its origin point */
		int currX = originX, currY = originY;
		for (int i = 0; i < asciiArt.length(); ++i)
		{
			char it = asciiArt.charAt(i);
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
				confettiObjects.add(new ConfettiText (centerX, centerY, currX, currY, it + "" ,colorMapping.get(it), false));
				currX += spacing;
			}
			
		}
		
		return confettiObjects;
	}
	
}
