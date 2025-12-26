package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    void testSetStateAndGetSpriteLoader() {
        GUILanterna gui = mock(GUILanterna.class);
        ActionParser actionParser = mock(ActionParser.class);
        Game game = new Game(gui, actionParser);

        assertNotNull(game.getSpriteLoader());

        State<?> state = mock(State.class);
        game.setState(state);
        verify(actionParser).notifyStateChange(state);
    }

    @Test
    void testRunLoop() throws IOException, InterruptedException {
        GUILanterna gui = mock(GUILanterna.class);
        ActionParser actionParser = mock(ActionParser.class);
        Game game = new Game(gui, actionParser);

        State<?> state = mock(State.class);
        game.setState(state);

        doAnswer(invocation -> {
            game.setState(null);
            return null;
        }).when(state).update(eq(game), eq(gui), eq(actionParser), anyDouble());

        game.run();

        verify(state, times(1)).update(eq(game), eq(gui), eq(actionParser), anyDouble());
        verify(gui).close();
    }

    @Test
    void testRunLoopWithSleep() throws IOException, InterruptedException {
        GUILanterna gui = mock(GUILanterna.class);
        ActionParser actionParser = mock(ActionParser.class);
        Game game = spy(new Game(gui, actionParser));

        State<?> state = mock(State.class);
        game.setState(state);

        doAnswer(invocation -> {
            game.setState(null);
            return null;
        }).when(state).update(any(), any(), any(), anyDouble());

        game.run();
        
        verify(game).sleep(anyLong());
    }

    @Test
    void testSleepBoundary() throws InterruptedException {
        GUILanterna gui = mock(GUILanterna.class);
        ActionParser actionParser = mock(ActionParser.class);
        Game game = new Game(gui, actionParser);
        
        game.sleep(-1);
        game.sleep(0);
    }
    @Test
    void testMain() throws IOException, InterruptedException {
        try (var mockedGame = mockConstruction(Game.class, (mock, context) -> {
            doNothing().when(mock).run();
        })) {
            Game.main(new String[]{});
            assertEquals(1, mockedGame.constructed().size());
            verify(mockedGame.constructed().get(0)).run();
        }
    }

    @Test
    void testConstructors() {

        try (var mockedGui = mockConstruction(GUILanterna.class)) {
            try {
                Game game = new Game();
                assertNotNull(game.getSpriteLoader());
                
                ActionParser parser = mock(ActionParser.class);
                Game game2 = new Game(parser);
                assertNotNull(game2.getSpriteLoader());
            } catch (Exception e) {
            }
        }
    }

    @Test
    void testMainException() {
        try (var mockedGame = mockConstruction(Game.class, (mock, context) -> {
            doThrow(new RuntimeException("Test Exception")).when(mock).run();
        })) {
            // main catches exceptions, so this shouldn't throw
            Game.main(new String[]{});
        }
    }
}
