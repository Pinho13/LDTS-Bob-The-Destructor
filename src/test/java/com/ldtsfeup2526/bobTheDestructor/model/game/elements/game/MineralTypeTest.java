package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralTypeTest {
    @Test
    void testEnumValues() {
        assertNotNull(MineralType.PINK);
        assertNotNull(MineralType.BLUE);
        assertNotNull(MineralType.YELLOW);
    }
}
