/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 4/21/25
 * Time: 2:08 PM
 *
 * Project: csci205_final_project
 * Package: org.ACTW
 * Class: ChunkLoader
 *
 * Description:
 *
 * ****************************************
 */

package org.ACTW;

import java.util.ArrayList;
import java.util.Random;

/**
 * The ChunkLoader class is responsible for generating and managing a chunk of platforms in the game. Chunks are
 * sections of the game's background that can be loaded as the doodle character moves upwards.
 *
 * @author Alexa
 * @author Trevor
 */
public class ChunkLoader {
    //List of platforms generated in this chunk
    public ArrayList<Platform> platforms;
    //Starting Y coordinate of the chunk
    public int StartY;

    /**
     * Constructs a new ChunkLoader starting at the specified Y.
     *
     * @param StartY the starting Y position for this chunk
     * @author Alexa
     */
    public ChunkLoader(int StartY) {
        this.StartY = StartY;
        platforms = new ArrayList<>();
    }

    /**
     * Generates platforms for the chunk based on the given height and difficulty level
     *
     * @param height the vertical height range to fill with platforms
     * @param difficulty the difficulty level, which controls spacing and platform types
     * @author Alexa
     * @author Trevor
     */
    public void generatePlatforms(int height, DifficultyState difficulty) {
        //Initialize random object
        Random random = new Random();
        //Adds platforms according to spacing by difficulty level
        for (int y = 0; y < height; y += difficulty.getPlatformSpacing()) {
            int x = random.nextInt(340);
            boolean isBouncePlatform = random.nextDouble() < difficulty.getBouncePlatformChance();
            Platform p = new Platform(x, StartY - y, isBouncePlatform);
            platforms.add(p);
        }
    }

    /**
     * Updates the Y-position of all platforms in the chunk by the given delta.
     * This simulates scrolling upward in the game
     *
     * @param dy the amount to move the platforms vertically
     * @author Alexa
     */
    public void update(double dy) {
        for (Platform p : platforms) {
            p.updatePosition(dy);
        }
        StartY += (int) dy;
    }

    /**
     * Returns the list of platforms contained in this chunk.
     *
     * @return an ArrayList of Platform objects
     * @author Alexa
     */
    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}