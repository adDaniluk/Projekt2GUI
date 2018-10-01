package Warcaby;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class BoardSquare extends Region {
    public BoardSquare() {
        setPrefSize(100, 100);
    }

    public void whiteSquare() {
        setColor(Color.WHITE);
    }

    public void blackSquare() {
        setColor(Color.BLACK);
    }

    public void highlightSquare()
    {
        setColor(Color.RED);
    }

    public void setColor(Color color)
    {
        BackgroundFill bgFill = new BackgroundFill(color, new CornerRadii(15), new Insets(0));
        Background bg = new Background(bgFill);
        setBackground(bg);
    }

}
