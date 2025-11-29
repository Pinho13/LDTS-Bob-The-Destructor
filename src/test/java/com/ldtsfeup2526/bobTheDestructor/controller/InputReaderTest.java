package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.controller.input.InputReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class InputReaderTest {
    private InputReader inputReader;

    @BeforeEach
    void setUp() {
        inputReader = new InputReader();
    }

    @Test
    void addsKeyToInputPressedListTest() {
        KeyEvent keyEvent = Mockito.mock(KeyEvent.class);
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);

        inputReader.keyPressed(keyEvent);

        assertEquals(1, inputReader.getInputPressed().size());
        assertTrue(inputReader.getInputPressed().contains(KeyEvent.VK_UP));
    }

    @Test
    void doesNotDuplicateKeysTest() {
        KeyEvent keyEvent = Mockito.mock(KeyEvent.class);
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);

        inputReader.keyPressed(keyEvent);
        inputReader.keyPressed(keyEvent);

        assertEquals(1, inputReader.getInputPressed().size());
        assertTrue(inputReader.getInputPressed().contains(KeyEvent.VK_LEFT));
    }

    @Test
    void removesFromPressedAndFinishedListTest() {
        KeyEvent press = Mockito.mock(KeyEvent.class);
        Mockito.when(press.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);

        inputReader.keyPressed(press);

        inputReader.getInputFinished().add(KeyEvent.VK_SPACE);

        inputReader.keyReleased(press);

        assertFalse(inputReader.getInputPressed().contains(KeyEvent.VK_SPACE));
        assertFalse(inputReader.getInputFinished().contains(KeyEvent.VK_SPACE));
    }

    @Test
    void updateRemovesFinishedOnesTest() {
        KeyEvent spaceEvent = Mockito.mock(KeyEvent.class);
        Mockito.when(spaceEvent.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);
        inputReader.keyPressed(spaceEvent);

        KeyEvent enterEvent = Mockito.mock(KeyEvent.class);
        Mockito.when(enterEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        inputReader.keyPressed(enterEvent);

        inputReader.addInputFinished(KeyEvent.VK_SPACE);

        inputReader.updateInputPressed();

        assertFalse(inputReader.getInputPressed().contains(KeyEvent.VK_SPACE));
        assertTrue(inputReader.getInputPressed().contains(KeyEvent.VK_ENTER));
        assertTrue(inputReader.getInputFinished().contains(KeyEvent.VK_SPACE));
    }
}