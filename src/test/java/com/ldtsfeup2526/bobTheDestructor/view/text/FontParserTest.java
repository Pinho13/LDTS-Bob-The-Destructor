package com.ldtsfeup2526.bobTheDestructor.view.text;

import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FontParserTest {
    @Test
    void testGet() throws IOException {
        SpriteLoader loader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        when(loader.get(anyString())).thenReturn(s1);

        FontParser parser = new FontParser(loader, "path/", "abc", 10);
        List<SpriteInstance> result = parser.get("AaB");
        
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).offset().getX());
        assertEquals(10, result.get(1).offset().getX());
        assertEquals(20, result.get(2).offset().getX());
    }

    @Test
    void testAdd() throws IOException {
        SpriteLoader loader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        when(loader.get("extra.png")).thenReturn(s1);

        FontParser parser = new FontParser(loader, "path/", "", 10);
        parser.add('!', "extra.png");
        List<SpriteInstance> result = parser.get("!");
        assertEquals(1, result.size());
        assertEquals(s1, result.get(0).sprite());
    }
}