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
                
                // Ensure all sprites that might be used for this type/state are mocked
                // ButtonViewer uses:
                // 1. spriteMap.get(type).get(state)
                // 2. iconMap.get(type)
                // 3. pickaxeIcon
                
                localViewer.draw(widget, gui, 0.1);
                
                // Verify button draw
                verify(buttonSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 100 && p.getY() == 100), eq(gui));
                
                // Verify icon draw
                // Position is sprite.getSize().getX()/2 + widget.getPosition().getX() + 5
                // 20/2 + 100 + 5 = 115
                verify(iconSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 115 && p.getY() == 100), eq(gui));
                
                if (state != WidgetState.UNSELECTED) {
                    // Verify pickaxeIcon draw
                    // Position is widget.getPosition().getX() - sprite.getSize().getX() / 2 - 4
                    // 100 - 20/2 - 4 = 86
                    verify(pickaxeSprite, atLeastOnce()).draw(argThat(p -> p.getX() == 86 && p.getY() == 100), eq(gui));
                } else {
                    verify(pickaxeSprite, never()).draw(any(), any());
                }
                
                // Re-stub necessary methods after reset
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
        
        // 4 types * 3 states + 4 types * 1 icon + 1 pickaxeIcon = 12 + 4 + 1 = 17 sprites
        verify(sprite, times(17)).center();
    }
}
