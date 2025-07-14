package org.ACTW.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.ACTW.Global;
import org.ACTW.SceneManager;


/**
 * Class for StartScreen scene builder. Builds and manages the start screen scene.
 *
 * @author William
 */
public class StartScreen {
    // private class variables
    private final SceneManager sceneManager;  // this is needed to switch scenes
    private final Pane stackPane;

    // private final class variables
    private final String GAME_SCENE_LABEL;  // this is also needed to switch scenes
    private final String BTN_IMG_PATH = "start-button.png";
    private final String TITLE_GRAPHIC = "title.png";

    /**
     * Constructor for the StartScreen class.
     */
    public StartScreen(SceneManager sceneManager, String gameSceneLabel) {
        if (sceneManager == null) {
            throw new NullPointerException("sceneManager is null");
        }

        // initialize stack pane
        this.stackPane = new Pane();

        // assign scene manager and game scene label
        this.sceneManager = sceneManager;
        this.GAME_SCENE_LABEL = gameSceneLabel;

        //Set background image as a new image
        Image backgroundImage = new Image("newbackground.png");
        ImageView backgroundView = new ImageView(backgroundImage);

        //Set fit to fill screen
        backgroundView.setFitHeight(Global.SCREEN_HEIGHT);
        backgroundView.setFitWidth(Global.SCREEN_WIDTH);
        stackPane.getChildren().addFirst(backgroundView);

        //Create title text graphic and space on screen
        Image titleImage = new Image(TITLE_GRAPHIC);
        ImageView titleView = new ImageView(titleImage);
        titleView.setPreserveRatio(true);
        titleView.setFitWidth(400);
        titleView.setLayoutX(10);
        titleView.setLayoutY(50);
        stackPane.getChildren().add(titleView);
    }

    /**
     * Builds and returns the start screen's scene.
     *
     * @return the start screen's scene
     * @author William
     */
    public Scene getScene() {
        // make the scene itself
        Scene scene = new Scene(stackPane, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

        // build and start the window
        addStartButton();

        // return the scene
        return scene;
    }

    /**
     * Adds the start button to the passed stage variable. Adds an image and then an overlapped button.
     *
     * @author William
     */
    private void addStartButton() {
        // get and style image
        ImageView imageView = new ImageView(BTN_IMG_PATH);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true); // keep the aspect ratio

        // create button object
        Button startButton = new Button();
        startButton.setGraphic(imageView);

        // set styling
        startButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

        // center on screen
        startButton.setLayoutX((stackPane.getWidth() - imageView.getFitWidth()) / 2);
        startButton.setLayoutY((stackPane.getHeight() - imageView.getFitHeight()) / 2);

        // on click, the button activates the game scene
        startButton.setOnAction(e -> {
                    Scene newGame = getGameScreen();
                    this.sceneManager.removeScene(this.GAME_SCENE_LABEL);
                    this.sceneManager.addScene(this.GAME_SCENE_LABEL, newGame);
                    this.sceneManager.activateScene(this.GAME_SCENE_LABEL);
                }
        );

        stackPane.getChildren().add(startButton);
    }

    /**
     * Getter method for game screen scene
     *
     * @return the game screen scene
     * @author William
     */
    private Scene getGameScreen() {
        GameScreen gameScreen = new GameScreen(this.sceneManager);
        return gameScreen.getScene();
    }
}
