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
    void testVolumes() {
        GameSettings settings = GameSettings.getInstance();
        
        settings.setMasterVolume(0.8f);
        assertEquals(0.8f, settings.getMasterVolume());
        
        settings.setMusicVolume(0.7f);
        assertEquals(0.7f, settings.getMusicVolume());
        
        settings.setSfxVolume(0.6f);
        assertEquals(0.6f, settings.getSfxVolume());
    }
}
