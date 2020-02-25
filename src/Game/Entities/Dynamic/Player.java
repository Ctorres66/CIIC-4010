package Game.Entities.Dynamic;

import Game.Entities.Static.*;
import Game.GameStates.State;
import Game.GameStates.WinState;
import Game.GameStates.LoseState;
import Main.Handler;
import Resources.Animation;
import Resources.Images;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends BaseDynamicEntity {
	// altered the speed
    Item item;
    public static float money;
    int speed = 3;
    public static int haveLeft;
    public static int served;
    private Burger burger;
    private String direction = "right";
    private int interactionCounter = 0;
    private Animation playerAnim;
    int chosenOne = 5;
    int arrowY = 90;
    public Player(BufferedImage sprite, int xPos, int yPos, Handler handler) {
        super(sprite, xPos, yPos,82,112, handler);
        createBurger();
        playerAnim = new Animation(120,Images.chef);
        
    }

    public void createBurger(){
        burger = new Burger(handler.getWidth() - 110, 100, 100, 50);

    }

    public void tick(){
        playerAnim.tick();
        if(xPos + width >= handler.getWidth()){
            direction = "left";

        } else if(xPos <= 0){
            direction = "right";
        }
        if (direction.equals("right")){
            xPos+=speed;
        } else{
            xPos-=speed;
        }
        if (interactionCounter > 15 && handler.getKeyManager().attbut){
            interact();
            interactionCounter = 0;
        } else {
            interactionCounter++;
        }
        if(handler.getKeyManager().fattbut){
            for(BaseCounter counter: handler.getWorld().Counters){
                if (counter instanceof PlateCounter && counter.isInteractable()){
                    createBurger();
                }
            }
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){
            for(BaseCounter counter: handler.getWorld().Counters) {
                if (counter instanceof PlateCounter && counter.isInteractable()) {
                    ringCustomer();
                }
            }
        }
        // press shift -> lower speed with no chance of stopping or going in reverse (neg speed)
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)){
        	
        	if (speed >= 1) {
            speed = speed - 1;
            State.setState(handler.getGame().loseState);
        	}
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_G)){
        	haveLeft++;
        }
        ArrowChange();
        if (money >= 50.0) {
        	State.setState(handler.getGame().winState);
        }
        for (Client nigga : handler.getWorld().clients) {
        if(nigga.leaveCheck  == true) {
        	haveLeft++;
        	nigga.leaveCheck = false;
        	}
        }
        if (haveLeft == 10) {
        	State.setState(handler.getGame().loseState);
        }
    }

    //new method "ArrowChange" which to change the chosen client in accordance with witch number was pressed
    
    public void ArrowChange() {
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)){
        	chosenOne = 1;
        	arrowY = 90;
        }
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)){
    		chosenOne = 2;
    		arrowY = 90 + 102;
        }
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)){
    		chosenOne = 3;
    		arrowY = 90 + (102 * 2);
        }
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)){
    		chosenOne = 4;
    		arrowY = 90 + (102 * 3);
        }
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)){
    		chosenOne = 5;
    		arrowY = 90 + (102 * 4);
        }
    	
    }
    private void ringCustomer() {

    	//added 25% patience award and also the 15% bonus tip for getting 50% or more patience when handing out order
    	//added if statement so that it only checks the client chosen by the chef
        for(Client client: handler.getWorld().clients){
        	if(client.linePosition == chosenOne) {
            boolean matched = ((Burger)client.order.food).equals(handler.getCurrentBurger());
            if(matched){
            	if (client.patience >= (client.OGpatience/2)) {
            		money += client.order.value * 0.15;
            	}
            	if (StoveCounter.burnPercentage >= 48.0 && StoveCounter.burnPercentage <= 53.0) {
            		money += client.order.value * 0.12;
            	}
                money+=client.order.value;
                handler.getWorld().clients.remove(client);
                handler.getPlayer().createBurger();
                System.out.println("Total money earned is: " + String.valueOf(money));
                client.perfectOrderReward();
                served++;
                return;
            }
            }

        }
    }

    public void render(Graphics g) {
    	g.drawImage(Images.arrow,handler.getWidth()/4-52,arrowY ,62,62,null);
        if(direction=="right") {
            g.drawImage(playerAnim.getCurrentFrame(), xPos, yPos, width, height, null);
        }else{
            g.drawImage(playerAnim.getCurrentFrame(), xPos+width, yPos, -width, height, null);

        }
        g.setColor(Color.green);
        burger.render(g);
        g.setColor(Color.green);
        g.fillRect(handler.getWidth()/2 -210, 3, 320, 32);;
        g.setColor(Color.yellow);
        g.setFont(new Font("ComicSans", Font.BOLD, 32));
        g.drawString("Money Earned: " + money, handler.getWidth()/2 -200, 30);
    }

    public void interact(){
        for(BaseCounter counter: handler.getWorld().Counters){
            if (counter.isInteractable()){
                counter.interact();
            }
        }
    }
    public Burger getBurger(){
        return this.burger;
    }
}
