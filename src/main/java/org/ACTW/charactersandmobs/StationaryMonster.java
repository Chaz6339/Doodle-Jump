/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 4/23/25
 * Time: 3:06 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: Monster
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW.charactersandmobs;

import java.util.Random;

/**
 * Class representing a Stationary Monster which is a type of Obstacle.
 *
 * @author Alexa
 */
public class StationaryMonster extends Obstacle {
    private static final String[] MONSTER_IMAGES = {
            "monster1.png", "monster3.png"
    };

    /**
     * Constructor for the StationaryMonster object.
     *
     * @author Alexa
     */
    public StationaryMonster() {
        Random random = new Random();
        int index = random.nextInt(MONSTER_IMAGES.length);
        this.characterImagePath = MONSTER_IMAGES[index];
    }
}
