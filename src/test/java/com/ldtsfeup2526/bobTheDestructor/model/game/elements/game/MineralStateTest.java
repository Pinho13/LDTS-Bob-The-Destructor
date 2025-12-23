package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralStateTest {
    @Test
    void testEnumValues() {
        assertNotNull(MineralState.SELECTED);
        assertNotNull(MineralState.UNSELECTED);
        assertNotNull(MineralState.DESTROYED);
        assertNotNull(MineralState.CLEANUP);
    }
}
