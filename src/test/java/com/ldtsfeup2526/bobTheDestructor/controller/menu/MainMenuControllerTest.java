package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class MainMenuControllerTest {
    private MainMenu menu;
    private MainMenuController controller;
    private Game game;
    private WidgetController widgetController;

    @BeforeEach
    void setUp() {
        menu = mock(MainMenu.class);
        widgetController = mock(WidgetController.class);
        controller = new MainMenuController(menu, widgetController);
        game = mock(Game.class);
    }

    @Test
    void testUpdateUp() throws IOException {
        controller.update(game, List.of(Action.UP), 0.1);
        verify(menu).moveUp();
        verify(widgetController).updateWidgetState();
    }

    @Test
    void testUpdateDown() throws IOException {
        controller.update(game, List.of(Action.DOWN), 0.1);
        verify(menu).moveDown();
        verify(widgetController).updateWidgetState();
    }

    @Test
    void testUpdateQuit() throws IOException {
        controller.update(game, List.of(Action.QUIT), 0.1);
        verify(game).setState(null);
    }

    @Test
    void testUpdateOtherAction() throws IOException {
        controller.update(game, List.of(Action.SELECT), 0.1);
        verify(widgetController).update(eq(game), anyList(), eq(0.1));
    }
    @Test
    void testUpdateMultipleActions() throws IOException {
        controller.update(game, List.of(Action.UP, Action.DOWN), 0.1);
        verify(menu).moveUp();
        verify(menu).moveDown();
        verify(widgetController, times(2)).updateWidgetState();
    }
}
