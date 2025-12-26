package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Button;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SettingsMenuControllerTest {
    private SettingsMenu menu;
    private SettingsMenuController controller;
    private Game game;
    private SoundPlayer soundPlayer;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(SettingsMenu.class);
        soundPlayer = mock(SoundPlayer.class);
        when(menu.getSoundPlayer()).thenReturn(soundPlayer);
        when(soundPlayer.getSound()).thenReturn(null);
        
        controller = new SettingsMenuController(menu);
        game = mock(Game.class);
    }

    @Test
    void testOnQuit() throws IOException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        controller.onQuit(game);
        verify(game).setState(any(MainMenuState.class));
        verify(soundPlayer).stop();
    }

    @Test
    void testUpdateQuit() throws IOException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));

        controller.update(game, List.of(Action.QUIT));
        verify(game).setState(any(MainMenuState.class));
        verify(soundPlayer).stop();
    }

    @Test
    void testUpdateOtherActions() throws IOException {
        controller.update(game, List.of(Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT));
        verify(game, never()).setState(any());
    }

    @Test
    void testUpdateSelectNotVolume() throws IOException {
        Button otherButton = mock(Button.class);
        when(otherButton.getButtonType()).thenReturn(ButtonType.PLAY);
        when(menu.getCurrentButton()).thenReturn(otherButton);
        
        float initialGain = GameSettings.getInstance().getMasterGain();
        controller.update(game, List.of(Action.SELECT));
        
        assertEquals(initialGain, GameSettings.getInstance().getMasterGain());
    }

    @Test
    void testUpdateSelectVolumeCycle() throws IOException {
        Button volumeButton = mock(Button.class);
        when(volumeButton.getButtonType()).thenReturn(ButtonType.VOLUME);
        when(menu.getCurrentButton()).thenReturn(volumeButton);

        float[] levels = new float[]{-20f, -10f, 0f, 10f, 20f};
        
        for (float startLevel : levels) {
            GameSettings.getInstance().setMasterGain(startLevel);
            controller.update(game, List.of(Action.SELECT));
            int expectedIdx = 0;
            for (int j = 0; j < levels.length; j++) {
                if (levels[j] == startLevel) {
                    expectedIdx = (j + 1) % levels.length;
                    break;
                }
            }
            assertEquals(levels[expectedIdx], GameSettings.getInstance().getMasterGain());
        }
        
        GameSettings.getInstance().setMasterGain(99.0f);
        controller.update(game, List.of(Action.SELECT));
        assertEquals(levels[1 % levels.length], GameSettings.getInstance().getMasterGain());
    }

    @Test
    void testUpdateOtherActionNone() throws IOException {
        controller.update(game, List.of(Action.NONE));
        verify(game, never()).setState(any());
    }

    @Test
    void testOnQuitException() throws IOException {
        Game gameMock = mock(Game.class);
        when(gameMock.getSpriteLoader()).thenAnswer(invocation -> {
            throw new IOException("Test IOException");
        });

        RuntimeException e = assertThrows(RuntimeException.class, () -> controller.onQuit(gameMock));
        assertEquals(IOException.class, e.getCause().getClass());
        assertEquals("Test IOException", e.getCause().getMessage());
    }
}
