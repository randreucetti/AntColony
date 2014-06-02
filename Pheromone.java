/*Ross Andreucetti
  Oisin Colm Kelly
  Dublin City University
  2011 */

public class Pheromone
{
	int posX;			//coords
	int posY;			
	int posZ;
	int type;	//1 = queen pheramone carrying , 2 = available, 3 = mating, 4 = lookingForFood, 5 = food found
	int movesLeft;//moves until pheromones fades
	
	Pheromone(int x, int y, int z, int t)
	{
		posX = x;
		posY = y;
		posZ = z;
		type = t;
		movesLeft=10;
	}
}