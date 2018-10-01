package Warcaby;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn extends Circle {


    private int player;

    public Pawn()
    {
        this.setCenterX(50);
        this.setCenterY(50);
        this.setRadius(0);
        this.player = player;
    }

    public void setPlayer(int player) {

        this.player = player;

        if(player == 0)
            this.setRadius(0);
        else if(player == 1)
        {
            this.setColor(Color.WHITE);
            this.setRadius(40);
        }
        else if(player == 2)
        {
            this.setColor(Color.DARKGREY);
            this.setRadius(40);
        }
    }

    public void setColor(Color color)
    {
        this.setFill(color);
    }
}
