package Game.Entities.Static;

import Main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class BaseCounter extends BaseStaticEntity {
    public Item item;
    public long now;
    public static long bonusStartTime;
    public static int DEFAULTCOUNTERWIDTH = 96;

    BaseCounter(BufferedImage sprite, int xPos, int yPos,int width,int height, Handler handler) {
        super(sprite, xPos, yPos,width, height, handler);
    }

    public boolean isInteractable(){
        return handler.getPlayer().xPos + width/2 >= xPos && handler.getPlayer().xPos + width/2 < xPos + width;
    }

    public void interact(){
        if (item != null) {
            handler.getPlayer().getBurger().addIngredient(item);
        }
       
    }
    public void tick(){
    	// timer for first part of new timer
    	if (TimerCounter.getrandomTime() > 1) {
    		TimerCounter.randomTime--;
    		System.out.println(TimerCounter.randomTime);
    	}
    	else if (TimerCounter.getrandomTime() == 1) {
    		TimerCounter.randomTime--;
    		TimerCounter.bonusTime = true;
    		bonusStartTime = System.nanoTime();
    	}
    	else {
    		TimerCounter.bonusTime = System.nanoTime()- bonusStartTime < TimeUnit.SECONDS.toNanos(2);
    	}
    	
    }

    public void render(Graphics g){
        g.drawImage(sprite,xPos,yPos,width,height,null);
        if(isInteractable() && item != null){
            g.drawImage(item.sprite,xPos + width/2 - 25,yPos -30,50,30,null);
        }
    }
    
    
}
