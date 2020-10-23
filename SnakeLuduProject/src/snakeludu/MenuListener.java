package snakeludu;

import java.awt.event.*;
public class MenuListener extends SnakeLudu implements ActionListener
{
	ShowMessage sm=new ShowMessage();
	boolean gamecontinue=false;
	public void actionPerformed(ActionEvent a)
	{
		String label = a.getActionCommand();
		
		if(label.equals("About"))
		{
			sm.showWarning(300, 100, " It is a two player clasic game.\n");
		}
		else if(label.equals("Rules"))
		{
				sm.showWarning(600, 100, "To play this game first Click on 'CLICK HERE' to get the dice value.\n"
					+"\nFor Moving GOB Click on the GOB and then click on the grid where GOB move.");
		}
		else if(label.equals("Continue Game"))
		{
			gamecontinue=true;
		}
		else if(label.equals("Exit Game"))
		{
			System.exit(0);
		}
		
		else if(label.equals("OK"))
		{
			modal.setVisible(false);
			
		}
	
	}
	
	
	public void sendData()
	{
		loadToarray();
		for(int i=0;i<167;i++)
		{
			String s="";
			s=s+array[i];
			
		}
	}
}
