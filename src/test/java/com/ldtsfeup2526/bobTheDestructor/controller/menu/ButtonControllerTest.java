package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Button;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Menu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class ButtonControllerTest {
    private ButtonController buttonController;
    private Menu menu;
    private Game game;
    private Button button;
    private SoundPlayer soundPlayer;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(Menu.class);
        game = mock(Game.class);
        button = mock(Button.class);
        soundPlayer = mock(SoundPlayer.class);
        when(menu.getCurrentButton()).thenReturn(button);
        when(menu.getSoundPlayer()).thenReturn(soundPlayer);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        when(spriteLoader.get(anyString())).thenReturn(mock(Sprite.class));
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(new java.awt.image.BufferedImage(16, 16, java.awt.image.BufferedImage.TYPE_INT_ARGB));

        buttonController = new ButtonController(menu);
    }

    @Test
    void testUpdatePlaySelect() throws IOException {
        when(button.getButtonType()).thenReturn(ButtonType.PLAY);
        buttonController.update(game, Collections.singletonList(Action.SELECT));
        verify(game).setState(any());
        verify(soundPlayer).stop();
    }

    @Test
    void testUpdateConfigSelect() throws IOException {
        when(button.getButtonType()).thenReturn(ButtonType.CONFIG);
        buttonController.update(game, Collections.singletonList(Action.SELECT));
        verify(game).setState(any());
        verify(soundPlayer).stop();
    }

    @Test
    void testUpdateExitSelect() throws IOException {
        when(button.getButtonType()).thenReturn(ButtonType.EXIT);
        buttonController.update(game, Collections.singletonList(Action.SELECT));
        verify(game).setState(null);
    }

    @Test
    void testUpdatePlayNone() throws IOException {
        when(button.getButtonType()).thenReturn(ButtonType.PLAY);
        buttonController.update(game, Collections.singletonList(Action.NONE));
        verify(game, never()).setState(any());
    }
}
