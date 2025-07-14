/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 5/4/25
 * Time: 8:54 PM
 *
 * Project: csci205_final_project
 * Package: PACKAGE_NAME
 * Class: ChunkLoaderTest
 *
 * Description:
 *
 * ****************************************
 */


import org.ACTW.ChunkLoader;
import org.ACTW.DifficultyState;
import org.ACTW.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChunkLoaderTest {

    private ChunkLoader chunkLoader;
    private final int START_Y = 600;
    private final int CHUNK_HEIGHT = 400;

    @BeforeEach
    void setUp() {
        chunkLoader = new ChunkLoader(START_Y, CHUNK_HEIGHT);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        assertEquals(START_Y, chunkLoader.StartY);
        assertNotNull(chunkLoader.getPlatforms());
        assertTrue(chunkLoader.getPlatforms().isEmpty());
    }

    @Test
    void testGeneratePlatformsCreatesPlatforms() {
        DifficultyState easyDifficulty = DifficultyState.EASY;  // You might need to adjust this constructor

        chunkLoader.generatePlatforms(CHUNK_HEIGHT, easyDifficulty);
        ArrayList<Platform> platforms = chunkLoader.getPlatforms();

        // Check that platforms were generated
        assertFalse(platforms.isEmpty());

        // Check Y positions decrease from StartY downward
        for (Platform p : platforms) {
            assertTrue(p.getNode().getLayoutY() <= START_Y);
        }
    }
}
