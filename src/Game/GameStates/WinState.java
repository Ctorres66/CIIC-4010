package Game.GameStates;

import Main.Handler;
import Resources.Images;
import Display.UI.ClickListlener;
import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Game.Entities.Dynamic.Player;

import java.awt.*;


public class WinState extends State {

    private int count = 0;
    private UIManager uiManager;

    public WinState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(56, 223, 128, 64, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
        }));

        uiManager.addObjects(new UIImageButton(56, 223+(64+16), 128, 64, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));

        uiManager.addObjects(new UIImageButton(56, (223+(64+16))+(64+16), 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
        uiManager.addObjects(new UIImageButton(handler.getWidth()/2+32, handler.getHeight()/2-32, 128, 64, Images.butstart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
            }
        }));




    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameState);
        }


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.Win,0,0,800,600,null);
        String left = String.valueOf(Player.haveLeft);
        String served = String.valueOf(Player.served);
        int fontsize = 30;
        g.setColor(Color.RED);
        g.setFont(new Font("Broadway", Font.PLAIN, fontsize));
        g.drawString("Clients that left: " + left, (handler.getWidth()/2) -100, handler.getHeight() -320);
        g.drawString("Clients served: " + served, (handler.getWidth()/2) -100, handler.getHeight() -280);
        uiManager.Render(g);

    }
}
