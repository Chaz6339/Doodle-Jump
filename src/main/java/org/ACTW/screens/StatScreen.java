package org.ACTW.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.ACTW.GameData;
import org.ACTW.Global;
import org.ACTW.SceneManager;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Class for StatScreen scene builder. Builds and manages the stat screen scene featuring a score and menu/play buttons.
 *
 * @author William
 * @author Alexa
 * @author Charlie
 */
public class StatScreen {
    // private class variables
    private final SceneManager sceneManager;  // this is needed to switch scenes
    private final Pane stackPane;

    // private final class variables
    private final String TITLE_GRAPHIC = "title.png";
    private final String RESTART_BTN_PATH = "play.png";
    private final String MENU_BTN_PATH = "menu.png";

    private GameScreen gameScreen;

    /**
     * Constructor for the StatScreen class.
     *
     * @author William
     * @author Alexa
     * @author Charlie
     */
    public StatScreen(SceneManager sceneManager) {
        if (sceneManager == null) {
            throw new NullPointerException("sceneManager is null");
        }

        // initialize stack pane
        this.stackPane = new Pane();

        // assign scene manager and game scene label
        this.sceneManager = sceneManager;

        //Load and set background image to fill screen
        Image backgroundImage = new Image("newbackground.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitHeight(Global.SCREEN_HEIGHT);
        backgroundView.setFitWidth(Global.SCREEN_WIDTH);
        stackPane.getChildren().addFirst(backgroundView);

        //Load and set Doodle Jump text graphic
        Image titleImage = new Image(TITLE_GRAPHIC);
        ImageView titleView = new ImageView(titleImage);
        titleView.setPreserveRatio(true);
        titleView.setFitWidth(400);
        titleView.setLayoutX(10);
        titleView.setLayoutY(50);
        stackPane.getChildren().add(titleView);
    }

    /**
     * Builds and returns the stat screen's scene.
     *
     * @return the start screen's scene
     * @author William
     * @author Alexa
     * @author Charlie
     */
    public Scene getScene() {
        // make the scene itself
        Scene scene = new Scene(stackPane, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

        // build and start the window
        addRestartButton();
        addMenuButton();
        addFinalScore();

        //Display the score
        //displayScore(GameData.currentScore);

        // return the scene
        return scene;
    }

    /**
     * Adds the restart button to the passed stage variable. Adds an image and then an overlapped button.
     *
     * @author William
     * @author Alexa
     * @author Charlie
     */
    private void addRestartButton() {
        // Get and style restart button image
        ImageView imageView = new ImageView(RESTART_BTN_PATH);
        imageView.setFitWidth(120);
        imageView.setPreserveRatio(true); // keep the aspect ratio

        // Create button object
        Button startButton = new Button();
        startButton.setGraphic(imageView);

        // Set styling
        startButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        // Center on screen
        startButton.setLayoutX((stackPane.getWidth() - imageView.getFitWidth()) / 2);
        startButton.setLayoutY((stackPane.getHeight() - imageView.getFitHeight()) / 2);

        // On click, the button activates the game scene
        startButton.setOnAction(e -> {
                    this.gameScreen = getGameScreen();
                    this.sceneManager.removeScene(Global.GAME_SCREEN_LABEL);
                    this.sceneManager.addScene(Global.GAME_SCREEN_LABEL, gameScreen.getScene());
                    this.sceneManager.activateScene(Global.GAME_SCREEN_LABEL);
                }
        );

        stackPane.getChildren().add(startButton);
    }

    /**
     * Adds the score from the previous game to the stat screen
     *
     * @author William
     * @author Alexa
     * @author Charlie
     */
    public void addFinalScore() {
        // Get score from game screen and style
        int finalScoreNumber = GameData.getCurrentScore();
        System.out.println("Final score is " + finalScoreNumber);
        Text finalScore = new Text("Score: " + finalScoreNumber);
        finalScore.setFont(new Font("Comic Sans MS", 30));
        finalScore.setFill(Color.MAROON);
        finalScore.setLayoutX(Global.SCREEN_WIDTH - finalScore.getBoundsInLocal().getWidth() / .65);
        finalScore.setLayoutY(Global.SCREEN_HEIGHT / 2.3);
        stackPane.getChildren().add(finalScore);
    }

    /**
     * Adds the menu button to the passed stage variable. Adds an image and then an overlapped button.
     *
     * @author William
     * @author Alexa
     * @author Charlie
     */
    private void addMenuButton() {
        // Get and style image
        ImageView imageView = new ImageView(MENU_BTN_PATH);
        imageView.setFitWidth(120);
        imageView.setPreserveRatio(true); // keep the aspect ratio

        // Create button object
        Button menuButton = new Button();
        menuButton.setGraphic(imageView);

        // Set styling
        menuButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        // Center on screen
        menuButton.setLayoutX((stackPane.getWidth() - imageView.getFitWidth()) / 2);
        menuButton.setLayoutY((stackPane.getHeight() - imageView.getFitHeight()) * (2.0 / 3.0));

        // On click, the button activates the game scene
        menuButton.setOnAction(e -> this.sceneManager.activateScene(Global.START_SCREEN_LABEL));

        stackPane.getChildren().add(menuButton);
    }


    /**
     * Getter method for the game screen
     *
     * @return game screen
     * @author William
     * @author Alexa
     * @author Charlie
     */
    public GameScreen getGameScreen() {
        return new GameScreen(this.sceneManager);
    }
}
