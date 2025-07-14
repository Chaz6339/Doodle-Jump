/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: William Chastain
 * Date: 4/22/2025
 * Time: 11:06 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: main
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ACTW.screens.GameScreen;
import org.ACTW.screens.StartScreen;
import org.ACTW.screens.StatScreen;

/**
 * MainApp class. This is where the game is build and ran. Entry point is `main(String[])` which starts the JavaFX app.
 * The `start(Stage)` method is the 'true' entry point.
 *
 * @author William
 * @author Charlie
 */
public class MainApp extends Application {
    // scene manager object
    private SceneManager sceneMan;


    // Private helper methods
    /**
     * Builds and returns the start screen scene.
     *
     * @return the start screen scene
     * @author William
     * @author Charlie
     */
    private Scene getStartScene() {
        StartScreen startScreen = new StartScreen(this.sceneMan, Global.GAME_SCREEN_LABEL);
        return startScreen.getScene();  // build and return start screen
    }

    /**
     * Builds and returns the stat screen scene.
     *
     * @return the game screen scene
     * @author William
     */
    private Scene getStatScene() {
        StatScreen statScreen = new StatScreen(this.sceneMan);
        return statScreen.getScene();  // build and return game scene
    }

    // Public methods
    /**
     * Starts the Main Application.
     *
     * @param primaryStage the primary stage with which to use scenes
     * @author William
     */
    @Override
    public void start(Stage primaryStage) {
        // initialize scene manager
        sceneMan = new SceneManager(primaryStage);

        // game screen
        GameScreen gameScreen = new GameScreen(this.sceneMan);

        // add scenes to scene manager
        sceneMan.addScene(Global.START_SCREEN_LABEL, getStartScene());
        sceneMan.addScene(Global.GAME_SCREEN_LABEL, gameScreen.getScene());
        sceneMan.addScene(Global.STAT_SCREEN_LABEL, getStatScene());

        // activate the start screen
        sceneMan.activateScene(Global.START_SCREEN_LABEL);

        // show the stage
        primaryStage.show();
    }

    /**
     * Entry point function, launches app.
     *
     * @param args program arguments
     * @author William
     */
    public static void main(String[] args) {
        launch(args);
    }
}