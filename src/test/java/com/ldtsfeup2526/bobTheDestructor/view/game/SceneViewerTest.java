package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SceneViewerTest {
    private SceneViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);

        doAnswer(invocation -> {
            com.ldtsfeup2526.bobTheDestructor.model.spatials.Position pos = invocation.getArgument(0);
            GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).draw(any(), any());

        viewer = new SceneViewer(spriteLoader);
    }

    @Test
    void testDraw() throws IOException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite structureSprite = mock(Sprite.class);
        Sprite detailsSprite = mock(Sprite.class);
        Sprite bgSprite = mock(Sprite.class);

        when(spriteLoader.get("cave/structure.png")).thenReturn(structureSprite);
        when(spriteLoader.get("cave/details.png")).thenReturn(detailsSprite);
        when(spriteLoader.get(contains("background/bg"))).thenReturn(bgSprite);

        SceneViewer viewer = new SceneViewer(spriteLoader);
        viewer.retrieveCaves(List.of("cave/"));

        Scene scene = mock(Scene.class);
        when(scene.getCaveFilePath()).thenReturn("cave/");

        GUI gui = mock(GUI.class);
        viewer.draw(scene, gui, 0.1);

        verify(bgSprite, atLeastOnce()).draw(any(), eq(gui));
        verify(structureSprite).draw(any(), eq(gui));
        verify(detailsSprite).draw(any(), eq(gui));
    }

    @Test
    void testDrawAnimationUpdate() throws IOException {
        SpriteLoader localLoader = mock(SpriteLoader.class);
        Sprite bg1 = mock(Sprite.class);
        Sprite bg2 = mock(Sprite.class);
        Sprite other = mock(Sprite.class);
        
        for (Sprite s : List.of(bg1, bg2, other)) {
            when(s.getSize()).thenReturn(new Size(1, 1));
            when(s.getOffset()).thenReturn(new Position(0, 0));
        }
        
        when(localLoader.get(anyString())).thenReturn(other);
        when(localLoader.get("background/bg1.png")).thenReturn(bg1);
        when(localLoader.get("background/bg2.png")).thenReturn(bg2);
        
        SceneViewer localViewer = new SceneViewer(localLoader);
        localViewer.retrieveCaves(List.of("cave/"));
        
        Scene scene = mock(Scene.class);
        when(scene.getCaveFilePath()).thenReturn("cave/");
        GUI gui = mock(GUI.class);
        
        // backgroundAnim frameTime is 0.125f
        localViewer.draw(scene, gui, 0.0);
        verify(bg1, atLeastOnce()).draw(any(), any());
        
        localViewer.draw(scene, gui, 0.13);
        verify(bg2, atLeastOnce()).draw(any(), any());
    }
}
