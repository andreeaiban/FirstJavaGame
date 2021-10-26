import java.applet.*;
import java.awt.*;          
import java.awt.event.*;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.* ;


/*
Stanger Things/ Video Game/ 2018
author:Andreea Ibanescu

Stranger Things, based on a tv show and book; the characters you choose from are the ones who are trying to find their friend Wil.. he got lost in the forest. The forest has monsters from the labotory in the forest, which take away the health of the characters.
Goal of the game is to not die from the monsters and find Will.


*/

public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet=100;
  private int timesAvoid;
  private AudioClip gamemusic1;
  private  int characterT;
  private boolean isGameOver;
  private int gets;
 
  
  public Game()
  {
     ImageIcon start= new ImageIcon(Game.class.getResource("strangerthingshed.png"));
  
        String[] c={"Start", "Exit"};
           
        int choice1= JOptionPane.showOptionDialog(null,"","Choose a character",0,0, start,c,null);
     
         if(choice1==0)
         {
             
             String[] characters={"Mike","Dustin","Lucas","Eleven"};
              ImageIcon chara= new ImageIcon(Game.class.getResource("characters.png"));
             
             int choice= JOptionPane.showOptionDialog(null,"","Choose a character",0,0, chara,characters,null);
             if(choice==0)
                characterT+=0;
             else if(choice==1)
                 characterT+=1;
             else if(choice==2)
                 characterT+=2;
             else
                 characterT+=3;
             
             ImageIcon intro= new ImageIcon(Game.class.getResource("intro.png"));
             JOptionPane.showMessageDialog(null,"","", 0,intro);
          
            
         grid = new Grid(5, 10);
         userRow = 0;
         msElapsed = 0;
         timesGet = 0;
         timesAvoid = 0;
         updateTitle();
   
          grid.setBackground("background1.gif");
          gamemusic1=Applet.newAudioClip(this.getClass().getResource("gamemusic1.wav"));
         if(characterT==0)
         grid.setImage(new Location(userRow, 0),"user.gif");
          else if(characterT==1)
       grid.setImage(new Location(userRow, 0),"dustin.gif");
          else if(characterT==2)
           grid.setImage(new Location(userRow, 0),"lucas.gif");
       else
           grid.setImage(new Location(userRow, 0),"eleven.gif");
       
         gamemusic1.loop();
         
         }
        
  }
  
  public void play()
  {
    while (!isGameOver)
    {
      isGameOver();
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0)
      {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
        if(getScore()<0)
        gameOver();
        System.exit(1);
        
         
  }
  
  public void handleKeyPress()
  {
      
      Location my_location = new Location(userRow, 0);
      int key=grid.checkLastKeyPressed();
      
      if(key==38)
      {
          if(userRow>0)
          {
         userRow--;
         Location next_location = new Location(userRow, 0);
         handleCollision(next_location );
        
         grid.setImage(my_location, null);
          if(characterT==0)
         grid.setImage(next_location, "user.gif");
           else if(characterT==1)
          grid.setImage(next_location, "dustin.gif");
          else if(characterT==2)
            grid.setImage(next_location, "lucas.gif");
          else
           grid.setImage(next_location, "eleven.gif");
        
          }
      }
      else if(key==40 )
            
             if( userRow<grid.getNumRows()-1)
                 
      {
         
          userRow++;
          
          Location next_location = new Location(userRow, 0);
          handleCollision(next_location);
          grid.setImage(my_location, null);
           if(characterT==0)
         grid.setImage(next_location, "user.gif");
           else if(characterT==1)
          grid.setImage(next_location, "dustin.gif");
          else if(characterT==2)
            grid.setImage(next_location, "lucas.gif");
          else
           grid.setImage(next_location, "eleven.gif"); 
      }
  }
  
  public void populateRightEdge()
  {
    int rnd=(int)(Math.random()*3);
    int miniboss=(int)(Math.random()*7);
    int will=(int)(Math.random()*30);
    
    if(rnd==2 )
    {
    int r=grid.getNumRows();   
    int randomRow= (int)(Math.random()*r);
    int c=grid.getNumCols();
    int randomCol=(int)(Math.random()*c);
    Location loc_place = new Location(randomRow,randomCol);
    grid.setImage(loc_place, "get.gif");
    }
    if( (rnd==1 || rnd==3 )&& gets>10)
    {
    int r=grid.getNumRows();   
    int randomRow= (int)(Math.random()*r);
    int c=grid.getNumCols();
    int randomCol=(int)(Math.random()*c);
    Location loc_place = new Location(randomRow,randomCol); 
    grid.setImage(loc_place, "avoid.gif");
    }
    if((this.gets>30)&& (miniboss==5 || miniboss==7 || miniboss==4  ))
    {
    int r=grid.getNumRows();   
    int randomRow= (int)(Math.random()*r);
    int c=grid.getNumCols();
    int randomCol=(int)(Math.random()*c);
    Location loc_place = new Location(randomRow,randomCol); 
    grid.setImage(loc_place, "avoid2.gif");
    }
    if( will==6 && this.gets>40 )
    {
       int r=grid.getNumRows();   
    int randomRow= (int)(Math.random()*r);
    int c=grid.getNumCols();
    int randomCol=(int)(Math.random()*c);
    Location loc_place = new Location(randomRow,randomCol); 
    grid.setImage(loc_place, "get2.gif");
    }
  }
  public void scrollLeft()
  {
      Location loc_user = new Location(userRow, 0);
      for (int i = 0; i < grid.getNumRows(); i++) {
	for (int j = 0; j < grid.getNumCols(); j++) {
	
	Location current = new Location(i, j);
	if (!current.equals(loc_user))
        {		
        int temp = j - 1;
        Location loc_set = new Location(i, temp);			
           if (temp < 0) 
           {
		grid.setImage(current, null);
	   } 
           else if (!current.equals(new Location(userRow, 1)))
                {
	        	grid.setImage(loc_set, grid.getImage(current));
		}
                    	if (j == grid.getNumCols() - 1) {
			grid.setImage(current, null);
			}
			        }
				if (current.equals(new Location(userRow, 1))){
					handleCollision(current);
				}
			}
		}
  }
  
  public void handleCollision(Location loc)
  {
      AudioClip getSound, avoidSound;
      getSound=Applet.newAudioClip(this.getClass().getResource("0227.wav"));
      avoidSound=Applet.newAudioClip(this.getClass().getResource("0359.wav"));
      AudioClip getSound2, avoidSound2;
      getSound2=Applet.newAudioClip(this.getClass().getResource("0201.wav"));
      avoidSound2=Applet.newAudioClip(this.getClass().getResource("0008.wav"));
      
      
     
      
     
      // check if an object exists
      if (grid.getImage(loc) == null) 
      {
          grid.setImage(loc, null);
      }
      else 
      {
       if (grid.getImage(loc) == "get.gif") 
      {
         grid.setImage(loc, null);
	timesGet += 5;
        this.gets++;
       
        getSound.play();
      }
       else if(grid.getImage(loc)=="avoid2.gif")
       {
         timesAvoid += 20;
         grid.setImage(loc, null);
         avoidSound2.play();
       }
       else if (grid.getImage(loc) == "avoid.gif")
      {
	 timesAvoid += 5;
         grid.setImage(loc, null);
         avoidSound.play();
      }
       else if(grid.getImage(loc)=="get2.gif")
       {
           getSound2.play();
           System.out.println("CONGRAZ");
             ImageIcon intro= new ImageIcon(Game.class.getResource("end.png"));
             JOptionPane.showMessageDialog(null,"","", 0,intro);
           isGameOver=true;
           
       }
       
	grid.setImage(loc, null);
      }
      
  }
  
  public int getScore()
  {
      //I changed this to health of the character score=health
    return (this.timesGet-(timesAvoid*3));
  }
  
  public void updateTitle()
  {
      
    grid.setTitle("Health:  " + getScore());
  }
  
  public void isGameOver()
  {
   if (getScore()<0){
      System.out.println("GAME OVER");
    
       isGameOver=true;
       
   }
   else
       isGameOver=false;
  
  }
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  public void gameOver()
  {
      if(getScore()<0)
      {
        ImageIcon intro= new ImageIcon(Game.class.getResource("gameover.jpg"));
        JOptionPane.showMessageDialog(null,"","", 0,intro);
        System.exit(1);
      }      
  }
  
  public static void main(String[] args)
  {
    test();
   
  }
}