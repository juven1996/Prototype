package projectprototype;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Circle {
    
    /*Center point of the circle*/
    protected Point origin;
    
    /*Timer to determine when circle should disappear*/
    protected Timer timer;
    
    /*Player reference to access shape list*/
    protected Player player;
    
    /*Time the circle should stay on screen. Default is 3000 (3 seconds)*/
    protected final int CIRCLETIME = 3000;
    
    /*Circle constructor.
      @param x: x center point of circle.
      @param y: y center point of circle.
      @param size: size of the circle. Default is 30.
      @param color: color the the circle.
      @param panel: reference to GamePanel.
    */
    public Circle(int x, int y, int size, GamePanel panel, Player player) {
        this.origin = new Point(x,y);
        this.player = player;
        timer = new Timer(CIRCLETIME, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                player.objects.remove(Circle.this);
                panel.clear();
                player.hp--;
                timer.stop();
            }    
        });
        timer.start();
    }

    /*Unused.*/
    public Circle(Point origin){
        this.origin = origin;
    }
    
    /*Detects whether the user clicked inside a circle or not.
      @param x: x point user clicked on.
      @param y: y point user clicked on.
    */
    public boolean contains(int x, int y) {
        int distance = (x - origin.x) * (x - origin.x) + (y - origin.y) * (y - origin.y);
            if(distance < (player.size*player.size)/4) {
                timer.stop();
                return true;
            }
        return false;
    }
}
