package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WallpaperViewerTest {
    @Test
    void testDraw() throws IOException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        WallpaperViewer viewer = new WallpaperViewer(spriteLoader);
        
        verify(sprite).center();

        GUI gui = mock(GUI.class);
        viewer.draw(gui);
        
        verify(sprite).draw(argThat(pos -> 
            pos.getX() == Game.resolution.width()/2 && 
            pos.getY() == Game.resolution.height()/2), eq(gui));
    }
}
