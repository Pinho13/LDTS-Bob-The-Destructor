package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointingDirectionTest {
    @Test
    void testEnumValues() {
        assertEquals(4, PointingDirection.values().length);
        assertEquals(PointingDirection.UP, PointingDirection.valueOf("UP"));
        assertEquals(PointingDirection.DOWN, PointingDirection.valueOf("DOWN"));
        assertEquals(PointingDirection.LEFT, PointingDirection.valueOf("LEFT"));
        assertEquals(PointingDirection.RIGHT, PointingDirection.valueOf("RIGHT"));
    }
}
