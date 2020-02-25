package Game.Entities.Static;

import Main.Handler;
import Resources.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Game.Entities.Dynamic.Client;

public class TimerCounter extends BaseCounter {
	public boolean timerOn = false;
	public static int randomTime;
	public static boolean bonusTime = false;

    public TimerCounter(int xPos, int yPos, Handler handler) {
        super(Images.newStuff[4], xPos, yPos,96,102,handler);
        	
    }
    @Override
    public void interact() {
    	if (timerOn == true) {
    		timerCheck();
    	}
    	else {
    		timerActivate();
    	}
    	
    }
    
    private void timerCheck() {
    	if (randomTime == 0 && bonusTime == true) {
    		if (bonusStartTime + 2000 > now) {
    		for (Client client: handler.getWorld().clients) {
    			client.patience = client.OGpatience;
    		}
    		}
    	}
    	timerOn = false;
    	bonusTime = false;
    	
    }
    
    private void timerActivate() {
    	timerOn = true;
    	randomTime = 1000 + (int) (Math.random() * 9000);
    	
    }
    
    public static int getrandomTime() {
		return randomTime;
    }
    
    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if (bonusTime== false) {
        g.drawImage(sprite,xPos,yPos,width,height,null);
        }
        else {
        	g.drawImage((Images.tint(sprite, 2, 1, 1)),xPos , yPos, width, height, null);
        }
        if(isInteractable()){
            g2.setColor(Color.RED);
            g.setFont(new Font("ComicSans", Font.ITALIC, 32));
            g2.drawString("Timer",xPos + width/2 - 42,yPos -30);
        }
        }
        

}
