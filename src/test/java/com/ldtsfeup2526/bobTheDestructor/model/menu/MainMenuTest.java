package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuTest {
    private MainMenu mainMenu;

    @BeforeEach
    void setUp() {
        mainMenu = new MainMenu();
    }

    @Test
    void testCreateButtons() {
        List<Button> buttons = mainMenu.getButtons();
        assertEquals(4, buttons.size());
        assertEquals(ButtonType.PLAY, buttons.get(0).getButtonType());
        assertEquals(ButtonState.SELECTED, buttons.get(0).getButtonState());
        assertEquals(ButtonType.CONFIG, buttons.get(1).getButtonType());
        assertEquals(ButtonType.CREDITS, buttons.get(2).getButtonType());
        assertEquals(ButtonType.EXIT, buttons.get(3).getButtonType());
    }

    @Test
    void testMoveDown() {
        mainMenu.moveDown();
        assertEquals(ButtonState.UNSELECTED, mainMenu.getButtons().get(0).getButtonState());
        assertEquals(ButtonState.SELECTED, mainMenu.getButtons().get(1).getButtonState());
        assertEquals(1, getSelectedIndex(mainMenu));

        mainMenu.moveDown();
        assertEquals(2, getSelectedIndex(mainMenu));

        mainMenu.moveDown();
        assertEquals(3, getSelectedIndex(mainMenu));

        mainMenu.moveDown();
        assertEquals(0, getSelectedIndex(mainMenu));
    }

    @Test
    void testMoveUp() {
        mainMenu.moveUp();
        assertEquals(3, getSelectedIndex(mainMenu));

        mainMenu.moveUp();
        assertEquals(2, getSelectedIndex(mainMenu));
    }

    @Test
    void testGetSoundPlayer() {
        assertNotNull(mainMenu.getSoundPlayer());
    }

    @Test
    void testCreateSoundPlayerException() {
        try (var mockedSoundLoader = mockConstruction(com.ldtsfeup2526.bobTheDestructor.sounds.SoundLoader.class, (mock, context) -> {
            when(mock.loadSound(any(), any())).thenThrow(new RuntimeException("Test"));
        })) {
            MainMenu menu = new MainMenu();
            assertInstanceOf(com.ldtsfeup2526.bobTheDestructor.sounds.NullSoundPlayer.class, menu.getSoundPlayer());
        }
    }

    private int getSelectedIndex(Menu menu) {
        for (int i = 0; i < menu.getButtons().size(); i++) {
            if (menu.getButtons().get(i).getButtonState() == ButtonState.SELECTED) {
                return i;
            }
        }
        return -1;
    }
}
