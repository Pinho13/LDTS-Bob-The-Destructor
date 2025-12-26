package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
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
}
