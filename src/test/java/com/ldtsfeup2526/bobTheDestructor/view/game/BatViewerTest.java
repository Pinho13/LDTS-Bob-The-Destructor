package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BatModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BatState;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
}
