package Warcaby;

import javafx.event.EventHandler;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Board extends GridPane {

    private BoardModel model;
    Shadow ShadowEfect = new Shadow(100, Color.CORNFLOWERBLUE);
    private StackBoard stackBoardTable[][];
    public boolean[][] validMovesTable = new boolean[8][8];

    public Board(BoardModel model) {
        this.model = model;
        stackBoardTable = new StackBoard[8][8];
        setupBoard();
    }

    public void setupBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int x = j;
                int y = i;

                BoardSquare boardSquare = new BoardSquare();
                Pawn pawn = new Pawn();
                StackBoard stackBoard = new StackBoard(boardSquare, pawn);

                if ((x + y) % 2 == 0)
                    boardSquare.whiteSquare();
                else{
                    stackBoard.setSquare(boardSquare);
                    boardSquare.blackSquare();

                    if(model.getPlayerCell(x, y) == 1) {
                        stackBoard.setPawn(pawn);
                        pawn.setPlayer(1);
                    }else if(model.getPlayerCell(x, y) == 2){
                        stackBoard.setPawn(pawn);
                        pawn.setPlayer(2);
                    }else{
                        stackBoard.setPawn(pawn);
                        pawn.setPlayer(0);
                    }

                stackBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event){
                        if (model.isPawnSelectable(x, y))
                            model.setToHiglightSquare(x, y, validMovesTable);

                        if (model.isPawnSelected() && event.getClickCount() % 2 == 0) {
                            model.unselectPawn();
                            }

                        if (model.isPawnMoveable(x, y) && validMovesTable[x][y]) {
                            model.setPawnToMove(x, y, validMovesTable);
                            if(model.getGameMode() == 1)
                                model.aiPossiblePawnToMove();
                            model.timerToMove();
                            }

                        if (model.isPawnOnAttackMode(x, y) && validMovesTable[x][y]) {
                            model.setPawnToAttack(x, y, validMovesTable);
                            if(model.getGameMode() == 1)
                                model.aiPossiblePawnToMove();
                            model.timerToMove();
                            }
                        update();
                    }
                });

                stackBoard.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        boardSquare.setEffect(ShadowEfect);
                    }
                });

                stackBoard.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        boardSquare.setEffect(null);
                    }
                });
            }
            stackBoardTable[x][y] = stackBoard;
            this.add(stackBoardTable[x][y], i, j);
            }
        }
    }

    public void update() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int x = j;
                int y = i;

                int hlX = model.getHLSquareX();
                int hlY = model.getHLSquareY();

                if (model.isPawnSelected())
                    stackBoardTable[hlX][hlY].getSquare().highlightSquare();

                if ((x + y) % 2 != 0) {
                    stackBoardTable[x][y].getSquare().blackSquare();
                    if (model.getPlayerCell(x, y) == 1) {
                        stackBoardTable[x][y].getPawn().setPlayer(1);
                    } else if (model.getPlayerCell(x, y) == 2) {
                        stackBoardTable[x][y].getPawn().setPlayer(2);
                    } else
                        stackBoardTable[x][y].getPawn().setPlayer(0);
                }
            }
        }
    }
}