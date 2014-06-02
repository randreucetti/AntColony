/*Ross Andreucetti
  Oisin Colm Kelly
  Dublin City University
  2011 */

import java.util.*;

public class World //world class which stores everything and updates their actions
{	
	int antsExisted=0;	//used for stat keeping
	int eggsLaid=0;
	int stepsTaken = 0;	//how many steps have been taken
	
	ArrayList<Ant> ants = new ArrayList<Ant>();//all items on map are stored in arraylists
	ArrayList<Food> food = new ArrayList<Food>();
	ArrayList<Water> water = new ArrayList<Water>();
	ArrayList<Rubbish> rubbish = new ArrayList<Rubbish>();
	ArrayList<Pheromone> pheromones = new ArrayList<Pheromone>();
	ArrayList<Pheromone> queenPheromones = new ArrayList<Pheromone>();
	
	QueenAnt queen;		//other items present are queen ant, egg used for ants young ants, 4 ladders and the store.
	Egg		egg = new Egg();
	Ladder ladder1above;
	Ladder ladder1below;
	Ladder ladder2above;
	Ladder ladder2below;
	Store store;
		
	World()
	{	
		queen = new QueenAnt(1,13,1);//default location for queen, ladder and store
		antsExisted++;
		ladder1above = new Ladder(10,7,0);
		ladder1below = new Ladder(10,7,1);
		ladder2above = new Ladder(20,7,0);
		ladder2below = new Ladder(20,7,1);
		store = new Store(0,5,1);
		
		for(int i=0;i<5;i++)	//creates a number of ants randomly on our map, 5 in this case.
		{
			Random generator = new Random();
			int x = generator.nextInt(30);
			int y = generator.nextInt(15);
			int z = generator.nextInt(2);
			while(!isFree(x,y,z))
			{
				x = generator.nextInt(30);
				y = generator.nextInt(15);
				z = generator.nextInt(2);
			}
			ants.add(new Ant(x,y,z));
			antsExisted++;
		}
		for(int i=0;i<5;i++)		//adds 5 food nodes to upper level
		{			
			Random generator = new Random();
			int x = generator.nextInt(30);
			int y = generator.nextInt(15);
			int z = 0;
			while(!isFree(x,y,z))
			{
				x = generator.nextInt(30);
				y = generator.nextInt(15);
			}
			food.add(new Food(x,y,z));
		}
		for(int i=0;i<5;i++)		//adds 5 water nodes to lower level
		{			
			Random generator = new Random();
			int x = generator.nextInt(30);
			int y = generator.nextInt(15);
			int z = 1;
			while(!isFree(x,y,z))
			{
				x = generator.nextInt(30);
				y = generator.nextInt(15);
			}
			water.add(new Water(x,y,z));
		}
		queenPheromones.add(0,new Pheromone(0,12,1,2));	//adds 8 pheromones around the queens location
		queenPheromones.add(1,new Pheromone(0,13,1,2));
		queenPheromones.add(2,new Pheromone(0,14,1,2));
		queenPheromones.add(3,new Pheromone(1,12,1,2));
		queenPheromones.add(4,new Pheromone(1,14,1,2));
		queenPheromones.add(5,new Pheromone(2,12,1,2));
		queenPheromones.add(6,new Pheromone(2,13,1,2));
		queenPheromones.add(7,new Pheromone(2,14,1,2));
		
		
	}

	public boolean isFree(int x,int y,int z)//used for checking if a node is free
	{										// returns true if free, false if otherwise
		for(Food f : food)
		{
			if(f.posX==x&&f.posY==y&&f.posZ==z)
				return false;
		}
		for(Water w : water)
		{
			if(w.posX==x&&w.posY==y&&w.posZ==z)
				return false;
		}
		for(Rubbish r : rubbish)
		{
			if(r.posX==x&&r.posY==y&&r.posZ==z)
				return false;
		}
		
		if(queen.posX==x&&queen.posY==y&&queen.posZ==z)
			return false;
		
		if(ladder1above.posX==x&&ladder1above.posY==y&&ladder1above.posZ==z)
			return false;
		if(ladder1below.posX==x&&ladder1below.posY==y&&ladder1below.posZ==z)
			return false;
		
		if(ladder2above.posX==x&&ladder2above.posY==y&&ladder2above.posZ==z)
			return false;
		if(ladder2below.posX==x&&ladder2below.posY==y&&ladder2below.posZ==z)
			return false;
		if(store.posX==x&&store.posY==y&&store.posZ==z)
			return false;
		if(x==10&&y==14&&z==1)
			return false;
		return true;
	}
	
	public void update()//gets all the ants decisions and updates anything else nessescary in the world
	{
		stepsTaken++;//increments steps taken
		if(ants.size()>0)
		{
			Iterator it = ants.iterator();
			while(it.hasNext())
			{
				Ant a = (Ant) it.next();
				if(a.stepsMade>2500||a.waterLevel==0||a.foodLevel==0)//ants die if they are too old, thirsty or hungry
				{
					it.remove();
				}
				if(a.bladder==200 && isFree(a.posX,a.posY,a.posZ))
				{
					rubbish.add(new Rubbish(a.posX,a.posY,a.posZ));//ants drop rubbish if bladder is full
					a.bladder=0;
				}
				else
					a.getMove(this);//gets the ants move if they don't need to create feces
			}
		}
		if(pheromones.size()>0)		//pheromones last for 10 steps and are removed afterwards
		{
			Iterator it = pheromones.iterator();
			while(it.hasNext())
			{
				Pheromone p = (Pheromone) it.next();
				p.movesLeft--;
				if(p.movesLeft==0)
					it.remove();
			}
		}
	
			for(Pheromone p:queenPheromones)	//produces apporiate pheromoes depending if queen is carrying or not
			{
				if(queen.carrying)
					p.type=1;
				else
					p.type=2;
			}
			if(queen.carrying&&egg.exists&&stepsTaken%10==0)//eggs hatch when ready and turn to ants
			{
				egg.hatch();
				queen.carrying=false;
				ants.add(new Ant(0,14,1));
				antsExisted++;//increments ants
			}
			else if(queen.carrying&&stepsTaken%10==0)//queen lays the egg
			{
				egg.lay();
				eggsLaid++;//increments eggs
			}
			
	}
	public void removeRubbish(int x,int y,int z)//called by ants to remove rubbish from a certain location
	{
		if(rubbish.size()>0)
		{
			Iterator it = rubbish.iterator();
			while(it.hasNext())
			{
				Rubbish r = (Rubbish) it.next();
				if(r.posX==x&&r.posY==y&&r.posZ==z)
					it.remove();
			}
		}
	}
	public void removeFood(int x,int y,int z)//called by Ant when a food source has been harvested to remove it from world
	{
		if(food.size()>0)
		{
			Iterator it = food.iterator();
			while(it.hasNext())
			{
				Food r = (Food) it.next();
				if(r.posX==x&&r.posY==y&&r.posZ==z)
					it.remove();
			}
			Random generator = new Random();
			int x1 = generator.nextInt(30);
			int y1 = generator.nextInt(15);
			int z1 = 0;
			while(!isFree(x1,y1,z1))
			{
				x1 = generator.nextInt(30);
				y1 = generator.nextInt(15);
			}
			food.add(new Food(x1,y1,z1));		//creates a new food source somewhere on map
		}
	}
	public int getPheromoneType(int x,int y,int z)//called by ant class to check what pheromone type is on a certain cell
	{
		if(pheromones.size()>0)
		{
			for(Pheromone p:pheromones)
			{
				if(p.posX==x&&p.posY==y&&p.posZ==z)
					return p.type;
			}
		}
		for(Pheromone p:queenPheromones)
		{
			if(p.posX==x&&p.posY==y&&p.posZ==z)
				return p.type;
		}
		return 0;//returns 0 if not pheromone is present
	}
}