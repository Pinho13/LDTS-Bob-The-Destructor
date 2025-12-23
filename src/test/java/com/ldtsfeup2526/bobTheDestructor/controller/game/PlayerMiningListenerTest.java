package com.ldtsfeup2526.bobTheDestructor.controller.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerMiningListenerTest {
    @Test
    void testInterface() {
        PlayerMiningListener listener = playerModel -> {};
        assertNotNull(listener);
    }
}
