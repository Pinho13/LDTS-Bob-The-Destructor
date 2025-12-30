package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BatModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BatState;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BatViewerTest {
    private BatViewer viewer;
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

        viewer = new BatViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        BatModel model = mock(BatModel.class);
        when(model.getBatState()).thenReturn(BatState.SLEEPING);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);
        
        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }

    @Test
    void testDrawAllStates() {
        GUI gui = mock(GUI.class);
        for (BatState state : BatState.values()) {
            BatModel model = mock(BatModel.class);
            when(model.getBatState()).thenReturn(state);
            when(model.getPosition()).thenReturn(new Position(0, 0));
            viewer.draw(model, gui, 0.1);
            // Draw again to test cache
            viewer.draw(model, gui, 0.1);
        }
    }

    @Test
    void testDrawAnimationUpdate() throws IOException {
        SpriteLoader localLoader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        Sprite other = mock(Sprite.class);
        
        for (Sprite s : Arrays.asList(s1, s2, other)) {
            when(s.getSize()).thenReturn(new Size(1, 1));
            when(s.getOffset()).thenReturn(new Position(0, 0));
        }
        
        when(localLoader.get(anyString())).thenReturn(other);
        when(localLoader.get("sprites/bat/bat_sleep1.png")).thenReturn(s1);
        when(localLoader.get("sprites/bat/bat_sleep2.png")).thenReturn(s2);
        
        BatViewer localViewer = new BatViewer(localLoader);
        BatModel model = mock(BatModel.class);
        when(model.getBatState()).thenReturn(BatState.SLEEPING);
        when(model.getPosition()).thenReturn(new Position(10, 10));
        GUI gui = mock(GUI.class);
        
        // frameTime is 0.1
        localViewer.draw(model, gui, 0.0);
        verify(s1, atLeastOnce()).draw(any(), any());
        
        localViewer.draw(model, gui, 0.11);
        verify(s2, atLeastOnce()).draw(any(), any());
    }
}
