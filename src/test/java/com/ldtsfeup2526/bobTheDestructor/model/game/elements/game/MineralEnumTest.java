package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralEnumTest {
    @Test
    void testMineralType() {
        assertEquals(3, MineralType.values().length);
        assertNotNull(MineralType.valueOf("PINK"));
        assertNotNull(MineralType.valueOf("BLUE"));
        assertNotNull(MineralType.valueOf("YELLOW"));
    }

    @Test
    void testMineralState() {
        assertEquals(4, MineralState.values().length);
        assertNotNull(MineralState.valueOf("UNSELECTED"));
        assertNotNull(MineralState.valueOf("SELECTED"));
        assertNotNull(MineralState.valueOf("DESTROYED"));
        assertNotNull(MineralState.valueOf("CLEANUP"));
    }

    @Test
    void testPointingDirection() {
        assertEquals(4, PointingDirection.values().length);
        assertNotNull(PointingDirection.valueOf("UP"));
        assertNotNull(PointingDirection.valueOf("DOWN"));
        assertNotNull(PointingDirection.valueOf("LEFT"));
        assertNotNull(PointingDirection.valueOf("RIGHT"));
    }
}
