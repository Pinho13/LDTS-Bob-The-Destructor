package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Widget;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetState;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetType;
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

public class ButtonViewerTest {
    private ButtonViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(sprite.getSize()).thenReturn(new Size(10, 10));
        when(sprite.getOffset()).thenReturn(new Position(0, 0));
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        
        viewer = new ButtonViewer(spriteLoader);
    }

    @Test
    void testDrawAllStatesAndTypes() throws IOException {
        GUI gui = mock(GUI.class);
        SpriteLoader localSpriteLoader = mock(SpriteLoader.class);
        
        Sprite buttonSprite = mock(Sprite.class);
        when(buttonSprite.getSize()).thenReturn(new Size(20, 10));
        when(buttonSprite.getOffset()).thenReturn(new Position(0, 0));
        
        Sprite iconSprite = mock(Sprite.class);
        when(iconSprite.getSize()).thenReturn(new Size(5, 5));
        when(iconSprite.getOffset()).thenReturn(new Position(0, 0));
        
        Sprite pickaxeSprite = mock(Sprite.class);
        when(pickaxeSprite.getSize()).thenReturn(new Size(8, 8));
        when(pickaxeSprite.getOffset()).thenReturn(new Position(0, 0));

        // Default return for any sprite not specifically mocked
        when(localSpriteLoader.get(anyString())).thenReturn(buttonSprite);

        // Stub every single call made by the constructor
        String[] typeNames = {"play", "config", "credits", "exit"};
        for (String type : typeNames) {
            for (int i = 1; i <= 3; i++) {
                when(localSpriteLoader.get("sprites/ui/buttons/" + type + "/button" + i + ".png")).thenReturn(buttonSprite);
            }
            when(localSpriteLoader.get("sprites/ui/buttons/" + type + "/icon.png")).thenReturn(iconSprite);
        }
        when(localSpriteLoader.get("sprites/ui/buttons/selected_icon.png")).thenReturn(pickaxeSprite);

        ButtonViewer localViewer = new ButtonViewer(localSpriteLoader);
        
        for (WidgetType type : Arrays.asList(WidgetType.PLAY, WidgetType.CONFIG, WidgetType.CREDITS, WidgetType.EXIT)) {
            for (WidgetState state : WidgetState.values()) {
                Widget widget = mock(Widget.class);
                when(widget.getWidgetType()).thenReturn(type);
                when(widget.getWidgetState()).thenReturn(state);
                when(widget.getPosition()).thenReturn(new Position(100, 100));
                
                localViewer.draw(widget, gui, 0.1);
                
                verify(buttonSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 100 && p.getY() == 100), eq(gui));
                
                verify(iconSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 115 && p.getY() == 100), eq(gui));
                
                if (state != WidgetState.UNSELECTED) {
                    verify(pickaxeSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 86 && p.getY() == 100), eq(gui));
                } else {
                    verify(pickaxeSprite, never()).draw(any(), any());
                }
                
                reset(buttonSprite, iconSprite, pickaxeSprite);
                when(buttonSprite.getSize()).thenReturn(new Size(20, 10));
                when(iconSprite.getSize()).thenReturn(new Size(5, 5));
                when(pickaxeSprite.getSize()).thenReturn(new Size(8, 8));
                when(buttonSprite.getOffset()).thenReturn(new Position(0, 0));
                when(iconSprite.getOffset()).thenReturn(new Position(0, 0));
                when(pickaxeSprite.getOffset()).thenReturn(new Position(0, 0));
            }
        }
    }

    @Test
    void testConstructorCallsCenter() throws IOException {
        SpriteLoader localSpriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(sprite.getSize()).thenReturn(new Size(10, 10));
        when(localSpriteLoader.get(anyString())).thenReturn(sprite);
        
        new ButtonViewer(localSpriteLoader);
        
        verify(sprite, times(17)).center();
    }
}
