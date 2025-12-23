package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Button;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonState;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ButtonViewerTest {
    private ButtonViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(sprite.getSize()).thenReturn(new Size(10, 10));
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        
        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).draw(any(), any());

        viewer = new ButtonViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        Button button = new Button(ButtonType.PLAY, ButtonState.SELECTED, new Position(0, 0));
        GUI gui = mock(GUI.class);
        viewer.draw(button, gui, 0.1);
        
        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }
}
