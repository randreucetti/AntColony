/*Ross Andreucetti
  Oisin Colm Kelly
  Dublin City University
  2011 */

public class Egg
{
	boolean exists;
	int posX;			//the eggs location on the x-axis
	int posY;			//the eggs location on the y-axis
	int posZ;			//the eggs location on the z-axis (which level it's on)
	
	Egg()
	{
		posX = 0;	//default location
		posY = 14;
		posZ = 1;
		exists = false;
	}
	public void lay()
	{
		exists = true;
	}
	public void hatch()
	{
		exists=false;
	}
}