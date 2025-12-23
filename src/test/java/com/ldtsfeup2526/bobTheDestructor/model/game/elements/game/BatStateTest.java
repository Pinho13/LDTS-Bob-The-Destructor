package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BatStateTest {
    @Test
    void testEnumValues() {
        assertNotNull(BatState.SLEEPING);
        assertNotNull(BatState.FLAPPING);
    }
}
