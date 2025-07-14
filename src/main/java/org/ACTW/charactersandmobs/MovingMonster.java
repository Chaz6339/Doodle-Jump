/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 4/23/25
 * Time: 3:14 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: MovingMonster
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW.charactersandmobs;

import javafx.scene.image.ImageView;
import java.util.Random;

/**
 * This represents a Moving Monster, which is a type of Obstacle.
 *
 * @author Alexa
 */
public class MovingMonster extends Obstacle {
    // private class variables
    private static final String[] MONSTER_IMAGES = {
            "monster4.png", "monster5.png", "monster6.png"
    };

    private ImageView imageView;
    private final double speed;
    private int direction;

    /**
     * Constructor for MovingMonster object.
     *
     * @author Alexa
     */
    public MovingMonster() {
        Random random = new Random();
        int index = random.nextInt(MONSTER_IMAGES.length);
        this.characterImagePath = MONSTER_IMAGES[index];

        this.speed = 2;
        this.direction = random.nextBoolean() ? -1 : 1;
    }

    /**
     * Function to update the monster's position.
     *
     * @author Alexa
     */
    public void updatePosition() {
        if (imageView == null) return;

        double newX = imageView.getLayoutX() + speed * direction;
        if (newX <= 0 || newX >= (400 - imageView.getFitWidth())) {
            direction *= -1;
            newX = Math.max(0, Math.min(newX, 400 - imageView.getFitWidth()));
        }
        imageView.setLayoutX(newX);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
