package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.controller.input.InputReader;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ActionParserTest {
    private ActionParser actionParser;
    private InputReader inputReader;

    @BeforeEach
    void setUp() {
        actionParser = new ActionParser();
        inputReader = actionParser.getInputReader();
    }

    private Component eventSource() {
        return new Component() { };
    }

    @Test
    void getInputReaderTest() {
        assertNotNull(actionParser.getInputReader());
    }

    @Test
    void getTest() {
        assertNotNull(actionParser.get());
        assertTrue(actionParser.get().isEmpty());
    }

    @Test
    void testUpKeyMapping() {
        KeyEvent event = new KeyEvent(eventSource(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        inputReader.keyPressed(event);

        List<Action> actions = actionParser.get();
        assertTrue(actions.contains(Action.UP));
    }

    @Test
    void testWASDMappings() {
        KeyEvent event = new KeyEvent(eventSource(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a');
        inputReader.keyPressed(event);

        List<Action> actions = actionParser.get();
        assertTrue(actions.contains(Action.LEFT));
    }

    @Test
    void testQuitAction() {
        KeyEvent event = new KeyEvent(eventSource(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
        inputReader.keyPressed(event);

        List<Action> actions = actionParser.get();
        assertTrue(actions.contains(Action.QUIT));
    }

    @Test
    void testContinuousMovement() {
        // IMPORTANT: continuous movement requires allowKeyHold=true, which happens in GameState
        actionParser.notifyStateChange(mock(GameState.class));

        KeyEvent event = new KeyEvent(eventSource(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        inputReader.keyPressed(event);

        assertTrue(actionParser.get().contains(Action.RIGHT));
        assertTrue(actionParser.get().contains(Action.RIGHT));

        inputReader.keyReleased(event);

        assertFalse(actionParser.get().contains(Action.RIGHT));
    }

    @Test
    void testOneShotAction() {
        KeyEvent event = new KeyEvent(eventSource(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' ');
        inputReader.keyPressed(event);

        assertTrue(actionParser.get().contains(Action.JUMP));
        assertFalse(actionParser.get().contains(Action.JUMP));
    }
}
