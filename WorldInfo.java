import javax.swing.*;
import java.awt.event.*;   
import java.awt.*;


public class WorldInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	//these JLabels will be present in the WorldInfo window
	private JLabel header;					//a JLabel called header
	private JLabel numberAnts;				//a JLabel called numberAnts
	private JLabel foodStore;				//a JLabel called foodStore
	private JLabel timeElapsed;				//a JLabel called timeElapsed
	
	javax.swing.Timer timer;				//a timer
	
	int hours = 0;							//an int for the number of hours 
	int counter = 0;						//an int for the counter
	java.text.DecimalFormat decimal = new java.text.DecimalFormat("00"); //this sets a decimal format which will be used for the timer

	WorldInfo()
	{		
		super(new GridLayout(4,1));								//sets the layout of the window
		header = new JLabel("World Info",JLabel.CENTER);		//creates a header for the top of the window called "World Info"
		header.setFont(new Font("Sans", Font.BOLD, 18));		//sets the font of the header to Sans, with a bold font and at size 18
		header.setForeground(Color.BLUE);						//sets the colour of the header to blue
		add(header);											//adds the header
		numberAnts = new JLabel();								//a JLabel for the number of ants, called numberAnts
		foodStore = new JLabel();								//a JLabel for the amount in the food store, called foodStore
		timeElapsed = new JLabel("Time Elapsed: 00:00:00");		//a JLabel for the timer, called timeElapsed
		
		//----------------[The following piece of code is for the session timer]-------------------
		ActionListener sessionTime = new ActionListener()
		{  
			public void actionPerformed(ActionEvent event)
			{  
		        counter++;  						//the counter increases
		        //sets the text for timeElapsed to look like "Time Elapsed: 00:07:45"
		        timeElapsed.setText("Time Elapsed: " + decimal.format(hours) + ":" + decimal.format(counter / 60)+":"+ decimal.format(counter % 60));
		        //this is used to get the number of hours
				if(counter % 3599 == 0 && counter != 0)		//if the value of the counter mod 3599 is 0 and the counter isn't 0
				{
					hours++;				//increase the number of hours by 1
					counter = -1;			//give the counter the value of -1
				}
			}
		};
		
		timer = new javax.swing.Timer(1000,sessionTime);
		timer.start();						//starts the timer
		
		//------------------------------------------------------------------------------

	    this.add(numberAnts,BorderLayout.SOUTH);		//adds numberAnts to the window, sets the layout to the bottom
	    this.add(foodStore);							//adds foodStore to the window
		this.add(timeElapsed); 							//adds timeElapsed to the window
		this.setBounds(600,400,200,200);				//sets the size of the window
	}
	
	public void update(World w)
	{
		//-----------[The following piece of code shows the number of ants in the world]-----------
		numberAnts.removeAll();						//remove all existing text
		numberAnts.setText("Total ants currently: " + (w.ants.size()+1));	//set the new text, getting the amount of ants in the array of ants
		numberAnts.repaint();						//puts the new text in
		//-----------------------------------------------------------------------------------------
		
		//--------[The following piece of code shows the amount of food in the food store]---------
		foodStore.removeAll();						//remove all existing text
		foodStore.setText("Food in food store: " + w.store.amount);	//set the new text, getting the amount in the food store
		foodStore.repaint();						//puts the new text in
		//-----------------------------------------------------------------------------------------
	}
}