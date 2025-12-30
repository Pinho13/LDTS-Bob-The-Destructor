package com.ldtsfeup2526.bobTheDestructor.states;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class GameStateListenerTest {
    @Test
    void testInterface() {
        GameStateListener listener = state -> {};
        assertNotNull(listener);
        listener.notifyStateChange(mock(State.class));
    }
}
