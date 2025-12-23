package com.ldtsfeup2526.bobTheDestructor.states;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IGameStateObserverTest {
    @Test
    void testInterface() {
        IGameStateObserver observer = state -> {};
        assertNotNull(observer);
    }
}
