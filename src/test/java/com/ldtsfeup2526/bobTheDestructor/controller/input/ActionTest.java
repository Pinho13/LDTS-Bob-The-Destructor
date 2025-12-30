package com.ldtsfeup2526.bobTheDestructor.controller.input;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
    @Test
    void testEnumValues() {
        assertEquals(9, Action.values().length);
        assertNotNull(Action.valueOf("UP"));
        assertNotNull(Action.valueOf("DOWN"));
        assertNotNull(Action.valueOf("LEFT"));
        assertNotNull(Action.valueOf("RIGHT"));
        assertNotNull(Action.valueOf("SELECT"));
        assertNotNull(Action.valueOf("QUIT"));
        assertNotNull(Action.valueOf("MINE"));
        assertNotNull(Action.valueOf("JUMP"));
        assertNotNull(Action.valueOf("NONE"));
    }
}