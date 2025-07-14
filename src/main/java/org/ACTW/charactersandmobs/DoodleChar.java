/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Charlie Zukauskas
 * Date: 4/16/25
 * Time: 3:37 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: DoodleChar
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW.charactersandmobs;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ACTW.Direction;
import org.ACTW.GameData;
import org.ACTW.Platform;
import org.ACTW.SceneManager;

import java.util.List;

/**
 * Class representing our Doodle Character.
 *
 * @author Charlie
 * @author Trevor
 * @author William
 */
public class DoodleChar {
    // private class variables
    private final ImageView doodleView;
    private final Image doodleRight = new Image("doodleR.png");
    private final Image doodleLeft = new Image("doodleL.png");
    private final Image doodleShoot = new Image("doodleS.png");

    /**
     * Constructor for a DoodleChar object.
     *
     * @author Charlie
     */
    public DoodleChar() {
        // Initialize doodleView
        this.doodleView = new ImageView(doodleRight);
    }

    /**
     * Handles the doodle's movement based on the direction it's moving.
     *
     * @param direction desired direction of movement
     * @author William
     * @author Charlie
     */
    public void handleDoodleMovement(Direction direction){
        switch (direction) {
            case LEFT:
                doodleView.setLayoutX(doodleView.getLayoutX() - 5);
                break;
            case RIGHT:
                doodleView.setLayoutX(doodleView.getLayoutX() + 5);
                break;
            case UP:
                break;

            default:
                throw new IllegalArgumentException("Invalid direction");
        }


    }

    /**
     * Checks platforms for collision with doodle character, handles normal and bouncy platforms.
     *
     * @param platforms list of platforms
     * @param velocityY velocity of doodle char
     * @return velocity based on platform
     * @author Trevor
     */
    public double checkPlatformCollision(List<Platform> platforms, double velocityY) {
        double currentBottom = getDoodleView().getLayoutY() + getDoodleView().getBoundsInParent().getHeight();
        double prevBottom = currentBottom - velocityY;

        // iterate through platforms
        for (Platform platform : platforms) {
            ImageView platformView = platform.getNode();
            double platformTop = platformView.getY();

            // check for collision
            if (getDoodleView().getBoundsInParent().intersects(platformView.getBoundsInParent())) {
                if (velocityY > 0 &&
                        currentBottom >= platformTop &&
                        prevBottom <= platformTop) {

                    getDoodleView().setLayoutY(platformTop - getDoodleView().getBoundsInParent().getHeight());

                    // bounce higher if platform is a bounce platform
                    return platform.isBouncePlatform() ? -24.0 : -12.0;
                }
            }
        }

        return velocityY; // no bounce occurred
    }

    /**
     * Checks for collision with monsters, handles death/results of said collision. Returns bounce velocity if bounce is
     * allowed, `null` otherwise.
     *
     * @param obstacleViews the ImageView objects for the obstacles
     * @param obstacles list of obstacles
     * @param velocityY the doodle char's velocity
     * @param sceneManager the SceneManager object
     * @param statScreenLabel the label for the Stat Screen
     * @return bounce velocity if bounce is allowed, `null` otherwise.
     * @author Trevor
     */
    public Double checkMonsterCollisionBounce(List<ImageView> obstacleViews, List<Obstacle> obstacles,
                                              double velocityY, SceneManager sceneManager, String statScreenLabel) {
        double currentBottom = getDoodleView().getLayoutY() + getDoodleView().getBoundsInParent().getHeight();
        double prevBottom = currentBottom - velocityY;

        // iterate through obstacles
        for (int i = 0; i < obstacleViews.size(); i++) {
            ImageView obstacleView = obstacleViews.get(i);
            Obstacle obstacle = obstacles.get(i);
            double obstacleTop = obstacleView.getLayoutY();

            if (getDoodleView().getBoundsInParent().intersects(obstacleView.getBoundsInParent())) {
                // If black hole is an obstacle, velocity doesn't matter because you cannot bounce off of it
                if (obstacle instanceof BlackHole) {
                    GameData.setCurrentScore(GameData.getCurrentScore());
                    sceneManager.activateScene(statScreenLabel);
                    return null;
                }

                if (velocityY > 0 &&
                        currentBottom >= obstacleTop &&
                        prevBottom <= obstacleTop) {

                    getDoodleView().setLayoutY(obstacleTop - getDoodleView().getBoundsInParent().getHeight());
                    return -12.0;
                } else {
                    GameData.setCurrentScore(GameData.getCurrentScore());
                    sceneManager.activateScene(statScreenLabel);
                    return null; // Die
                }
            }
        }

        return velocityY; // No collision
    }

    /**
     * Public method for handling Doodle movement and image orientation.
     *
     * @param direction direction of movement
     * @param isMoving whether Doodle Char is moving
     * @author William
     * @author Charlie
     */
    public void updateDoodleChar(Direction direction, boolean isMoving) {
        if (isMoving) {
            handleDoodleMovement(direction);
        }
        setDoodleDir(direction);
    }

    // Getters & Setters
    public ImageView getDoodleView() {
        return doodleView;
    }

    public double getDoodleHeight() {
        return doodleView.getImage().getHeight();
    }

    public double getDoodleWidth() {
        return doodleView.getImage().getWidth();
    }

    public double getX() {
        return doodleView.getLayoutX();
    }

    public double getY() {
        return doodleView.getLayoutY();
    }

    public void setDoodleDir(Direction direction){
        switch (direction){
            case UP:
                doodleView.setImage(doodleShoot);
                break;

            case LEFT:
                doodleView.setImage(doodleLeft);
                break;

            case RIGHT:
                doodleView.setImage(doodleRight);
                break;

            default:
                throw new IllegalArgumentException("Invalid direction");

        }


    }

    public void setDoodlePosition(double x, double y) {
        doodleView.setLayoutX(x);
        doodleView.setLayoutY(y);
    }
}

