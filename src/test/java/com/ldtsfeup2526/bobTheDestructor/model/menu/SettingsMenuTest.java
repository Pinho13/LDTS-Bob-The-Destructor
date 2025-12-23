package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
