package Warcaby;

import javafx.scene.layout.StackPane;

public class StackBoard extends StackPane {

    private Pawn pawn;
    private BoardSquare square;

    public StackBoard(BoardSquare square, Pawn pawn) {
        getChildren().addAll(square,pawn);
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public BoardSquare getSquare() {
        return square;
    }

    public void setSquare(BoardSquare square) {
        this.square = square;
    }
}
