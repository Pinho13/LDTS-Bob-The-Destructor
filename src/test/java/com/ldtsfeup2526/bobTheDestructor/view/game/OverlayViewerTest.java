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
        
        verify(gui, atLeast(2)).drawPixel(any(), any());
    }

    @Test
    void testDrawMarkerPosition() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite overlay = mock(Sprite.class);
        Sprite marker = mock(Sprite.class);
        Sprite num = mock(Sprite.class);
        
        when(spriteLoader.get("sprites/ui_overlay/ui_overlay.png")).thenReturn(overlay);
        when(spriteLoader.get("sprites/ui_overlay/marker.png")).thenReturn(marker);
        when(spriteLoader.get(contains("num"))).thenReturn(num);
        
        viewer = new OverlayViewer(spriteLoader);
        
        SceneManager model = mock(SceneManager.class);
        Scene scene = mock(Scene.class);
        when(model.getScene()).thenReturn(scene);
        when(model.getCurrentCavePathIndex()).thenReturn(2);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);
        
        verify(marker).draw(argThat(pos -> pos.getX() == 157 && pos.getY() == 50), eq(gui));

        verify(num, atLeastOnce()).setOffset(any());
        verify(num, atLeastOnce()).draw(any(), eq(gui));
    }
}
