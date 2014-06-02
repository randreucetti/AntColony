import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class AntGUI extends JFrame
{
	static boolean playOption;					//a boolean used for the play buttons
	static boolean pauseOption;					//a boolean used for the pause buttons
	static boolean paused;						//a boolean which will be true when the program is paused
	
	private static final long serialVersionUID = 1L;
	AntGUI()
	{
		JMenuBar options = new JMenuBar();			//creating the menu bar
		
		JMenu file = new JMenu("File");				//the first submenu, called "File"
		JMenu time = new JMenu("Time Control");		//the second submenu, called "Time Control"
		JMenu help = new JMenu("Help");				//the third submenu, called "Help"
	
		//Allows the user to press Alt and F to open File----------
		file.setMnemonic(KeyEvent.VK_F);		
		//---------------------------------------------------------
		
		//Allows the user to press Alt and T to open Time Control--
		time.setMnemonic(KeyEvent.VK_T);
		//---------------------------------------------------------
		
		//Allows the user to press Alt and H to open Help----------
		help.setMnemonic(KeyEvent.VK_H);
		//---------------------------------------------------------
		
		//Create the different options which will go in the submenus--
		JMenuItem endProgram = new JMenuItem("Exit (ALT + E)");			//an option to exit
		JMenuItem play = new JMenuItem("Play");							//an option to resume the program if paused
		JMenuItem pause = new JMenuItem("Pause");						//an option to pause the program
		JMenuItem about = new JMenuItem("About Virtual Ant Colony");	//an option which opens an about file
		//------------------------------------------------------------
		
		//Puts a line above Exit
		endProgram.add(new JSeparator());
		//---------------------------------------------------------

		//Allows the user to press Alt and E to exit the program---
		//(note: this only works if the File menu is already open)-
		endProgram.setMnemonic(KeyEvent.VK_E);
		//---------------------------------------------------------
		
		//This opens the about file if the user clicks "About Virtual Ant Colony"----
		about.addActionListener(new ActionListener() 
		{
		      public void actionPerformed(ActionEvent event) 
		      {
		    	  new About();							//opens the about file
		      }
		});		
		//---------------------------------------------------------------------------
		
		//This resumes the program if the user presses "Play"------------------------
		play.addActionListener(new ActionListener() 
		{
		      public void actionPerformed(ActionEvent event) 
		      {
		    	  playOption = true;					//set the play to true
		    	  pauseOption = false;					//set the pause to false
		      }
		});		
		//---------------------------------------------------------------------------
		
		//This pauses the program if the user presses "Pause"------------------------
		pause.addActionListener(new ActionListener() 
		{
		      public void actionPerformed(ActionEvent event) 
		      {
		    	  	pauseOption = true;					//set the pause to true
		    	  	playOption = false;					//set the play to false
		      }
		});
		//---------------------------------------------------------------------------
		
		//This ends the program if the user presses "Exit"---------------
		endProgram.addActionListener(new ActionListener() 
		{
		      public void actionPerformed(ActionEvent event) 
		      {
		        System.exit(0);							//exit the program
		      }
		});	
		//---------------------------------------------------------------

		//Add the options for File---------------------------------
		file.add(endProgram);
		//---------------------------------------------------------
		
		//Add the options for Time Control-------------------------
		time.add(play);
		time.add(pause);
		//---------------------------------------------------------
		
		//Add the options for Help
		help.add(about);
		//---------------------------------------------------------

		//Add the different submenus-------------------------------
		options.add(file);
		options.add(time);
		options.add(help);
		//---------------------------------------------------------

		//Add entire the menubar-----------------------------------
		setJMenuBar(options);
		this.setTitle("Ant Colony");				//sets the title at the top of the program
        this.setSize(808,655);						//sets the size of the window to 808, 655
        
        this.setLocationRelativeTo(null);			//places the window in the middle of the screen
		this.setVisible(true);						//makes the program visable
		this.setResizable(false);					//this makes sure that the window can't be resized
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//this sets the default closing option to exit
	}
	
	public static void main(final String [] args) throws InterruptedException
	{
		paused = false;								//when the program initally starts, paused is false, as the program is running
		AntGUI gui = new AntGUI();					//creates a new AntGUI called gui
		gui.setLayout(null);						//sets the layout of gui
		World w = new World();						//creates a new World called w
		AboveGround ag = new AboveGround();			//creates a new AboveGround  called ag
		BelowGround bg = new BelowGround();			//creates a new	BelowGround called bg
		AntInfo antInfo = new AntInfo();			//creates a new AntInfo called antInfo
		WorldInfo worldInfo = new WorldInfo();		//creates a new	WorldInfo called worldInfo
		TimePanel timePanel = new TimePanel();		//creates a new	TimePanel called timepanel
		gui.add(ag);								//add ag to gui
		gui.add(bg);								//add bg to gui
		gui.add(antInfo);							//add antInfo to gui
		gui.add(worldInfo);							//add worldInfo to gui
		gui.add(timePanel);							//add timePanel to gui
		gui.repaint();								//repaint gui, with all the new infomation in it
		gui.setVisible(true);						//make sure gui can be seen
		int speed = 375;							//sets the initial speed of the program
		while(true)	
		{
			w.update();								//updates the world
			ag.update(w);							//updates ag
			bg.update(w);							//updates bg
			antInfo.update(w);						//updates antInfo
			worldInfo.update(w);						//updates worldInfo
			
			speed = (int) (2000 - ((timePanel.update()*17.5)+250))/3;		//this is the formula used to calculate the speed
			
			if(timePanel.pauseButton == true)		//if the boolean pauseButton in the timePanel class is true
			{
				paused = true;						//paused becomes true
				timePanel.playButton = false;		//the playButton in timePanel becomes false
				pauseOption = true;					//pauseOption becomes true
				playOption = false;					//playOption becomes false
			}
			
			if(pauseOption == true)					//if pauseOption is true
			{
				paused = true;						//paused becomes true
				timePanel.playButton = false;		//the playButton in timePanel becomes false
				timePanel.pauseButton = true;		//the pauseButton in timePanel becomes true
				playOption = false;					//playOption becomes false
			}
			
			if(timePanel.playButton == true)		//if the timePanel playButton is true
			{
				paused = false;						//paused becomes false
				timePanel.pauseButton = false;		//the pauseButton in timePanel becomes false
				pauseOption = false;				//pauseOption becomes false
				playOption = true;					//playOption becomes true
			}
			
			if(playOption == true)					//if playOption is true
			{
				paused = false;						//paused becomes false
				timePanel.pauseButton = false;		//the pauseButton in timePanel becomes false
				timePanel.playButton = true;		//the playButton in timePanel becomes true
				pauseOption = false;				//pauseOption becomes false
			}

			//----[This is how the program knows where to play or pause]----
			if(paused ==  true)						//if paused is true
			{
				while(paused == true)				//while pause is true
				{
					//this checks to see if the user has pressed either of the play buttons
					if(playOption == true || timePanel.playButton == true) //if either play buttons are pressed
					{
						pauseOption = false;						//pauseOption becomes false
						timePanel.pauseButton = false;				//the pauseButton in the timePanel becomes false
						paused = false;								//paused becomes false
					}
					//if play hasn't been pressed, it goes through the loop again and again until a play button is pressed
				}
			}
			//--------------------------------------------------------------
			
			//-----[This set the speed of the ants movements, where the user puts in the desired speed using the slider]-----
			if(timePanel.playButton == true)				//if the playButton in timeContol is true
			{
				speed = (int) (2000 - ((timePanel.update()*17.5)+250))/3;			//the speed is set using this formula
			}
			
			Thread.sleep(speed);							//the ants go at the speed given, sleeping for a certain time before carrying out a new action
		}
	}
}
