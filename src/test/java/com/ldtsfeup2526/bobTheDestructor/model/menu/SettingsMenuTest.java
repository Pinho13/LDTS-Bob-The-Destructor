package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SettingsMenuTest {
    private SettingsMenu settingsMenu;

    @BeforeEach
    void setUp() {
        settingsMenu = new SettingsMenu();
    }

    @Test
    void testCreateButtons() {
        List<Button> buttons = settingsMenu.getButtons();
        assertEquals(1, buttons.size());
        assertEquals(ButtonType.VOLUME, buttons.get(0).getButtonType());
        assertEquals(ButtonState.SELECTED, buttons.get(0).getButtonState());
    }

    @Test
    void testGetSoundPlayer() {
        assertNotNull(settingsMenu.getSoundPlayer());
    }

    @Test
    void testMoveDown() {
        settingsMenu.moveDown();
        assertEquals(ButtonState.SELECTED, settingsMenu.getButtons().get(0).getButtonState());
    }

    @Test
    void testCreateSoundPlayerException() {
        try (var mockedSoundLoader = mockConstruction(com.ldtsfeup2526.bobTheDestructor.sounds.SoundLoader.class, (mock, context) -> {
            when(mock.loadSound(any(), any())).thenThrow(new RuntimeException("Test"));
        })) {
            SettingsMenu menu = new SettingsMenu();
            assertNull(menu.getSoundPlayer().getSound());
        }
    }
}
