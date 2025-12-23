package com.ldtsfeup2526.bobTheDestructor.controller.input;

import org.junit.jupiter.api.Test;
import java.awt.Component;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;

public class InputReaderTest {
    @Test
    void testKeyPressAndRelease() {
        InputReader reader = new InputReader();
        Component source = new Component() {};
        KeyEvent e = new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a');
        
        reader.keyPressed(e);
        assertTrue(reader.getInputPressed().contains(KeyEvent.VK_A));
        
        reader.keyReleased(e);
        assertFalse(reader.getInputPressed().contains(KeyEvent.VK_A));
        assertFalse(reader.getInputFinished().contains(KeyEvent.VK_A));
    }

    @Test
    void testAddInputFinished() {
        InputReader reader = new InputReader();
        reader.addInputFinished(KeyEvent.VK_A);
        assertTrue(reader.getInputFinished().contains(KeyEvent.VK_A));

        Component source = new Component() {};

        reader.keyPressed(new KeyEvent(source, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_B, 'b'));
        assertTrue(reader.getInputPressed().contains(KeyEvent.VK_B));
        
        reader.addInputFinished(KeyEvent.VK_B);
        reader.updateInputPressed();
        assertFalse(reader.getInputPressed().contains(KeyEvent.VK_B));
    }

    @Test
    void testKeyTyped() {
        InputReader reader = new InputReader();
        Component source = new Component() {};
        KeyEvent e = new KeyEvent(source, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 'a');
        reader.keyTyped(e);
    }
}
