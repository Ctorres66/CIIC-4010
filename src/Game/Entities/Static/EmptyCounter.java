package Game.Entities.Static;

import Main.Handler;
import Resources.Images;

import java.awt.*;

public class EmptyCounter extends BaseCounter {
    public EmptyCounter(int xPos, int yPos, Handler handler) {
        super(Images.kitchenCounter[2], xPos, yPos,96,102,handler);
        	item = Item.onion;
    }
}



