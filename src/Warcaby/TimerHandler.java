package Warcaby;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class TimerHandler implements EventHandler {

    private int seconds = 0;
    private Label label = new Label(seconds+"s");

    public TimerHandler(Label label)
    {
        this.label = label;
    }

    @Override
    public void handle(Event event) {
        seconds++;
        label.setText(seconds+"s");
        System.out.println(seconds);
    }

    public void zeroSeconds()
    {
        seconds = 0;
    }

}
