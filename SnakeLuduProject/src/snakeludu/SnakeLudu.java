package snakeludu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SnakeLudu extends Component
{
	static JFrame f;
    static Dialog modal;//used for make a dialogue
    static int dx=745,dy=530,dicevalue=7,playno=0;
    static  AGrid gob[][]=new AGrid[12][12];//for drow border
    static int player1_value,player2_value;
    static int initflag_red=0,initflag_green=0;
    static int selectionflag_red=0,selectionflag_green=0;
    static int prerow,precol;
    static int valid_red=1,valid_green=1;
    static int noof1_red=0,noof1_green=0;
    static int status=0;
    static int row,col;
    static int winflag=-1;
    static int remain_red=99,remain_green=99;
    static int array[]=new int[167];
    static MenuListener mel;
    static ShowMessage sm;
  
    public static void main(String args[])
    {
    	
    	
    	
    		
    	sm=new ShowMessage();
    	AddFrame af=new AddFrame();
    	//create frame.here we call addFrame method of ADDFrame class.
    	f=af.addFrame(900,770,"SNAKE LUDU");
    	SnakeLudu ob=new SnakeLudu();
    	//calling the method for making button
    	ob.addElement(f);
    	f.add(ob);   	
    	//adding mouse listener
    	Mouse ml=new Mouse();
    	f.addMouseListener(ml);
    	//calling method for creating object of grid class  	
    	ob.newGrid();
    	//setting serial of grids
    	ob.setSerial();
    	f.setVisible(true);
    }
	
	public void addElement(JFrame f)
	{	
		//Create Button to connect in broad
		Container con=f.getContentPane();
		
		JPanel jp=new JPanel();
		jp.setBackground(Color.GREEN);
		con.add(jp,BorderLayout.SOUTH);
		//Create Menu bars and Menu Items
		JMenuBar menubar = new JMenuBar();
	    JMenu gamemenu = new JMenu("Game");
	    JMenu helpmenu = new JMenu("Help");
		JMenuItem gameItem1 = new JMenuItem("Pause Game");
		JMenuItem gameItem2 = new JMenuItem("Continue Game");
		JMenuItem gameItem3 = new JMenuItem("Exit Game");
		JMenuItem helpItem1 = new JMenuItem("About");
		JMenuItem helpItem2=new JMenuItem("Rules");
		gamemenu.add(gameItem1);
		gamemenu.add(gameItem2);
		gamemenu.add(gameItem3);
		helpmenu.add(helpItem1);
		helpmenu.add(helpItem2);
		menubar.add(gamemenu);
		menubar.add(helpmenu);
		f.setJMenuBar(menubar);
		//adding listener to menuitems
		mel=new MenuListener();
		gameItem1.addActionListener(mel);
		gameItem2.addActionListener(mel);
		gameItem3.addActionListener(mel);
		helpItem1.addActionListener(mel);
		helpItem2.addActionListener(mel);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLUE);
		
		g.fillRect(697,0,203,770);
		
		//drawing board
		Image im=new ImageIcon("images/board.gif").getImage();
		g.drawImage(im,0,0,697,697,null);
		
		String path="images/"+dicevalue+".gif";
		//drawing dice image
		Image dice=new ImageIcon(path).getImage();
		g.drawImage(dice,dx,dy,100,100,null);
			
        Font fnt = new Font("Times New Roman",Font.BOLD,35);
        g.setFont(fnt);
		int p=playno+1;
		String player="PLAYER "+p;
		if(p==1)
			g.setColor(Color.RED);
		else if(p==2)
			g.setColor(Color.GREEN);
		g.drawString(player,710,200);//draw player number
		
		//draw the full grid
		for(int i=0;i<12;i++)
			for(int j=0;j<12;j++)
			{
				
			    if(gob[i][j].isred==1 && gob[i][j].isgreen==1)
				{
					int gx,gy;
					gx=(j)*54+33;
					gy=(i)*55+25;
					Image rg=new ImageIcon("images/both.gif").getImage();
					g.drawImage(rg,gx,gy,40,40,null);
				}
			    
			    else if(gob[i][j].isred==1)
				{	
					int gx,gy;
					gx=(j)*54+33;
					gy=(i)*55+25;
					Image rg=new ImageIcon("images/red.gif").getImage();
					g.drawImage(rg,gx,gy,40,40,null);
				}
				else if(gob[i][j].isgreen==1)
				{
					int gx,gy;
					gx=(j)*54+33;
					gy=(i)*55+25;
					Image rg=new ImageIcon("images/green.gif").getImage();
					g.drawImage(rg,gx,gy,40,40,null);
				}
			}
		
		//draw border
		if(selectionflag_red==2)
		{
			int gx,gy;
			gx=(precol)*54+28;
			gy=(prerow)*55+21;	
			Image rg=new ImageIcon("images/red_border.gif").getImage();
			g.drawImage(rg,gx,gy,50,50,null);
		}
		else if(selectionflag_green==2)
		{
			int gx,gy;
			gx=(precol)*54+28;
			gy=(prerow)*55+21;	
			Image rg=new ImageIcon("images/green_border.gif").getImage();
			g.drawImage(rg,gx,gy,50,50,null);
		}
		//showing message of winning
		if(winflag==0)
		{	
			sm.showWarning(200,100,"     -----Red Win-----  ");
		}
		else if(winflag==1)
		{	
			sm.showWarning(200,100,"     -----Green Win-----  ");
		}
		
	}
	
	public void mClicked(MouseEvent m)
	{
		int mx=m.getX();
		int my=m.getY();
		
		System.out.println(mx+" "+my);
		
		//identify the click to dice
		if(mx>=dx && mx<=dx+100 && my>=dy && my<=dy+100 )
		{
			if(selectionflag_red==0 && selectionflag_green==0)
			{
				
				//Random random=new Random();
				dicevalue=(int)(Math.random()*10);
				dicevalue=(dicevalue%6)+1;
				//dicevalue=1+random.nextInt(6);
				
				
				if(playno==0)
				{
					player1_value=dicevalue;
					playermove_red();
					mel.sendData();
					f.repaint();
					
				}
				else 
				{
					player2_value=dicevalue;
					playermove_green();				
					mel.sendData();	
					f.repaint();
				}
				if(selectionflag_red==0 && selectionflag_green==0)
				{
					playno=1-playno;	
					
				}
				
				f.repaint();
				mel.sendData();
				f.repaint();
				
			}
			else
			{
				sm.showWarning(200,100,"-----First Complete Your Move-----");
			}
		}
		//identify click to board
		if(mx>=30 && mx<=675 && my>=50 && my<=710 )//&& playno==status)
		{
			
			row=(int)(Math.floor(my-50)/55);
			col=(int)(Math.floor(mx-30)/54);
			System.out.println(row+"x"+col);
			if(gob[row][col].serial==-1)
			{
				sm.showWarning(200,100,"------Invalid Move!!!------");
			}
			
			if(valid_red==1 && playno==0)
				sm.showWarning(300,100,"-----Player 1 first click to dice-----");
			else if(valid_green==1 && playno==1)
				sm.showWarning(300,100,"-----Player 2 first click to dice-----");
			
			if(gob[row][col].isred==1 && selectionflag_red==1)
			{
				prerow=row;
				precol=col;
				selectionflag_red=2;
				
				f.repaint();
				mel.sendData();
			}
			else if(gob[row][col].isred==0 && selectionflag_red==2)
			{
				remain_red=gob[0][4].serial-gob[prerow][precol].serial;
				if(gob[row][col].serial-gob[prerow][precol].serial==player1_value)
				{
					gob[prerow][precol].isred=0;
					gob[row][col].isred=1;
					selectionflag_red=0;
					valid_red=1;
					if(player1_value!=1)
					playno=1-playno;
					else
						noof1_red++;
					dicevalue =7;
					f.repaint();
					checkRed();
					f.repaint();
					dicevalue=7;
					mel.sendData();
				}
				else
				{
					if(player1_value>remain_red)
					{
						selectionflag_red=0;
						valid_red=1;
						if(player1_value!=1)
						playno=1-playno;
						else
							noof1_red++;
						dicevalue=7;
						mel.sendData();
					}
					else
					{
						sm.showWarning(200,100,"-----Invalid move!!!-----");
						
					}
				}
				
			}
			
			if(gob[row][col].isgreen==1 && selectionflag_green==1)
			{
				prerow=row;
				precol=col;
				selectionflag_green=2;
				
				f.repaint();
				mel.sendData();
				
			}
			else if(gob[row][col].isgreen==0 && selectionflag_green==2)
			{
				remain_green=gob[0][4].serial-gob[prerow][precol].serial;
				if(gob[row][col].serial-gob[prerow][precol].serial==player2_value)
				{
					gob[prerow][precol].isgreen=0;
					gob[row][col].isgreen=1;
					selectionflag_green=0;
					valid_green=1;
					if(player2_value!=1)
					playno=1-playno;
					else
						noof1_green++;
					
					f.repaint();
					ckeckGreen();
					f.repaint();
					dicevalue=7;
					mel.sendData();

				}
				else
				{
					if(player2_value>remain_green)
					{
						selectionflag_green=0;
						valid_green=1;
						if(player2_value!=1)
						playno=1-playno;
						else
							noof1_green++;
					
						dicevalue=7;
						mel.sendData();
					}
					else
						sm.showWarning(200,100,"------Invalid Move!!!-----");
				}
			}
			f.repaint();
		}
	}
	//method for identify whether the red gob is in index of stair or snake
	public void checkRed()
	{
		
		if(row==4 && col==11)
		{
			gob[4][11].isred=0;
			gob[11][6].isred=1;
		}
		else if(row==10 && col==7)
		{
			gob[10][7].isred=0;
			gob[9][8].isred=1;
		} 
		else if(row==10 && col==5)
		{
			gob[10][5].isred=0;
			gob[6][9].isred=1;
		} 
		else if(row==7 && col==5)
		{
			gob[7][5].isred=0;
			gob[9][7].isred=1;
		} 
		else if(row==8 && col==1)
		{
			gob[8][1].isred=0;
			gob[7][2].isred=1;
		}
		else if(row==4 && col==4)
		{
			gob[4][4].isred=0;
			gob[8][2].isred=1;
		}  
		else if(row==3 && col==8)
		{
			gob[3][8].isred=0;
			gob[6][7].isred=1;
		}  
		else if(row==4 && col==1)
		{
			gob[4][1].isred=0;
			gob[6][3].isred=1;
		}  
		else if(row==6 && col==0)
		{
			gob[6][0].isred=0;
			gob[1][5].isred=1;
		}  
		else if(row==5 && col==8)
		{
			gob[5][8].isred=0;
			gob[3][10].isred=1;
		}  
		else if(row==5 && col==4)
		{
			gob[5][4].isred=0;
			gob[3][6].isred=1;
		}  
		else if(row==0 && col==7)
		{
			gob[0][7].isred=0;
			gob[9][4].isred=1;
		}
		else if(row==2 && col==4)
		{
			gob[2][4].isred=0;
			gob[5][2].isred=1;
		}
		else if(row==0 && col==4)
		{
			gob[0][4].isred=1;
			f.repaint();
			winflag=0;
		}
	
	}
	//snake and stair for green gob
	public void ckeckGreen()
	{
		
		if(row==4 && col==11)
		{
			gob[4][11].isgreen=0;
			gob[11][6].isgreen=1;
		}
		else if(row==10 && col==7)
		{
			gob[10][7].isgreen=0;
			gob[9][8].isgreen=1;
		} 
		else if(row==10 && col==5)
		{
			gob[10][5].isgreen=0;
			gob[6][9].isgreen=1;
		} 
		else if(row==7 && col==5)
		{
			gob[7][5].isgreen=0;
			gob[9][7].isgreen=1;
		} 
		else if(row==8 && col==1)
		{
			gob[8][1].isgreen=0;
			gob[7][2].isgreen=1;
		}
		else if(row==4 && col==4)
		{
			gob[4][4].isgreen=0;
			gob[8][2].isgreen=1;
		}  
		else if(row==3 && col==8)
		{
			gob[3][8].isgreen=0;
			gob[6][7].isgreen=1;
		}  
		else if(row==4 && col==1)
		{
			gob[4][1].isgreen=0;
			gob[6][3].isgreen=1;
		}  
		else if(row==6 && col==0)
		{
			gob[6][0].isgreen=0;
			gob[1][5].isgreen=1;
		}  
		else if(row==5 && col==8)
		{
			gob[5][8].isgreen=0;
			gob[3][10].isgreen=1;
		}  
		else if(row==5 && col==4)
		{
			gob[5][4].isgreen=0;
			gob[3][6].isgreen=1;
		}  
		else if(row==0 && col==7)
		{
			gob[0][7].isgreen=0;
			gob[9][4].isgreen=1;
		} 
		else if(row==2 && col==4)
		{
			gob[2][4].isgreen=0;
			gob[5][2].isgreen=1;
		}
		else if(row==0 && col==4)
		{
			gob[0][4].isgreen=1;
			f.repaint();
			winflag=1;
		}
	}
	//this method is called when player1 first time click on dice 
	public void playermove_red()
	{
		
		if(initflag_red==0)
		{
			if(player1_value==1)
			{
				gob[11][4].isred=1;
				initflag_red=1;
				playno=1-playno;
				gob[10][1].isred=0;
			}
			
		}
		else if(initflag_red==1)
		{
			selectionflag_red=1;
			valid_red=0;
		}

	}
	//this method is called when player2 first time click on dice
	public void playermove_green()
	{
		
		if(initflag_green==0)
		{
			if(player2_value==1)
			{
				gob[11][4].isgreen=1;
				initflag_green=1;
				playno=1-playno;
				gob[11][1].isgreen=0;
			}
		}
		else if(initflag_green==1)
		{
			selectionflag_green=1;
			valid_green=0;
		}
	
	}
	//method for creating grid object
	void newGrid()
	{
		
		for(int i=0;i<12;i++)
			for(int j=0;j<12;j++)
				gob[i][j]=new AGrid();

	}
	//method for set grid serial
	void setSerial()
	{
		
		int ser=1;
		for(int i=4;i<=7;i++,ser++)
		{
			gob[11][i].serial=ser;
			gob[11][i].isgreen=0;
			gob[11][i].isred=0;
		}
		for(int i=8;i>=3;i--,ser++)
		{
			gob[10][i].serial=ser;
			gob[10][i].isgreen=0;
			gob[10][i].isred=0;
		}
		for(int i=3;i<=8;i++,ser++)
		{
			gob[9][i].serial=ser;
			gob[9][i].isgreen=0;
			gob[9][i].isred=0;
		}
		for(int i=10;i>=1;i--,ser++)
		{
			gob[8][i].serial=ser;
			gob[8][i].isgreen=0;
			gob[8][i].isred=0;
		}
		for(int i=0;i<=11;i++,ser++)
		{
			gob[7][i].serial=ser;
			gob[7][i].isgreen=0;
			gob[7][i].isred=0;
		}
		for(int i=11;i>=0;i--,ser++)
		{
			gob[6][i].serial=ser;
			gob[6][i].isgreen=0;
			gob[6][i].isred=0;
		}
		for(int i=0;i<=11;i++,ser++)
		{
			gob[5][i].serial=ser;
			gob[5][i].isgreen=0;
			gob[5][i].isred=0;
		}
		for(int i=11;i>=0;i--,ser++)
		{
			gob[4][i].serial=ser;
			gob[4][i].isgreen=0;
			gob[4][i].isred=0;
		}
		for(int i=1;i<=10;i++,ser++)
		{
			gob[3][i].serial=ser;
			gob[3][i].isgreen=0;
			gob[3][i].isred=0;
		}
		for(int i=8;i>=3;i--,ser++)
		{
			gob[2][i].serial=ser;
			gob[2][i].isgreen=0;
			gob[2][i].isred=0;
		}
		for(int i=3;i<=8;i++,ser++)
		{
			gob[1][i].serial=ser;
			gob[1][i].isgreen=0;
			gob[1][i].isred=0;
		}
		for(int i=7;i>=4;i--,ser++)
		{
			gob[0][i].serial=ser;
			gob[0][i].isgreen=0;
			gob[0][i].isred=0;
		}
		gob[0][3].isgreen=0;
		gob[0][3].isred=0;
		
		gob[11][1].isgreen=1;
		gob[10][1].isred=1;


	}
	//method for loading necessary variable to array 
    public void loadToarray()
    {
    	
	   	array[0]=dicevalue;
	   	array[1]=playno;
	   	array[2]=player1_value;
	   	array[3]=player2_value;
	   	array[4]=initflag_red;
	   	array[5]=initflag_green;
	   	array[6]=selectionflag_red;
	   	array[7]=selectionflag_green;
	   	array[8]=prerow;
	   	array[9]=precol;
	   	array[10]=valid_red;
	   	array[11]=valid_green;
	   	array[12]=noof1_red;
	   	array[13]=noof1_green;
	   	array[14]=row;
	   	array[15]=col;
	   	array[16]=winflag;
	   	array[17]=remain_red;
	   	array[18]=remain_green;
	   	int cou=19;
        for(int i=19;i<167;i++)
        	array[i]=0;
        
        for(int i=0;i<12;i++)
        {
	       	 for(int j=0;j<12;j++)
	       	 {
	       		 if(gob[i][j].isred==1 && gob[i][j].isgreen!=1)
	       			 array[cou]=1;
	       		 else if(gob[i][j].isred!=1 && gob[i][j].isgreen==1)
	       			 array[cou]=2;
	       		 else if(gob[i][j].isred==1 && gob[i][j].isgreen==1)
	       			 array[cou]=3;
	       		 else
	       			 array[cou]=0;
	       		 cou++;
	       	 }
        } 
	   	
    }
  //method for loading value from array to variable
    public void loadFromarray()
    {
    	
	   	dicevalue=array[0];
	   	playno=array[1];
	   	player1_value=array[2];
	   	player2_value=array[3];
	   	initflag_red=array[4];
	   	initflag_green=array[5];
	   	selectionflag_red=array[6];
	   	selectionflag_green=array[7];
	   	prerow=array[8];
	   	precol=array[9];
	   	valid_red=array[10];
	   	valid_green=array[11];
	   	noof1_red=array[12];
	   	noof1_green=array[13];
	   	row=array[14];
	   	col=array[15];
	   	winflag=array[16];
	   	remain_red=array[17];
	   	remain_green=array[18];
	   	
	   	int cou=19;
	   	
        for(int i=0;i<12;i++)
        {
	       	 for(int j=0;j<12;j++)
	       	 {
	       		 if(array[cou]==1)
	       		 {
	       			gob[i][j].isred=1;
	       			gob[i][j].isgreen=0;
	       		 }
	       		 else if(array[cou]==2)
	       		 {
	       			gob[i][j].isred=0;
	       			gob[i][j].isgreen=1;
	       		 }
	       		 else if(array[cou]==3)
	       		 {
	       			gob[i][j].isred=1;
	       			gob[i][j].isgreen=1;
	       		 }	    
	       		 else
	       		 {
		       		gob[i][j].isred=0;
		       		gob[i][j].isgreen=0;
		       	 }
	       		 cou++;
	       	 }
        } 
    	
    }
}
//grid class
class AGrid 
{
	 
		 int isred=0;
		 int isgreen=0;
		 int serial=-1;
	
}