/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Alexa Andron
 * Date: 5/4/25
 * Time: 8:32 PM
 *
 * Project: csci205_final_project
 * Package: PACKAGE_NAME
 * Class: DoodleCharTest
 *
 * Description:
 *
 * ****************************************
 */

import org.ACTW.Direction;
import org.ACTW.charactersandmobs.DoodleChar;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DoodleCharTest {

    private DoodleChar doodle;

    @Test
    void testInitialImageViewNotNull() {
        assertNotNull(doodle.getDoodleView());
    }

    @Test
    void testSetPosition() {
        doodle.setDoodlePosition(100, 200);
        assertEquals(100, doodle.getX(), 0.01);
        assertEquals(200, doodle.getY(), 0.01);
    }

    @Test
    void testHandleMovementRight(){

        doodle.setDoodlePosition(50, 50);
        doodle.handleDoodleMovement(Direction.RIGHT);

        assertEquals(55, doodle.getX(), 0.01);
    }

    @Test
    void testHandleMovementLeft()  {

        doodle.setDoodlePosition(50, 50);
        doodle.handleDoodleMovement(Direction.LEFT);

        assertEquals(45, doodle.getX(), 0.01);
    }


}
