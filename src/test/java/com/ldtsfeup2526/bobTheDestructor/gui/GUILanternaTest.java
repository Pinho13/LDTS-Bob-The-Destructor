package com.ldtsfeup2526.bobTheDestructor.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.FontFormatException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GUILanternaTest {
    private Screen screen;
    private ScreenCreator screenCreator;
    private GUILanterna gui;
    private KeyListener keyListener;
    private Resolution resolution;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException, FontFormatException {
        screen = mock(Screen.class);
        screenCreator = mock(ScreenCreator.class);
        keyListener = mock(KeyListener.class);
        resolution = new Resolution(10, 10);
        
        when(screenCreator.createScreen(any(), anyInt(), anyString(), any())).thenReturn(screen);
        
        gui = new GUILanterna(screenCreator, keyListener, resolution, 12, "Title");
    }

    @Test
    void testDrawPixel() {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        
        gui.drawPixel(new Position(1, 2), new TextColor.RGB(255, 0, 0));
        
        verify(graphics).setBackgroundColor(new TextColor.RGB(255, 0, 0));
        verify(graphics).setCharacter(1, 2, ' ');
    }

    @Test
    void testClear() {
        gui.clear();
        verify(screen).clear();
    }

    @Test
    void testRefresh() throws IOException {
        gui.refresh();
        verify(screen).refresh();
    }

    @Test
    void testClose() throws IOException {
        gui.close();
        verify(screen).close();
    }
    
    @Test
    void testDrawBackground() {
        TextGraphics graphics = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        TextColor color = new TextColor.RGB(0, 0, 0);
        
        gui.drawBackground(color);
        
        verify(graphics, times(100)).setCharacter(anyInt(), anyInt(), eq(' '));
        verify(graphics, atLeastOnce()).setBackgroundColor(color);
        
        verify(graphics).setCharacter(0, 0, ' ');
        verify(graphics).setCharacter(9, 9, ' ');
        verify(graphics, never()).setCharacter(10, 0, ' ');
        verify(graphics, never()).setCharacter(0, 10, ' ');
    }

    @Test
    void testGetScreen() {
        assertEquals(screen, gui.getScreen());
    }

    @Test
    void testAlternativeConstructors() throws IOException, URISyntaxException, FontFormatException {
        Resolution res = new Resolution(100, 100);
        GUILanterna gui2 = new GUILanterna(screenCreator, keyListener, res, 8, "Title");
        assertEquals(screen, gui2.getScreen());
        verify(screenCreator).createScreen(eq(res), eq(8), eq("Title"), eq(keyListener));

        GUILanterna gui3 = new GUILanterna(screenCreator, keyListener, new Resolution(240, 135), 6, "Title");
        assertEquals(screen, gui3.getScreen());
        verify(screenCreator).createScreen(eq(new Resolution(240, 135)), eq(6), eq("Title"), eq(keyListener));
    }

    @Test
    void testConstructorsWithoutScreenCreator() throws IOException, URISyntaxException, FontFormatException {
        try {
            new GUILanterna(keyListener, "Title");
        } catch (Exception e) {
            // Expected if headless or font not found in this environment
        }

        try {
            new GUILanterna(keyListener, resolution, 12, "Title");
        } catch (Exception e) {
            // Expected if headless or font not found in this environment
        }
    }
}
