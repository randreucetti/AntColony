import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimePanel extends JPanel implements ActionListener
{
	public static final long serialVersionUID = 1L;
	public JLabel header;							//a JLabel for the header
	public JSlider timeSpeed;						//a JSlider to control the speed of time
	public JButton play;							//a JButton for the play button
	public JButton pause;							//a JButton for the pause button
	public JLabel currentSpeed;						//a JLabel to show the current speed
	boolean pauseButton;							//a boolean for the pause button
	boolean playButton;								//a boolean for the play button

	TimePanel()
	{
		super(new GridLayout(5,1));					//set the layout of the window
		header = new JLabel("Time Control",JLabel.CENTER);	//set the header to say "Time Control" and put it in the centre
		header.setFont(new Font("Sans", Font.BOLD, 18));	//set the font as sans, make it bold and set it to size 18
		header.setForeground(Color.BLUE);					//set the colour to blue
		add(header);										//add the header to the window
		timeSpeed = new JSlider();							//create a new JSlider
		timeSpeed.setBorder(BorderFactory.createTitledBorder("Speed of Time"));	//set the border of the slider and give it a title of "Speed of Time"
		timeSpeed.setMajorTickSpacing(20);					//set the spacing of the larger lines to 20
		timeSpeed.setMinorTickSpacing(5);					//set the spacing of the smaller lines to 5
		timeSpeed.setPaintTicks(true);						//show the lines
		add(timeSpeed);										//add the slider
		
		pause = new JButton("Pause");						//create a new button for pause
		pause.addActionListener(this);						//have an action listener for pause
		add(pause);											//add pause to the window
		
		play = new JButton("Play");							//create a new button for play
		play.addActionListener(this);						//have an action listener for play
		add(play);											//add play to the window
		
		currentSpeed = new JLabel();						//create a JLabel for the current speed
		add(currentSpeed);									//add current speed to the window
		this.setBounds(600,0,200,200);						//set the size of the window
	}
	
	//this is used to check which button is pressed and carrys out commands depending on the button-------
	public void actionPerformed(ActionEvent e)
	{
		JButton Pl = (JButton)e.getSource();					//get the button pressed, store it in Pl
		if(Pl == pause)											//if Pl is pause
		{
			pauseButton = true;									//pauseButton is true
			playButton = false;									//playButton is false
		}
		if(Pl == play)											//if Pl is play
		{
			playButton = true;									//playButton is true
			pauseButton = false;								//pauseButton is false
		}
	}
	//----------------------------------------------------------------------------------------------------
	
	public int update()							//updates the window
	{
		currentSpeed.removeAll();				//removes the text in currentSpeed
		currentSpeed.setText("Current Speed: " + timeSpeed.getValue());	//set the new text of current speed, taking the new speed into account
		return timeSpeed.getValue();
	}
}