/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 5/2/25
 * Time: 11:33 AM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: GameData
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW;

/**
 * Class to store the current score of the game to be passed to the stat screen.
 *
 * @author Alexa
 * @author Charlie
 * @author William
 */
public class GameData {
    private static int finalScore = 0;

    /**
     * Sets the score for the menu to display
     *
     * @param score score to set
     * @author Charlie
     */
    public static void setCurrentScore(int score) {
        finalScore = score;
    }

    /**
     * Getter to get the current score
     *
     * @return current score
     * @author Charlie
     */
    public static int getCurrentScore() {
        return finalScore;
    }
}