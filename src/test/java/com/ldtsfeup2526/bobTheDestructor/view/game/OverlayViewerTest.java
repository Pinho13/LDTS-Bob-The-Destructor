package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OverlayViewerTest {
    private OverlayViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).draw(any(), any());
        
        viewer = new OverlayViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        SceneManager model = mock(SceneManager.class);
        Scene scene = mock(Scene.class);
        when(model.getScene()).thenReturn(scene);
        when(model.getTotalMineralsCollected()).thenReturn(10);
        when(scene.getCurrentMineralsCollected()).thenReturn(5);
        when(model.getCurrentCavePathIndex()).thenReturn(1);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);
        
        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }
}
