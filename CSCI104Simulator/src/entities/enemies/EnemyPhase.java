/** Defines the different phases the enemy could be in while making
 *  a move. In this game, a common enemy could be in the following four stages:
 *  
 *  	- Spawning - The phase where the enemy is flying to their spawn point
 *  	- Idle - The phase where the enemy waits for further instructions to attack
 *  	- Attack - The phase where the enemy swoops in and attacks the player
 *  	- Retreat - The phase where the enemy retreats back to its
 *                  origin after attacking. */
package entities.enemies;

public enum EnemyPhase 
{
	kSpawning, kIdle, kAttack, kRetreat
}
