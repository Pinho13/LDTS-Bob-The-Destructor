package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WidgetStateTest {
    @Test
    void testEnumValues() {
        assertEquals(3, WidgetState.values().length);
        assertNotNull(WidgetState.valueOf("SELECTED"));
        assertNotNull(WidgetState.valueOf("UNSELECTED"));
        assertNotNull(WidgetState.valueOf("CLICKED"));
    }
}