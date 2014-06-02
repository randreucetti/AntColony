import javax.swing.*;

import java.awt.*;

public class AboveGround extends JPanel
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

	AboveGround()
	{
		this.setLayout(null);											//sets no particular layout
		this.setBackground(Color.green);								//sets the background colour to green
		this.setBounds(0,0,600,300);									//sets the size of the window
		this.setBorder(BorderFactory.createLineBorder(Color.black));	//the boarder lines between each square is set to black  
	}
	
	public void update(World w)											//updates the map
	{
		this.removeAll();												//remove all images already on the map
		for(Ant a :w.ants)												//go through the array of ants
		{
			if(a.posZ == 0)												//if the ants posZ is 0
			{
				JLabel ant = new JLabel(new ImageIcon(getClass().getResource("image/ant"+a.direction+".gif")));	//creates a new JLabel for the ants, with an image showing which direction it's going
				ant.setBounds(a.posX*20+1,a.posY*20+1,19,19);			//sets the postion of the ant
				this.add(ant);											//adds the ant to the map
			}
		}
		for(Food f :w.food)												//go through the array of food
		{
			if(f.posZ == 0)												//if the foods posZ is 0
			{
				JLabel food = new JLabel(new ImageIcon(getClass().getResource("image/leaf.gif")));		//create a new JLabel for the food, with a leaf image associated with it
				food.setBounds(f.posX*20+1,f.posY*20+1,19,19);					//set the location of the food
				this.add(food);													//add the food to the map
			}
		}
		for(Rubbish r :w.rubbish)										//go through the array of rubbish
		{
			if(r.posZ == 0)												//is the rubbish's posZ is 0
			{
				JLabel rubbish = new JLabel(new ImageIcon(getClass().getResource("image/poo.gif")));	//create a new JLabel for the rubbish, with a rubbish image associated with it
				rubbish.setBounds(r.posX*20+1,r.posY*20+1,19,19);				//set the location of the rubbish
				this.add(rubbish);												//add the rubbish to the map
			}
		}
		for(Pheromone p :w.pheromones)									//go through the array of pheromones
		{
			if(p.posZ == 0)												//if the pheromones posZ is 0
			{
				JLabel pheromone = new JLabel(new ImageIcon(getClass().getResource("image/pheromone"+p.type+".gif")));	//create a new JLabel for the pheromone, with a pheromone image (depending on the type) associated with it 
				pheromone.setBounds(p.posX*20+1,p.posY*20+1,19,19);		//set the pheromones location
				this.add(pheromone);									//add the pheromone to the map
			}
		}
		
		JLabel ladder1 = new JLabel(new ImageIcon(getClass().getResource("image/ladder.gif")));		//create a JLabel for the first ladder, with a ladder image associated with it
		JLabel ladder2 = new JLabel(new ImageIcon(getClass().getResource("image/ladder.gif")));		//create a JLabel for the second ladder, with a ladder image associated with it
		ladder1.setBounds(w.ladder1above.posX*20+1,w.ladder1above.posY*20+1,19,19);		//set the location of the first ladder
		ladder2.setBounds(w.ladder2above.posX*20+1,w.ladder2above.posY*20+1,19,19);		//set the location of the second ladder
		this.add(ladder1);						//add the first ladder to the map
		this.add(ladder2);						//add the second ladder to the map
		
		this.repaint();							//repaint the map
	}
}
