package Warcaby;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Collections;

public class BoardModel {

    private int[][] tableModel = new int[8][8];
    private int player;
    private int movesMade = 0;
    private AiArraylist aiArraylist;
    private int gameMode =2;

    private int highlightedSquareX = 0;
    private int highlightedSquareY = 0;

    private boolean pawnSelected = false;


    public void selectPawn() {
        pawnSelected = true;
    }

    public void unselectPawn() {
        pawnSelected = false;
    }

    public boolean isPawnSelected() {
        return pawnSelected;
    }

    public void setHLSquare(int x, int y) {
        highlightedSquareX = x;
        highlightedSquareY = y;
    }

    public int getHLSquareX() {
        return highlightedSquareX;
    }

    public int getHLSquareY() {
        return highlightedSquareY;
    }

    public int getHLPlayer() {
        player = tableModel[getHLSquareX()][getHLSquareY()];
        return player;
    }

    public int getPlayerCell(int x, int y) {
        player = tableModel[x][y];
        return player;
    }

    public void setPlayerCell(int x, int y, int player) {
        tableModel[x][y] = player;
    }

    public int[][] setNewGame() {

        movesMade = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) {
                    if (i < 3)
                        setPlayerCell(i, j, 2);
                    else if (i > 4)
                        setPlayerCell(i, j, 1);
                    else
                        setPlayerCell(i, j, 0);
                } else
                    setPlayerCell(i, j, 3);
            }
        }
        return tableModel;
    }

    public void getPawnCheckMoveTable(int originX, int originY, boolean validMovesTable[][]) {

        if (getPlayerCell(originX, originY) == 1) {

            try {
                if (getPlayerCell(originX - 1, originY + 1) == 0)
                    validMovesTable[originX - 1][originY + 1] = true;
                else
                    validMovesTable[originX - 1][originY + 1] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}

            try {
                if (getPlayerCell(originX - 1, originY - 1) == 0)
                    validMovesTable[originX - 1][originY - 1] = true;
                else
                    validMovesTable[originX - 1][originY - 1] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}

        }else if (getPlayerCell(originX, originY) == 2) {

            try {
                if (getPlayerCell(originX + 1, originY + 1) == 0)
                    validMovesTable[originX + 1][originY + 1] = true;
                else
                    validMovesTable[originX + 1][originY + 1] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}

            try {
                if (getPlayerCell(originX + 1, originY - 1) == 0)
                    validMovesTable[originX + 1][originY - 1] = true;
                else
                    validMovesTable[originX + 1][originY - 1] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    public void getPawnAttackTableCheck(int originX, int originY, boolean validMovesTable[][]) {


        if (getPlayerCell(originX, originY) == 1) {

            try {
                if (getPlayerCell(originX - 2, originY + 2) == 0 && getPlayerCell(originX - 1, originY + 1) == 2)
                    validMovesTable[originX - 2][originY + 2] = true;
                else
                    validMovesTable[originX - 2][originY + 2] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}

            try {
                if (getPlayerCell(originX - 2, originY - 2) == 0 && getPlayerCell(originX - 1, originY - 1) == 2)
                    validMovesTable[originX - 2][originY - 2] = true;
                else
                    validMovesTable[originX - 2][originY - 2] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}

        }else if (getPlayerCell(originX, originY) == 2) {

            try {
                if (getPlayerCell(originX + 2, originY - 2) == 0 && getPlayerCell(originX + 1, originY - 1) == 1)
                    validMovesTable[originX + 2][originY - 2] = true;
                else
                    validMovesTable[originX + 2][originY - 2] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}

            try {
                if (getPlayerCell(originX + 2, originY + 2) == 0 && getPlayerCell(originX + 1, originY + 1) == 1)
                    validMovesTable[originX + 2][originY + 2] = true;
                else
                    validMovesTable[originX + 2][originY + 2] = false;
                }
            catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    public void clearValidMovesTable(boolean table[][]) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                table[i][j] = false;
            }
        }
    }

    public boolean getMoveRange(int x, int y) {
        if (Math.abs(getHLSquareX() - x) == 1 && Math.abs(getHLSquareY() - y) == 1)
            return true;
        else
            return false;
    }

    public boolean isPawnMoveable(int x, int y) {
        return isPawnSelected() && getPlayerCell(x, y) == 0 && getMoveRange(x, y) ? true : false;
    }

    public boolean isPawnSelectable(int x, int y) {
        if (getMovesMade() % 2 == 0)
            return (getPlayerCell(x, y) == 1) && isPawnSelected() == false ? true : false;
        else
            return (getPlayerCell(x, y) == 2) && isPawnSelected() == false ? true : false;
    }

    public boolean isPawnOnAttackMode(int x, int y) {
        return isPawnSelected() && getPlayerCell(x, y) == 0 && getAttackRange(x, y) ? true : false;
    }

    public boolean getAttackRange(int x, int y) {
        if (Math.abs(getHLSquareX() - x) == 2 && Math.abs(getHLSquareY() - y) == 2)
            return true;
        else
            return false;
    }

    public void destroyPawn(int x, int y) {
        if (getHLPlayer() == 1) {
            if (y > highlightedSquareY)
                setPlayerCell(x + 1, y - 1, 0);
            else
                setPlayerCell(x + 1, y + 1, 0);
        } else if (getHLPlayer() == 2) {
            if (y > highlightedSquareY)
                setPlayerCell(x - 1, y - 1, 0);
            else
                setPlayerCell(x - 1, y + 1, 0);
        }
    }

    public void addMovesMade() {
        movesMade++;
        updateDescription();
    }

    public void setMovesMade(int mm) {
        movesMade = mm;
        updateDescription();
    }

    public int getMovesMade() {
        return movesMade;
    }


    private SimpleStringProperty description = new SimpleStringProperty();

    SimpleStringProperty descriptionProperty() {
        return description;
    }

    private void updateDescription() {
        if (getMovesMade() % 2 == 0)
            description.set("Ruch gracza Białego!");
        else
            description.set("Ruch gracza Czarnego!");
    }

    public void setToHiglightSquare(int x, int y, boolean validMovesTable[][]) {
        selectPawn();
        setHLSquare(x, y);
        getPawnCheckMoveTable(x, y, validMovesTable);
        getPawnAttackTableCheck(x, y, validMovesTable);
    }

    public void setPawnToMove(int x, int y, boolean validMovesTable[][]) {
        setPlayerCell(x, y, getHLPlayer());
        setPlayerCell(getHLSquareX(), getHLSquareY(), 0);
        addMovesMade();
        unselectPawn();
        clearValidMovesTable(validMovesTable);
    }

    public void setPawnToAttack(int x, int y, boolean validMovesTable[][]) {
        setPlayerCell(x, y, getHLPlayer());
        destroyPawn(x, y);
        setPlayerCell(getHLSquareX(), getHLSquareY(), 0);
        clearValidMovesTable(validMovesTable);
        getPawnAttackTableCheck(x, y, validMovesTable);
        boolean check = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int row = j;
                int column = i;

                if (validMovesTable[row][column] == true) {
                    check = true;
                    clearValidMovesTable(validMovesTable);
                    setToHiglightSquare(x, y, validMovesTable);
                }
            }
        }
        if (check == false) {
            addMovesMade();
            unselectPawn();
        }
    }


    public StringBuilder saveModel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                sb.append(tableModel[i][j]);
            }
        }

        if (getMovesMade() % 2 == 0)
            sb.append(0);
        else
            sb.append(1);

        if(getGameMode() == 1)
            sb.append(1);
        else
            sb.append(2);

        return sb;
    }

    public void loadModel(String s) {
        int tab[] = new int[64];
        for (int i = 0; i < 64; i++) {
            int a = Character.getNumericValue(s.charAt(i));
            tab[i] = (int) a;
        }

        int mm = Character.getNumericValue(s.charAt(64));
        setMovesMade(mm);

        int gm = Character.getNumericValue(s.charAt(65));
        setGameMode(gm);

        int index = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                tableModel[i][j] = tab[index];
                index++;
            }
        }
    }

    public boolean checkEndGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int y = i;
                int x = j;

                if ((x + y) % 2 != 0 && getPlayerCell(x, y) != 0 && isPawnSelectable(x, y)) {
                    boolean checkTableMove[][] = new boolean[8][8];
                    boolean checkTableAttack[][] = new boolean[8][8];

                    getPawnCheckMoveTable(x, y, checkTableMove);
                    for (int im = 0; im < 8; im++) {
                        for (int jm = 0; jm < 8; jm++) {
                            if ((im + jm) % 2 != 0 && checkTableMove[im][jm])
                                return false;
                        }
                    }

                    getPawnAttackTableCheck(x, y, checkTableAttack);
                    for (int ia = 0; ia < 8; ia++) {
                        for (int ja = 0; ja < 8; ja++) {
                            if ((ia + ja) % 2 != 0 && checkTableAttack[ia][ja])
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public String getScore() {
        int white = 0;
        int black = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getPlayerCell(j, i) == 1)
                    white++;
                else if (getPlayerCell(j, i) == 2)
                    black++;
            }
        }

        if (white < black) {
            return "Koniec gry, wygrywa czarny gracz! \n Ilość zbitych czarnych pionków: " + (12 - black) + "\n Ilość zbitych białych pionków: " + (12 - white);
        } else if (black < white) {
            return "Koniec gry, wygrywa biały gracz! \n Ilość zbitych czarnych pionków: " + (12 - black) + "\n Ilość zbitych białych pionków: " + (12 - white);
        } else if (black == white)
            return "Koniec gry, remis! \n Ilość zbitych czarnych pionków: " + (12 - black) + "\n Ilość zbitych białych pionków: " + (12 - white);
        return white + " " + black;
    }

    public void aiMove() {
        // tablica pionkow z mozliwymi ruchami/atakami czarnego - arraylist / losowanie / pobranie pierwszego elementu arraylisty
        // losowanie z puli pionkow
        // tablica sprawdzajaca mozliwosc ruchu/ataku
        // losowanie z puli mozliwych ruchow
        // ruch / zmiana gracza
    }

    public void aiPossiblePawnToMove() {

        AiArraylist arrayMoveList = new AiArraylist();
        AiArraylist arraySquareMoveList = new AiArraylist();
        AiXYField possiblePawnsMove;
        AiXYField possibleSquareMove;
        int counterCheckPawn = 0;
        int counterCheckSquare = 0;

        int aiPawnX;
        int aiPawnY;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                possiblePawnsMove = new AiXYField();
                int y = i;
                int x = j;

                if ((x + y) % 2 != 0 && getPlayerCell(x, y) == 2 && isPawnSelectable(x, y)) {
                    boolean checkTableM[][] = new boolean[8][8];
                    boolean checkTableA[][] = new boolean[8][8];

                    getPawnCheckMoveTable(x, y, checkTableM);
                    for (int im = 0; im < 8; im++) {
                        for (int jm = 0; jm < 8; jm++) {
                            if ((im + jm) % 2 != 0 && checkTableM[im][jm]) {
                                possiblePawnsMove.setX(x);
                                possiblePawnsMove.setY(y);
                                arrayMoveList.add(counterCheckPawn, possiblePawnsMove);
                                counterCheckPawn++;
                            }
                        }
                    }

                    getPawnAttackTableCheck(x, y, checkTableA);
                    for (int ia = 0; ia < 8; ia++) {
                        for (int ja = 0; ja < 8; ja++) {
                            if ((ia + ja) % 2 != 0 && checkTableA[ia][ja]) {
                                possiblePawnsMove.setX(x);
                                possiblePawnsMove.setY(y);
                                arrayMoveList.add(counterCheckPawn, possiblePawnsMove);
                                counterCheckPawn++;
                            }
                        }
                    }
                }
            }
        }

        Collections.shuffle(arrayMoveList);
        if (arrayMoveList.size() != 0) {
            possiblePawnsMove = (AiXYField) arrayMoveList.get(0);

            aiPawnX = possiblePawnsMove.getX();
            aiPawnY = possiblePawnsMove.getY();
            setHLSquare(aiPawnX, aiPawnY);
            selectPawn();

            boolean checkTableMA[][] = new boolean[8][8];
            int aiPawnMoveX;
            int aiPawnMoveY;
            possibleSquareMove = new AiXYField();

            getPawnAttackTableCheck(aiPawnX, aiPawnY, checkTableMA);
            getPawnCheckMoveTable(aiPawnX, aiPawnY, checkTableMA);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (checkTableMA[i][j]) {
                        possibleSquareMove.setY(j);
                        possibleSquareMove.setX(i);
                        arraySquareMoveList.add(counterCheckSquare, possibleSquareMove);
                        counterCheckSquare++;
                    }
                }
            }

            Collections.shuffle(arraySquareMoveList);
            AiXYField aiSquareMove = new AiXYField();
            aiSquareMove = (AiXYField) arraySquareMoveList.get(0);
            aiPawnMoveX = aiSquareMove.getX();
            aiPawnMoveY = aiSquareMove.getY();

            if (isPawnMoveable(aiPawnMoveX, aiPawnMoveY)) {
                setPlayerCell(aiPawnMoveX, aiPawnMoveY, getHLPlayer());
                setPlayerCell(aiPawnX, aiPawnY, 0);
                addMovesMade();
                unselectPawn();
            }else if (isPawnOnAttackMode(aiPawnMoveX, aiPawnMoveY)) {
                setPlayerCell(aiPawnMoveX, aiPawnMoveY, getHLPlayer());
                destroyPawn(aiPawnMoveX, aiPawnMoveY);
                setPlayerCell(aiPawnX, aiPawnY, 0);
                addMovesMade();
                unselectPawn();
            }
        }
    }

    Label label = new Label();

    TimerHandler timerHandler = new TimerHandler(label);
    KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), timerHandler);
    Timeline timer = new Timeline(keyFrame);

    private SimpleStringProperty timeLabelStringProperty = new SimpleStringProperty();

    SimpleStringProperty descriptionTimeProperty() {
        return timeLabelStringProperty;
    }

    public void updateDescriptionTime() {
        timeLabelStringProperty.bind(label.textProperty());
    }

    public void timerToMove() {
        label.setText("Pierwszy ruch");
        timer.setCycleCount(10);

        timer.setOnFinished(e -> {
            label.setText("Strata ruchu");
            addMovesMade();
            unselectPawn();
            if(getGameMode() == 1)
                aiPossiblePawnToMove();
            updateDescriptionTime();
            timer.play();
            timerHandler.zeroSeconds();
        });

        timer.stop();
        timerHandler.zeroSeconds();
        timer.play();
        updateDescriptionTime();
    }

    public void setGameMode(int x) {
        if(x == 1)
            gameMode=x;
    }

    public int getGameMode() {
        return gameMode;
    }

}