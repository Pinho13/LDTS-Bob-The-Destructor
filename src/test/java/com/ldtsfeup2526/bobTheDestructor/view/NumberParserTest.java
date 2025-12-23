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
        List<Sprite> sprites = parser.get("12");
        
        assertEquals(2, sprites.size());
        verify(s1, times(2)).setOffset(any());
    }
}
