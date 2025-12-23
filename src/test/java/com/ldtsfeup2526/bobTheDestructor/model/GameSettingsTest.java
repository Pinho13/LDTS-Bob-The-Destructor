package com.ldtsfeup2526.bobTheDestructor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GameSettingsTest {
    @Test
    void testSingleton() {
        GameSettings instance1 = GameSettings.getInstance();
        GameSettings instance2 = GameSettings.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testMasterGain() {
        GameSettings settings = GameSettings.getInstance();
        settings.setMasterGain(10.0f);
        assertEquals(10.0f, settings.getMasterGain());
    }
}
