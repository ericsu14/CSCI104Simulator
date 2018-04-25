/** Defines the different types of command an enemy could use.
 * 		- Move moves an enemy to a desginated coordinate of the game world
 *      - Prepare attack forces the enemy to do a "spin" before traveling to the attack point
 *        based on the enemy's current direction of the game world.
 * 		- Attack moves an enemy to a location close to the vicinity of the player
 * 		- Retreat moves an enemy back to its origin */
package entities.enemies;

public enum CommandType 
{
	kMove, kPrepareAttack, kAttack, kRetreat
}
