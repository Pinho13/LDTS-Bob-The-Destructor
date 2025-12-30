package com.ldtsfeup2526.bobTheDestructor.view.text;

import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StringParserTest {
    @Test
    void testSpecialCharactersMapping() throws IOException {
        SpriteLoader loader = mock(SpriteLoader.class);
        when(loader.get(anyString())).thenReturn(mock(Sprite.class));

        new StringParser(loader, "fonts/myfont/", 5);
        
        // Verify that the special characters are loaded from the correct paths
        verify(loader).get("sprites/ui/letters/space.png");
        verify(loader).get("sprites/ui/letters/rightBar.png");
        verify(loader).get("sprites/ui/letters/leftBar.png");
        
        // Also verify some standard character from FontParser superclass to be sure
        verify(loader).get("fonts/myfont/a.png");
        verify(loader).get("fonts/myfont/0.png");
    }
}