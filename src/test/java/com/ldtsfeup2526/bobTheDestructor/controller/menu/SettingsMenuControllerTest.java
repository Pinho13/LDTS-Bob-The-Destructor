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
    void testUpdateSelectVolume() throws IOException {
        Button volumeButton = mock(Button.class);
        when(volumeButton.getButtonType()).thenReturn(ButtonType.VOLUME);
        when(menu.getCurrentButton()).thenReturn(volumeButton);
        
        float initialGain = GameSettings.getInstance().getMasterGain();
        controller.update(game, List.of(Action.SELECT));

        float newGain = GameSettings.getInstance().getMasterGain();
        boolean found = false;
        for (float level : new float[]{-20f, -10f, 0f, 10f, 20f}) {
            if (newGain == level) {
                found = true;
                break;
            }
        }
        assert(found);
    }
}
