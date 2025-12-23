package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.mockito.Mockito.*;

public class MainMenuControllerTest {
    private MainMenu menu;
    private MainMenuController controller;
    private Game game;
    private SoundPlayer soundPlayer;

    @BeforeEach
    void setUp() throws IOException {
        menu = mock(MainMenu.class);
        soundPlayer = mock(SoundPlayer.class);
        when(menu.getSoundPlayer()).thenReturn(soundPlayer);

        when(soundPlayer.getSound()).thenReturn(null);
        
        controller = new MainMenuController(menu);
        game = mock(Game.class);
    }

    @Test
    void testOnQuit() {
        controller.onQuit(game);
        verify(game).setState(null);
    }

    @Test
    void testUpdateUp() throws IOException {
        controller.update(game, List.of(Action.UP));
        verify(menu).moveUp();
    }

    @Test
    void testUpdateDown() throws IOException {
        controller.update(game, List.of(Action.DOWN));
        verify(menu).moveDown();
    }

    @Test
    void testUpdateQuit() throws IOException {
        controller.update(game, List.of(Action.QUIT));
        verify(game).setState(null);
    }
}
