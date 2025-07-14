/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 4/21/25
 * Time: 3:17 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: Platform
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents a platform in the game that the doodle character can land on.
 * Platforms can be either normal or bounce platforms.
 *
 * @author Alexa
 * @author Trevor
 */
public class Platform {
    // The x and y coordinates of the platform
    public double x, y;

    // Width and height of the platform
    public static final double WIDTH = 60;
    public static final double HEIGHT = 20;

    // NEW: indicates if this is a bounce platform
    private final boolean isBouncePlatform;
    public String CharacterImagePath;
    public ImageView imageView;

    /**
     * Constructor for the platform object
     *
     * @param x the x-dir position
     * @param y the y-dir position
     * @param isBouncePlatform states whether the platform will have an extra bounce
     * @author Alexa
     * @author Trevor
     */
    // Updated constructor with bounce flag
    public Platform(double x, double y, boolean isBouncePlatform) {
        this.x = x;
        this.y = y;
        this.isBouncePlatform = isBouncePlatform;

        // Set image path based on platform type
        if (isBouncePlatform) {
            this.CharacterImagePath = "/bouncebar.png";
        } else {
            this.CharacterImagePath = "/bar.png";
        }

        //Load and set up the platform image
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(CharacterImagePath)));
        this.imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * Updates the vertical position of the platform by a given delta.
     *
     * @param dy the amount to move the platform vertically
     * @author Alexa
     */
    public void updatePosition(double dy) {
        y += dy;
        imageView.setY(y);
    }

    /**
     * Returns the JavaFX ImageView node for this platform, used to add it to the scene graph.
     *
     * @return the node representing the platform
     * @author Alexa
     */
    public ImageView getNode() {
        return imageView;
    }

    /**
     * Returns whether this platform is a bounce platform.
     *
     * @return true if this is a bounce platform, false otherwise
     * @author Trevor
     */
    public boolean isBouncePlatform() {
        return isBouncePlatform;
    }
}