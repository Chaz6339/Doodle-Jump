/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: William Chastain
 * Date: 4/22/2025
 * Time: 9:16 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: SceneManager
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Class for managing Scenes. Scenes are added with a corresponding String label. Scenes can be activated, accessed, and
 * removed using said label. A *copy* of the hashmap used to store scenes and labels can be accessed as well.
 * Sample usage:
 * ```
 * SceneManager sceneMan = new SceneManager(stage);  // declare object
 * sceneMan.addScene("start-menu", startScene);  // add start scene
 * sceneMan.addScene("game", gameScene);  // add game scene
 * sceneMan.activateScene("start-menu");  // activate game scene
 * ```
 *
 * @author William
 */
public class SceneManager {
    // class variables
    private final Stage stage;  // stage can be final
    private final HashMap<String, Scene> sceneMap;  // keys are labels and the values are scenes

    /**
     * Constructor for SceneManager, assuming no args are passed. Just initializes the scene list.
     *
     * @author William
     */
    public SceneManager(Stage stage) {
        this.stage = stage;
        this.sceneMap = new HashMap<>();
    }

    /**
     * Adds a scene to be managed.
     *
     * @param label String label of the scene
     * @param scene scene to manage
     * @author William
     */
    public void addScene(String label, Scene scene) {
        this.sceneMap.put(label, scene);
    }

    /**
     * Removes a scene being managed.
     *
     * @param label String label of scene to remove
     * @author William
     */
    public void removeScene(String label) {
        this.sceneMap.remove(label);
    }

    /**
     * Activates a managed scene by label parameter.
     *
     * @param label label of the scene to load
     * @author William
     */
    public void activateScene(String label) {
        Scene toActivate = getScene(label);

        if (toActivate != null) {
            stage.setScene(toActivate);
        }
    }

    /**
     * Returns scene corresponding to label passed as parameter.
     *
     * @param label the String label for your Scene
     * @return the Scene requested
     * @author William
     */
    public Scene getScene(String label) {
        return this.sceneMap.get(label);
    }
}