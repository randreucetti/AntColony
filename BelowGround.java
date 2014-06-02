import javax.swing.*;

import java.awt.*;

public class BelowGround extends JPanel
{
	private static final long serialVersionUID = 1L;
	//----[The following creates the basic layout of the map]-----
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		for(int i=20;i<600;i=i+20)
			g2d.drawLine(i,0,i,300);
		for(int i=20;i<300;i=i+20)
			g2d.drawLine(0,i,600,i);
	}
	//------------------------------------------------------------

	BelowGround()
	{
		this.setLayout(null);											//sets no particular layout
		this.setBackground(new Color(139,69,19));						//sets the background colour to brown
		this.setBounds(0,300,600,300);									//sets the size of the window
		this.setBorder(BorderFactory.createLineBorder(Color.black));	//the boarder lines between each square is set to black  
	}
	public void update(World w)											//updates the map
	{
		this.removeAll();												//remove all images already on the map

		for(Ant a :w.ants)												//go through the array of ants
		{
			if(a.posZ == 1&&!(a.posX==10&&a.posY==14))					//if the ants posZ is 1 and its posX is 10 and its posY is 14
			{
				JLabel ant = new JLabel(new ImageIcon(getClass().getResource("image/ant"+a.direction+".gif")));		//creates a new JLabel for the ants, with an image showing which direction it's going
				ant.setBounds(a.posX*20+1,a.posY*20+1,19,19);			//sets the postion of the ant
				this.add(ant);											//adds the ant to the map
			
			}
		}
		for(Water d :w.water)											//go through the array of water
		{
			if(d.posZ == 1)												//if the waters posZ is 1
			{
				JLabel water = new JLabel(new ImageIcon(getClass().getResource("image/water.gif")));	//create a new JLabel for the water, with a water image associated with it
				water.setBounds(d.posX*20+1,d.posY*20+1,19,19);			//set the location of the water
				this.add(water);										//add the food to the map
			}
		}
		for(Rubbish r :w.rubbish)										//go through the array of rubbish
		{
			if(r.posZ == 1)												//is the rubbish's posZ is 1
			{
				JLabel rubbish = new JLabel(new ImageIcon(getClass().getResource("image/poo.gif")));	//create a new JLabel for the rubbish, with a rubbish image associated with it
				rubbish.setBounds(r.posX*20+1,r.posY*20+1,19,19);		//set the location of the rubbish
				this.add(rubbish);										//add the rubbish to the map
			}
		}
		for(Pheromone p :w.pheromones)									//go through the array of pheromones
		{
			if(p.posZ == 1)												//if the pheromones posZ is 1
			{
				JLabel pheromone = new JLabel(new ImageIcon(getClass().getResource("image/pheromone"+p.type+".gif")));		//create a new JLabel for the pheromone, with a pheromone image (depending on the type) associated with it 
				pheromone.setBounds(p.posX*20+1,p.posY*20+1,19,19);		//set the pheromones location
				this.add(pheromone);									//add the pheromone to the map
			}
		}
		for(Pheromone p :w.queenPheromones)								//go through the array of the queens pheromones
		{
			if(p.posZ == 1)												//if the pheromones posZ is 1
			{
				JLabel pheromone = new JLabel(new ImageIcon(getClass().getResource("image/pheromone"+p.type+".gif")));		//create a new JLabel for the pheromone, with a pheromone image (depending on the type) associated with it 
				pheromone.setBounds(p.posX*20+1,p.posY*20+1,19,19);		//set the pheromones location
				this.add(pheromone);									//add the pheromone to the map
			}
		}
		
		if(w.egg.exists)												//if an egg exists on the grid
		{
			JLabel egg = new JLabel(new ImageIcon(getClass().getResource("image/egg.gif")));	//create a new JLabel for the egg, with an egg image associated with it
			egg.setBounds(w.egg.posX*20+1,w.egg.posY*20+1,19,19);		//set the eggs location
			this.add(egg);												//add the pheromone to the map
		}
		
		JLabel ladder1 = new JLabel(new ImageIcon(getClass().getResource("image/ladder.gif")));		//create a JLabel for the first ladder, with a ladder image associated with it
		JLabel ladder2 = new JLabel(new ImageIcon(getClass().getResource("image/ladder.gif")));		//create a JLabel for the second ladder, with a ladder image associated with it
		ladder1.setBounds(w.ladder1below.posX*20+1,w.ladder1below.posY*20+1,19,19);		//set the location of the first ladder
		ladder2.setBounds(w.ladder2below.posX*20+1,w.ladder2below.posY*20+1,19,19);		//set the location of the second ladder
		this.add(ladder1);			//add the first ladder to the map
		this.add(ladder2);			//add the second ladder to the map
		
		JLabel den = new JLabel(new ImageIcon(getClass().getResource("image/den.gif")));		//create a new JLabel for the den, with the den image associated with it
		den.setBounds(10*20+1,14*20+1,19,19);							//set the dens location
		this.add(den);													//add the den to the map
		
		JLabel queen = new JLabel(new ImageIcon(getClass().getResource("image/queen.gif")));	//create a new JLabel for the queen ant, with the queen ant image associated with it
		queen.setBounds(w.queen.posX*20+1,w.queen.posY*20+1,19,19);		//set the queen ants location
		this.add(queen);												//add the queen to the map
			
		JLabel store = new JLabel(new ImageIcon(getClass().getResource("image/store.gif")));	//create a new JLabel for the food store, with the food store image associated with it
		store.setBounds(w.store.posX*20+1,w.store.posY*20+1,19,19);		//set the food store's location
		this.add(store);												//add the food store to the map
		
		this.repaint();				//repaint the map
	}
}
