package Game.Entities.Dynamic;

import Game.Entities.Static.Burger;
import Game.Entities.Static.Item;
import Game.Entities.Static.Order;
import Game.GameStates.State;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

// added variable for a clients position in the line
public class Client extends BaseDynamicEntity {
	//added variables for the pos and neg effect of the inspector
    public int patience;
    public int OGpatience;
    Order order;
    public int haveLeft = 0;
    int linePosition = 1;
    public static boolean leaveCheck;
    public 
    int inspectorNegEffect = 0;
    int inspectorPosEffect = 0;
    // made the randomizer for the specific sprites of each client into its own variable
    private static int r;
    public boolean isLeaving = false;
    public Client(int xPos, int yPos, Handler handler) {
        super(Images.people[r], xPos, yPos,64,72, handler);
        r = new Random().nextInt(11);
        for (Client client: handler.getWorld().clients) {
        	if (this.inspectorNegEffect < client.inspectorNegEffect) {
        		this.inspectorNegEffect = client.inspectorNegEffect;
        	}
        	if (this.inspectorPosEffect < client.inspectorPosEffect) {
        		this.inspectorPosEffect = client.inspectorPosEffect;
        	}
        }
        patience = (int) ((new Random().nextInt(120*60)+60*60) * Math.pow(0.94, inspectorNegEffect) * Math.pow(1.10, inspectorPosEffect));
        OGpatience = patience;
        
        int numOfIngredients = new Random().nextInt(4)+1;
        order = new Order();
        order.food = new Burger(xPos +72,yPos,52,22);
        ((Burger) order.food).addIngredient(Item.botBread);
        ((Burger) order.food).addIngredient(Item.burger);
        order.value += 1.0;
        for(int i = 0;i<numOfIngredients;i++){
            int ingredients = new Random().nextInt(4)+1;
            order.value += 0.5;
            switch (ingredients){
                case 1:
                    ((Burger) order.food).addIngredient(Item.lettuce);

                    break;
                case 2:
                    ((Burger) order.food).addIngredient(Item.tomato);

                    break;

                case 3:
                    ((Burger) order.food).addIngredient(Item.cheese);

                    break;

            }

        }
        ((Burger) order.food).addIngredient(Item.topBread);
    }

    //Added method AntiVTick, which does a special version of tick only for only 1 of the list of possible sprites
    public void tick(){
  
    	if (r == 9) {
    		AntiVTick();
    	}
    	else if (r==10) {
    		InspectorTick();
    	}
    	else{
    		patience--;
        if(patience<=0){
            isLeaving=true;
       
        }
    	}
    	/*if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_T)){
        	haveLeft++;
        }*/
    	if(patience==0){
    		leaveCheck = true;
	        }
    	
    }
    
    public void InspectorTick() {
    	patience--;
        if(patience<=0){
            isLeaving=true;
            InspectorLoss();
        }
        if(patience==0){
        	leaveCheck = true;
	        }
        
    }
    
    public void InspectorLoss() {
    	Player.money = (float) (Player.money/2.0);
    	for(Client client: handler.getWorld().clients) {
    		client.inspectorNegEffect++;
    	}
    }
    public void InspectorWin() {
    	for(Client client: handler.getWorld().clients) {
    		client.patience = (int) (client.patience + (client.OGpatience * 0.12));
    		client.inspectorPosEffect++;
    	}
    }
    
    public void AntiVTick() {
    	  patience--;
	        if(patience<=0){
	            isLeaving=true;
	        }
	        if(patience==0){
	           haveLeft++;
	        }
	        for(int i = 0; i < 13; i++) {
	        	if(patience == OGpatience - (0.08 * i * OGpatience)) {
	        		 int r = new Random().nextInt(2) + 1;
	        		 if (r == 0) {
	        			for (Client client: handler.getWorld().clients) {
	        				if(client.linePosition == (this.linePosition +1)) {
	        					client.patience =  (client.patience - client.OGpatience/25);
	        				}
	        			}
	        		 }
	        		 else {
	        			 for (Client client: handler.getWorld().clients) {
		        				if(client.linePosition == (this.linePosition -1)) {
		        					client.patience =  (client.patience - client.OGpatience/25);
		        				}
		        			}
	        		 }
	        	}
	        }
	    }
    	
    public void render(Graphics g){

        if(!isLeaving){
            g.drawImage(Images.tint(sprite,1.0f,((float)patience/(float)OGpatience),((float)patience/(float)OGpatience)),xPos,yPos,width,height,null);

            ((Burger) order.food).render(g);
        }
    }

    public void move(){
        yPos+=102;
        ((Burger) order.food).y+=102;
        linePosition++;
        
    }
    
    //added 25% bonus patience reward for every correct order done
    public void perfectOrderReward(){
    	patience += OGpatience/4;
    	if (patience > OGpatience) {
    		patience = OGpatience;
    	}
    }
    
    public int getpatience() {
    	return patience;
    }
    public int getOGpatience() {
    	return OGpatience;
    }
}
