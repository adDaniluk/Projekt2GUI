package Warcaby;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Optional;
// profiler
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BoardModel model = new BoardModel();
        BorderPane rootBoard = addBorderPane();

        TextField textFieldPlayer = new TextField();
        textFieldPlayer.setEditable(false);
        textFieldPlayer.setMaxWidth(250);

        TextField textFieldTimer = new TextField();
        textFieldTimer.setEditable(false);
        textFieldTimer.setMaxWidth(250);

        Button startButton = new Button("Nowa gra");
        Button quitButton = new Button("Wyjście");
        Button saveButton = new Button("Zapis gry");
        Button loadButton = new Button("Wczytanie gry");
        Button musicButton = new Button("Music");
        Button checkEndGameButton = new Button("Sprawdzenie gry");

        startButton.setOnAction( e -> {
            model.setNewGame();
            Board boardGame = new Board(model);
            boardGame.setAlignment(Pos.CENTER);
            rootBoard.setCenter(boardGame);
            textFieldPlayer.textProperty().bind(model.descriptionProperty());
            textFieldTimer.textProperty().bind(model.descriptionTimeProperty());
            //boardGame.setScaleX(0.5);
            //boardGame.setScaleY(0.5);

            TextInputDialog dialog = new TextInputDialog("2");
            dialog.setContentText("Wybór trybu gry");
            dialog.setHeaderText("Wprowadź 1 dla gry z komputerem");
            Optional<String> result = dialog.showAndWait();

            try {
                if(result.isPresent())
                    model.setGameMode(Integer.parseInt(result.get()));
                }
            catch(NumberFormatException el){}

            textFieldPlayer.textProperty().addListener((observable -> {
                boardGame.update();
            }));
        });

        quitButton.setOnAction(e -> {
            primaryStage.close();
        });

        musicButton.setOnAction(e -> {
            backgroundMusicTask();
            musicButton.setDisable(true);

            if(backgroundMusicTask().isRunning())
                musicButton.setDisable(false);
        });

        saveButton.setOnAction( e -> {
            try{
                TextInputDialog dialog = new TextInputDialog("zapis gry");
                dialog.setHeaderText("Zapisz grę");
                dialog.setContentText("Podaj nazwę pliku");
                Optional<String> result = dialog.showAndWait();

                if(result.isPresent()){
                    PrintWriter out = new PrintWriter(result.get()+".txt");
                    out.println(model.saveModel());
                    out.close();
                }
            }
            catch(NullPointerException el){}
            catch(FileNotFoundException e1){}
        });

        loadButton.setOnAction( e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\Users\\Adam\\IdeaProjects\\Projekt2GUI"));
            fileChooser.setTitle("Otwórz zapis gry");
            fileChooser.getInitialDirectory().toPath();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt","*.txt"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            String content = null;

            try {
                if (selectedFile != null) {
                    content = new String(Files.readAllBytes(selectedFile.toPath()));
                    model.loadModel(content);
                    Board boardGame = new Board(model);
                    boardGame.setAlignment(Pos.CENTER);
                    rootBoard.setCenter(boardGame);
                    textFieldPlayer.textProperty().bind(model.descriptionProperty());
                    }
                }
            catch (IOException e1) {}
        });

        checkEndGameButton.setOnAction( e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if(model.checkEndGame()) {
                alert.setTitle("Gratulacje!");
                alert.setContentText(model.getScore() + "");
                alert.showAndWait();
                }
            else{
                alert.setTitle("Gramy dalej");
                alert.setContentText("Nadal są możliwe ruchy.");
                alert.showAndWait();
                }
        });

        VBox vBoxLeft = addVBox();
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxLeft.getChildren().addAll(startButton,quitButton,musicButton);

        VBox vBoxRight = addVBox();
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxRight.getChildren().addAll(saveButton,loadButton, checkEndGameButton);

        HBox hBoxBottom = new HBox();
        hBoxBottom.setPadding(new Insets(5));
        hBoxBottom.setAlignment(Pos.CENTER);
        hBoxBottom.getChildren().addAll(textFieldPlayer, textFieldTimer);

        rootBoard.setLeft(vBoxLeft);
        rootBoard.setRight(vBoxRight);
        rootBoard.setBottom(hBoxBottom);
        Scene scene = new Scene(rootBoard, 1200, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public BorderPane addBorderPane()
    {
        BorderPane borderPane = new BorderPane();

        Label nameGame = new Label("WARCABY");
        nameGame.setAlignment(Pos.TOP_CENTER);
        nameGame.setFont(Font.font("Arial", FontWeight.BOLD, 40));

        borderPane.setPadding(new Insets(20, 10, 50, 10));
        borderPane.setTop(nameGame);
        borderPane.setAlignment(nameGame, Pos.TOP_CENTER);
        return borderPane;
    }

    public VBox addVBox()
    {
       VBox vBox = new VBox();
       vBox.setPadding(new Insets(200,0,200,0));
       vBox.setSpacing(50);

       return  vBox;
    }

    public Task backgroundMusicTask()
    {
        final Task taskMusic = new Task() {

            @Override
            protected Object call() throws Exception {
                int s = 1;
                AudioClip audio = new AudioClip(getClass().getResource("PowerRangersThemeInstrumental.wav").toExternalForm());
                audio.setVolume(0.5f);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(taskMusic);
        thread.start();

        return taskMusic;
    }
}