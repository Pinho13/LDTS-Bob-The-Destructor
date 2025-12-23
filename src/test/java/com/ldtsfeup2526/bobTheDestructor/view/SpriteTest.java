package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpriteTest {
    @Test
    void testOffsetAndSize() {
        BufferedImage image = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
        Sprite sprite = new Sprite(image);
        
        assertEquals(10, sprite.getSize().getX());
        assertEquals(20, sprite.getSize().getY());
        
        sprite.setOffset(new Position(5, 5));
        assertEquals(5, sprite.getOffset().getX());
        
        sprite.center();
        assertEquals(-5, sprite.getOffset().getX());
        assertEquals(-10, sprite.getOffset().getY());
    }

    @Test
    void testDraw() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000); // Opaque Red
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(any(Position.class), any());
    }
}
