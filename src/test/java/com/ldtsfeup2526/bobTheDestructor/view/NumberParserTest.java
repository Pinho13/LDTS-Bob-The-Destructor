package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.text.NumberParser;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class NumberParserTest {
    @Test
    void testGet() throws IOException {
        SpriteLoader loader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        when(loader.get(anyString())).thenReturn(s1);

        NumberParser parser = new NumberParser(loader, "font/", 5);
        List<SpriteInstance> sprites = parser.get("12");
        
        assertEquals(2, sprites.size());
        assertEquals(0, sprites.get(0).offset().getX());
        assertEquals(5, sprites.get(1).offset().getX());

        for (int i = 0; i < 10; i++) {
            verify(loader).get("font/" + i + ".png");
        }

        verify(loader, never()).get("font/10.png");

        parser = new NumberParser(loader, "font/", 10);
        sprites = parser.get("12");
        assertEquals(0, sprites.get(0).offset().getX());
        assertEquals(10, sprites.get(1).offset().getX());
    }
    @Test
    void testGetInvalidChars() throws IOException {
        SpriteLoader loader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        when(loader.get(anyString())).thenReturn(s1);

        NumberParser parser = new NumberParser(loader, "font/", 5);
        List<SpriteInstance> sprites = parser.get("1a2");
        
        assertEquals(3, sprites.size()); // includes 'a' with null sprite
        assertNull(sprites.get(1).sprite());
    }
}
