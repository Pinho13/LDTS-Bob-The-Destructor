package com.ldtsfeup2526.bobTheDestructor.view;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpriteTest {
    private boolean compareColors(TextColor color, int r, int g, int b) {
        if (!(color instanceof TextColor.RGB)) return false;
        TextColor.RGB rgb = (TextColor.RGB) color;
        return rgb.getRed() == r && rgb.getGreen() == g && rgb.getBlue() == b;
    }
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

        assertEquals(image, sprite.getImage());
    }

    @Test
    void testDraw() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 10 && pos.getY() == 10), any());
        
        image.setRGB(0, 0, 0x00FF0000);
        reset(gui);
        sprite.draw(new Position(10, 10), gui);
        verify(gui, never()).drawPixel(any(), any());
    }

    @Test
    void testDrawFlipX() {
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 0, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawFlipX(new Position(0, 0), gui);

        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 0, 255, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 1 && pos.getY() == 0), argThat(color -> compareColors(color, 255, 0, 0)));
    }

    @Test
    void testDrawFlipY() {
        BufferedImage image = new BufferedImage(1, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(0, 1, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawFlipY(new Position(0, 0), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 0, 255, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 1), argThat(color -> compareColors(color, 255, 0, 0)));
    }

    @Test
    void testDrawRotRight() {
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 0, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawRotRight(new Position(0, 0), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 255, 0, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 1), argThat(color -> compareColors(color, 0, 255, 0)));
    }

    @Test
    void testDrawRotLeft() {
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 0, 0xFF00FF00);
        Sprite sprite = new Sprite(image);
        GUI gui = mock(GUI.class);

        sprite.drawRotLeft(new Position(0, 0), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 1), argThat(color -> compareColors(color, 255, 0, 0)));
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 0 && pos.getY() == 0), argThat(color -> compareColors(color, 0, 255, 0)));
    }

    @Test
    void testDrawWithOffset() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 12 && pos.getY() == 13), any());
    }

    @Test
    void testDrawWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 1, 0xFFFF0000); 
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);
        
        sprite.draw(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawFlipXWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 1, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawFlipX(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawFlipYWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 0, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawFlipY(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawRotRightWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 1, 0xFFFF0000); 
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawRotRight(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }

    @Test
    void testDrawRotLeftWithOffsetAndNonZeroPixel() {
        BufferedImage image = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(1, 1, 0xFFFF0000);
        Sprite sprite = new Sprite(image);
        sprite.setOffset(new Position(2, 3));
        GUI gui = mock(GUI.class);

        sprite.drawRotLeft(new Position(10, 10), gui);
        verify(gui).drawPixel(argThat(pos -> pos.getX() == 13 && pos.getY() == 14), any());
    }
}
