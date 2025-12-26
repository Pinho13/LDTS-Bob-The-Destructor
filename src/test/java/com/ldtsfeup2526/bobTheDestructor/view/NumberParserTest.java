package com.ldtsfeup2526.bobTheDestructor.view;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NumberParserTest {
    @Test
    void testGet() throws IOException {
        SpriteLoader loader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        when(loader.get(anyString())).thenReturn(s1);

        NumberParser parser = new NumberParser(loader, "font/");
        List<SpriteInstance> sprites = parser.get("12");
        
        assertEquals(2, sprites.size());
        assertEquals(0, sprites.get(0).offset().getX());
        assertEquals(5, sprites.get(1).offset().getX());

        for (int i = 0; i < 10; i++) {
            verify(loader).get("font/num" + i + ".png");
        }

        verify(loader, never()).get("font/num10.png");

        parser = new NumberParser(loader, "font/", 10);
        sprites = parser.get("12");
        assertEquals(0, sprites.get(0).offset().getX());
        assertEquals(10, sprites.get(1).offset().getX());
    }
}
