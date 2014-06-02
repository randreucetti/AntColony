/*Ross Andreucetti
  Oisin Colm Kelly
  Dublin City University
  2011 */


import java.util.*;	//packages included

public class Ant	//Represents a single worked ant
{
	public int stepsMade;		//how many steps the ant has made.
	public int posX;			//the ants location on the x-axis
	public int posY;			//the ants location on the y-axis
	public int posZ;			//the ants location on the z-axis (which level it's on)
	public double waterLevel;		//the ants water level
	public double foodLevel;		//the ants food level
	public int bladder;			//bladder level, when this reaches 100 it will create feces at current location
	private boolean foodCarried;	//whether the ant is carrying food or not
	public int gatherSkill;	//how good ant is at gaterings
	public int direction;		//direction ant is facing.... 1=right,2=down,3=left,4=up
	private int activity; 		//what the ant is currently doing, ensures ant carries out a task fully before doing something else
	private int energy;			//energy level, needs atleast 100 to mate
	
	Ant(int x, int y, int z)		//constructor takes in x,y,z coords as parameters
	{
		stepsMade = 0;				//steps made set to zero
		posX = x;					//sets ants correct position
		posY = y;
		posZ = z;
		Random r = new Random();
		foodLevel = 70+r.nextInt(31);		//a little bit random so ants start out with different stats
		waterLevel = 70+r.nextInt(31);
		bladder = r.nextInt(31);
		foodCarried = false;		//whether ant is carrying food
		gatherSkill = 100;			//starting gathering skill, (ant can carry 100 food)
		direction = 1;				//direction ant starts
		activity =0;		//no current activity
		energy = 100;	//ants energy, needs 100 to mate
	}
	public void getMove(World w)	//gets what decision ant will make based on current state of ant and surroundings
	{
		this.stepsMade++;	//adjust ants stats
		this.bladder++;
		foodLevel = foodLevel -0.4;
		waterLevel = waterLevel -0.5;
		if(energy<100)	//increase if energy's not full
			energy++;
		
		if(waterLevel<50)	//drinking gets highest priority
			drink(w);
		else if(foodLevel<50&&w.store.amount>50)	//then food
			eat(w);
		else if(w.rubbish.size()>0&&(activity==0||activity==1))	//then removing rubbish
			this.removeRubbish(w);
		else if(w.store.amount<w.ants.size()*100&&(activity==0||activity==2))	//then food gathering
			this.gatherFood(w);
		else if(energy>=100&&(activity==0||activity==3))	//mating
			this.mate(w);
		else		//sleep if nothing to do
			this.sleep(w);
	}

	private void move(int i)
	{
		//move 1 = left
		//move 2 = right
		//move 3 = up
		//move 4 = down
		//direction is used for graphical repesentation of ants direction

		switch(i)
		{
			case 1: 
				if(posX > 0)
				{
					posX--; 
					direction=3;
				}
			break;
			
			case 2: 
				if(posX < 29)
				{
					posX++; 
					direction=1;
				}
			break;
			
			case 3: 
				if(posY > 0)
				{
					posY--;
					direction=4;
				}
			break;
			
			case 4: 
				if(posY < 14)
				{
					posY++;
					direction=2;
				}
			break;
		}
	}
	
	private boolean moveTowards(int x,int y,int z,World w)//method which takes the world and a 
	{									//coord as a parmeter and moves ant towrds it returns true
		if(posZ!=z)						//when reached
			this.changeLevel(w);//call change level if on wrong level
		else if(posX>x)
			this.move(1);
		else if(posX<x)
			this.move(2);
		else if(posY>y)
			this.move(3);
		else if(posY<y)
			this.move(4);
		if(x==posX&&y==posY&&z==posZ)
			return true;
		else return false;
	}
	private void changeLevel(World w)//changles level
	{
		if((getDistance(w.ladder1above.posX,w.ladder1above.posY))//finds the closest ladder and uses it
				<(getDistance(w.ladder2above.posX,w.ladder2above.posY)))
		{
			if(moveTowards(w.ladder1above.posX,w.ladder1above.posY,posZ,w))
				if(posZ==0)
					posZ=1;
				else
					posZ=0;
		}
		else
		{
			if(moveTowards(w.ladder2above.posX,w.ladder2above.posY,posZ,w))
				if(posZ==0)
					posZ=1;
				else
					posZ=0;
		}
	}
	
	private void gatherFood(World w)//method to gather food
	{
		activity = 2;//sets activity to two so can't  be interupted by equal priority activites
		int result;
		if(foodCarried)	//if the ant is carrying food, call deposit food
		{
			depositFood(w);
			return;
		}
		else if(posZ!=0)	//if not on top level where food is, change level
		{
			w.pheromones.add(new Pheromone(posX,posY,posZ,4));//drop our pheromones on way
			changeLevel(w);
			return;
		}
			result = search4Food(w.food,w);	//call search for food which checks 3 square radius of ant for food
		if(result==0)//if no food is near, move in a new direction
		{
			ArrayList<Integer> unvisited = new ArrayList<Integer>();
			if(w.getPheromoneType(posX-1,posY,posZ)!=4)//this check neighbouring squares for pheromones
				unvisited.add(1);						//of ants doing same task
			if(w.getPheromoneType(posX+1,posY,posZ)!=4)//and tries to only move to a fresh square
				unvisited.add(2);
			if(w.getPheromoneType(posX,posY-1,posZ)!=4)
				unvisited.add(3);
			if(w.getPheromoneType(posX,posY+1,posZ)!=4)
				unvisited.add(4);
			
			if(unvisited.size()==0)
			{
				Random r = new Random();
				this.move(r.nextInt(4)+1);
				w.pheromones.add(new Pheromone(posX,posY,posZ,4));
			}
			else
			{
				Random r = new Random();
				int dir = r.nextInt(unvisited.size());
				this.move(unvisited.get(dir));
				w.pheromones.add(new Pheromone(posX,posY,posZ,4));//drop our pheromones on way
			}
			
			
		}
		if(result==1)//if search returns 1 then there is food near, but we still need to travel a small amount
			w.pheromones.add(new Pheromone(posX,posY,posZ,4));
		if(result==2)//if 2 we are on the food and will pick it up
		{
			w.removeFood(posX,posY,posZ);//food removed
			foodCarried = true;
		}
	}
	private int search4Food(ArrayList a,World w) //0 if no food is near, 1 if food is near , 2 if food is on current cell
	{
		Food target=null;	//searches 3 square radius for food cells
		for(Food f:w.food)
		{
			if(f.posX>=posX-3&&f.posX<=posX+3&&f.posY>=posY-3&&f.posY<=posY+3)
			{
				target=f;
				break;
			}
		}
		if(target!=null)
		{
			if(moveTowards(target.posX,target.posY,target.posZ,w))
			{
				return 2;
			}
			else
				return 1;
		}
		return 0;
	}
	private void depositFood(World w)//simply causes the ant to travel to the store and deposit the food
	{
		if(this.moveTowards(w.store.posX,w.store.posY,w.store.posZ, w))
		{
			w.store.amount= w.store.amount + gatherSkill;
			gatherSkill= gatherSkill+5;
			foodCarried = false;
			activity=0;
		}
		else
		{
			w.pheromones.add(new Pheromone(posX,posY,posZ,5));//drop our pheromones on way
		}
	}
	
	private void mate(World w)	//mate with the queen to produce a new egg
	{
		activity = 3;	//locks activity
		if(this.moveTowards(w.queen.posX,w.queen.posY,w.queen.posZ,w))//move to the queen
		{
			w.queen.carrying=true;//impregnate queen
			energy=0;	//energy is reduced
			activity=0;	//activity is released
		}
		else
		{
			if(w.getPheromoneType(posX,posY,posZ)==1||w.getPheromoneType(posX,posY,posZ)==3)
				activity=0;//if pheromones of another mating ant is found or queen is not ready to be mated abandon mate act
			else
				w.pheromones.add(new Pheromone(posX,posY,posZ,3)); //drop our pheromones on way
		}
	}
	
	private void eat(World w)	//simple method, move towards store and eat to restore food level
	{
		if(this.moveTowards(w.store.posX,w.store.posY,w.store.posZ,w))
		{
			w.store.amount = (int) (w.store.amount - (100-this.foodLevel));
			foodLevel = 100;
		}
		else
			w.pheromones.add(new Pheromone(posX,posY,posZ,8));//appropriate pheromone left
		
	}
	
	private void drink(World w)//finds closest water source and drinks to replenish water level
	{
		Water closest = null;
		double distance = -1;
		for(Water w1 :w.water)
		{
			double d = getDistance(w1.posX,w1.posY);
			if(distance == -1 ||distance>d)
			{
				distance = d;
				closest = w1;
			}
		}
		if(closest!=null)
		{
			if(moveTowards(closest.posX,closest.posY,closest.posZ,w))
			{
				waterLevel = 100;
			}
			else
				w.pheromones.add(new Pheromone(posX,posY,posZ,9));//appropriate pheromone left
		}
	}
	
	private void removeRubbish(World w)//removes any rubbish from colony
	{
		activity = 1;		//activity locked
		Rubbish closest = null;
		double distance = -1;
		for(Rubbish r :w.rubbish)//find closest rubbish
		{
			double d = getDistance(r.posX,r.posY);
			if(distance == -1 ||distance>d)
			{
				distance = d;
				closest = r;
			}
		}
		if(closest!=null)
		{
			if(moveTowards(closest.posX,closest.posY,closest.posZ,w))
			{
				activity =0;
				w.removeRubbish(closest.posX,closest.posY,closest.posZ);
			}
			else
				w.pheromones.add(new Pheromone(posX,posY,posZ,7));//leave appropriate pheromones
		}	
	}
	
	
	private void sleep(World w)//ant returns to den to sleep
	{
		if(moveTowards(10,14,1,w))
		{
			foodLevel = foodLevel+.1;//food level and waterlevel decrease slower when sleeping
			waterLevel = waterLevel+.1;
			if(energy<100)
				energy++;
		}
		else
			w.pheromones.add(new Pheromone(posX,posY,posZ,6));//leave pheromones on way
	}
	
	private double getDistance(int x,int y)
	{
		return Math.sqrt((x-posX)*(x-posX)+(y-posY)*(y-posY));	//used for finding distance to a node.
	}
}