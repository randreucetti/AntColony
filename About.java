import javax.swing.*;

public class About extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public About() 
	{
		build();
	}
	
	public final void build()
	{      
		//-----------------------[CONTENTS]------------------------
		JLabel info = new JLabel("<html>About the virtual ant colony: " +
				"<p> <br>" +
				"Version 1.0" +
				"</p> <br>" +
				"<p>" +
			    "Press pause to make the ants stop temporarily. <br>" +
			    "Press play to make them move again. <br>" +
			    "Use the slider to control the speed of the ants" +
				"</p> <br> <br>" +
				"<p>" +
				"Developed by Ross Andreucetti and Oisín Colm Kelly" +
				"</p> <br>" + 
				"<br>" +
				"<p>" +
				"Copyright 2011" +
				"</p>");
		add(info);
		//---------------------------------------------------------
		
		//---------------------[WINDOW SIZE]-----------------------
		this.setSize(400, 300);
		//---------------------------------------------------------
		
		//--------------[TITLE AT THE TOP OF THE WINDOW]-----------
		setTitle("About the Virtual Ant Colony");
		//---------------------------------------------------------
		
		//-----[SET THE LOCATION TO THE MIDDLE OF THE SCREEN]------
		setLocationRelativeTo(null);
		//---------------------------------------------------------
		
		//----------[MAKE SURE THE WINDOW CAN BE SEEN]-------------
		setVisible(true);
		//---------------------------------------------------------
	}
}