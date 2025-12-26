package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class MenuTest {
    @Test
    void testMenuLogic() {
        Menu menu = new Menu() {
            @Override
            protected List<Button> createButtons() {
                List<Button> buttons = new ArrayList<>();
                buttons.add(new Button(ButtonType.PLAY, ButtonState.SELECTED, null));
                buttons.add(new Button(ButtonType.EXIT, ButtonState.UNSELECTED, null));
                return buttons;
            }

            @Override
            protected SoundPlayer createSoundPlayer() {
                return mock(SoundPlayer.class);
            }
        };

        assertEquals(2, menu.getNumberOfButtons());
        assertEquals(ButtonType.PLAY, menu.getCurrentButton().getButtonType());
        
        menu.moveDown();
        assertEquals(ButtonType.EXIT, menu.getCurrentButton().getButtonType());
        assertEquals(ButtonState.SELECTED, menu.getCurrentButton().getButtonState());
        
        menu.moveUp();
        assertEquals(ButtonType.PLAY, menu.getCurrentButton().getButtonType());
        
        assertNotNull(menu.getButtons());
        assertNotNull(menu.getSoundPlayer());
    }

    @Test
    void testGetSoundPlayerNull() {
        Menu menu = new Menu() {
            @Override protected List<Button> createButtons() { return new ArrayList<>(); }
            @Override protected SoundPlayer createSoundPlayer() { return null; }
        };
        assertInstanceOf(com.ldtsfeup2526.bobTheDestructor.sounds.NullSoundPlayer.class, menu.getSoundPlayer());
    }
}
