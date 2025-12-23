package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointingDirectionTest {
    @Test
    void testEnumValues() {
        assertNotNull(PointingDirection.UP);
        assertNotNull(PointingDirection.DOWN);
        assertNotNull(PointingDirection.LEFT);
        assertNotNull(PointingDirection.RIGHT);
    }
}
