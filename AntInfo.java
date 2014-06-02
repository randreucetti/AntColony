import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;


public class AntInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel header;							//a JLabel for the header
	private JLabel timesMated;						//a JLabel for the times the queen produces an egg
	private JLabel totalNumberOfAnts;				//a JLabel for the total number of ants that have existed
	int start;										//an int for the number of ants that orignally started
	
	AntInfo()
	{
		super(new GridLayout(4,1));					//set the layout of the window
		header = new JLabel("Colony Info",JLabel.CENTER);		//set the header to say "Time Control" and put it in the centre
		header.setFont(new Font("Sans", Font.BOLD, 18));		//set the font as sans, make it bold and set it to size 18
		header.setForeground(Color.BLUE);						//set the colour to blue
		add(header);											//add the header to the window
		timesMated = new JLabel();								//a new JLabel for the amount of eggs produced
		totalNumberOfAnts = new JLabel();						//a new JLabel for the total number of ants
		this.add(timesMated, BorderLayout.SOUTH);				//add the JLabel for the amount of eggs produced
		this.add(totalNumberOfAnts);							//add the JLabel for the total number of ants
		this.setBounds(600,200,200,200);						//set the size of the window
	}
	
	public void update(World w)										//update the window
	{
		//-----[This piece of code shows how many times the ants have mated with the queen ant]------
		timesMated.removeAll();									//remove everything in timesMated
		timesMated.setText("Total Eggs Laid: " + w.eggsLaid);		//set the new text for timesMated, taking into account noOfEggs from the world class								
		//-------------------------------------------------------------------------------------------
		
		//-------[This piece of code shows how many ants have existed since the colony began]--------
		totalNumberOfAnts.removeAll();							//remove evetything in totalNumberOfAnts
		totalNumberOfAnts.setText("Total Ants Ever: " + w.antsExisted);	//set the new text for totalNumberOfAnts, taking into account noOfAntsExisted from the world class added to start
		//-------------------------------------------------------------------------------------------
	}
}