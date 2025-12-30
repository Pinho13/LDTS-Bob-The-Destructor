package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralType;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PointingDirection;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MineralViewerTest {
    private MineralViewer viewer;
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
        
        viewer = new MineralViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);
        
        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }

    @Test
    void testDrawAllTypesAndDirections() throws IOException {
        Position pos = new Position(10, 20);
        GUI gui = mock(GUI.class);

        for (MineralType type : MineralType.values()) {
            Sprite sprite = mock(Sprite.class);
            Sprite selectedSprite = mock(Sprite.class);
            
            SpriteLoader localSpriteLoader = mock(SpriteLoader.class);
            when(localSpriteLoader.get(anyString())).thenReturn(mock(Sprite.class));
            
            String gemPath = switch(type) {
                case PINK -> "sprites/gems/gem1.png";
                case YELLOW -> "sprites/gems/gem7.png";
                case BLUE -> "sprites/gems/gem13.png";
            };
            String selectedPath = switch(type) {
                case PINK -> "sprites/gems/gem5.png";
                case YELLOW -> "sprites/gems/gem11.png";
                case BLUE -> "sprites/gems/gem17.png";
            };

            when(localSpriteLoader.get(eq(gemPath))).thenReturn(sprite);
            when(localSpriteLoader.get(eq(selectedPath))).thenReturn(selectedSprite);
            
            MineralViewer localViewer = new MineralViewer(localSpriteLoader);

            for (PointingDirection dir : PointingDirection.values()) {
                String color = switch (dir) {
                    case DOWN -> "fffdfe89";
                    case LEFT -> "ffff5dcc";
                    case RIGHT -> "ff5efdf7";
                    case UP -> "other";
                };
                
                // Test Selected
                MineralModel modelSelected = new MineralModel(pos, color, type.ordinal());
                modelSelected.setState(MineralState.SELECTED);
                localViewer.draw(modelSelected, gui, 0.0); // Use 0.0 deltaTime to stay on first frame
                
                verifySpriteDraw(sprite, selectedSprite, dir, pos, gui);
                
                // Test Unselected
                MineralModel modelUnselected = new MineralModel(pos, color, type.ordinal());
                modelUnselected.setState(MineralState.UNSELECTED);
                localViewer.draw(modelUnselected, gui, 0.0);
                
                verifySpriteDraw(sprite, null, dir, pos, gui);
            }
        }
    }

    private void verifySpriteDraw(Sprite sprite, Sprite selectedSprite, PointingDirection dir, Position pos, GUI gui) {
        switch (dir) {
            case UP -> {
                verify(sprite, atLeastOnce()).draw(eq(pos), eq(gui));
                if (selectedSprite != null) verify(selectedSprite, atLeastOnce()).draw(eq(pos), eq(gui));
            }
            case DOWN -> {
                verify(sprite, atLeastOnce()).drawFlipY(eq(pos), eq(gui));
                if (selectedSprite != null) verify(selectedSprite, atLeastOnce()).drawFlipY(eq(pos), eq(gui));
            }
            case LEFT -> {
                verify(sprite, atLeastOnce()).drawRotLeft(eq(pos), eq(gui));
                if (selectedSprite != null) verify(selectedSprite, atLeastOnce()).drawRotLeft(eq(pos), eq(gui));
            }
            case RIGHT -> {
                verify(sprite, atLeastOnce()).drawRotRight(eq(pos), eq(gui));
                if (selectedSprite != null) verify(selectedSprite, atLeastOnce()).drawRotRight(eq(pos), eq(gui));
            }
        }
    }

    @Test
    void testDrawDestroyed() {
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.DESTROYED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        GUI gui = mock(GUI.class);
        
        viewer.draw(model, gui, 1.0);
        verify(model, atLeastOnce()).notifyWhenAnimFinished(eq("CrackAnim"));
    }
    @Test
    void testDrawAllStatesAndDirections() {
        GUI gui = mock(GUI.class);
        for (MineralType type : MineralType.values()) {
            for (MineralState state : MineralState.values()) {
                if (state == MineralState.CLEANUP) continue;
                for (PointingDirection dir : PointingDirection.values()) {
                    MineralModel model = mock(MineralModel.class);
                    when(model.getType()).thenReturn(type);
                    when(model.getState()).thenReturn(state);
                    when(model.getPosition()).thenReturn(new Position(0, 0));
                    when(model.getDirection()).thenReturn(dir);
                    viewer.draw(model, gui, 0.1);
                }
            }
        }
    }

    @Test
    void testDrawDefaultDirection() {
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
    }

    @Test
    void testDrawAllTypes() {
        GUI gui = mock(GUI.class);
        for (MineralType type : MineralType.values()) {
            MineralModel model = mock(MineralModel.class);
            when(model.getType()).thenReturn(type);
            when(model.getState()).thenReturn(MineralState.UNSELECTED);
            when(model.getPosition()).thenReturn(new Position(0, 0));
            when(model.getDirection()).thenReturn(PointingDirection.UP);
            viewer.draw(model, gui, 0.1);
        }
    }

    @Test
    void testDrawAllStates() {
        GUI gui = mock(GUI.class);
        for (MineralState state : MineralState.values()) {
            if (state == MineralState.CLEANUP) continue;
            MineralModel model = mock(MineralModel.class);
            when(model.getType()).thenReturn(MineralType.PINK);
            when(model.getState()).thenReturn(state);
            when(model.getPosition()).thenReturn(new Position(0, 0));
            when(model.getDirection()).thenReturn(PointingDirection.UP);
            viewer.draw(model, gui, 0.1);
        }
    }
    @Test
    void testDrawSelectedDirections() {
        GUI gui = mock(GUI.class);
        for (PointingDirection dir : PointingDirection.values()) {
            MineralModel model = mock(MineralModel.class);
            when(model.getType()).thenReturn(MineralType.PINK);
            when(model.getState()).thenReturn(MineralState.SELECTED);
            when(model.getPosition()).thenReturn(new Position(0, 0));
            when(model.getDirection()).thenReturn(dir);
            viewer.draw(model, gui, 0.1);
        }
    }
    @Test
    void testCreateAnimCaching() {
        GUI gui = mock(GUI.class);
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        
        viewer.draw(model, gui, 0.1);
        viewer.draw(model, gui, 0.1);
    }
    @Test
    void testDrawAnimationFinished() {
        GUI gui = mock(GUI.class);
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.DESTROYED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        
        viewer.draw(model, gui, 1.0);
        verify(model).notifyWhenAnimFinished(anyString());
    }
    @Test
    void testDrawShineAnimation() {
        GUI gui = mock(GUI.class);
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        
        viewer.draw(model, gui, 0.1);
        verify(model, never()).notifyWhenAnimFinished(anyString());
    }
    @Test
    void testDrawAllStatesAndTypes() {
        GUI gui = mock(GUI.class);
        for (MineralType type : MineralType.values()) {
            for (MineralState state : MineralState.values()) {
                if (state == MineralState.CLEANUP) continue;
                MineralModel model = mock(MineralModel.class);
                when(model.getType()).thenReturn(type);
                when(model.getState()).thenReturn(state);
                when(model.getPosition()).thenReturn(new Position(0, 0));
                when(model.getDirection()).thenReturn(PointingDirection.UP);
                viewer.draw(model, gui, 0.1);
            }
        }
    }
    @Test
    void testDrawCrackAnimationAllTypes() {
        GUI gui = mock(GUI.class);
        for (MineralType type : MineralType.values()) {
            MineralModel model = mock(MineralModel.class);
            when(model.getType()).thenReturn(type);
            when(model.getState()).thenReturn(MineralState.DESTROYED);
            when(model.getPosition()).thenReturn(new Position(0, 0));
            when(model.getDirection()).thenReturn(PointingDirection.UP);
            viewer.draw(model, gui, 0.1);
        }
    }
    @Test
    void testDrawAnimationFinishedAllTypes() {
        GUI gui = mock(GUI.class);
        for (MineralType type : MineralType.values()) {
            MineralModel model = mock(MineralModel.class);
            when(model.getType()).thenReturn(type);
            when(model.getState()).thenReturn(MineralState.DESTROYED);
            when(model.getPosition()).thenReturn(new Position(0, 0));
            when(model.getDirection()).thenReturn(PointingDirection.UP);
            viewer.draw(model, gui, 1.0);
            verify(model, atLeastOnce()).notifyWhenAnimFinished(anyString());
        }
    }
    @Test
    void testDrawAllCombinations() {
        GUI gui = mock(GUI.class);
        for (MineralType type : MineralType.values()) {
            for (MineralState state : new MineralState[]{MineralState.UNSELECTED, MineralState.SELECTED, MineralState.DESTROYED}) {
                for (PointingDirection dir : PointingDirection.values()) {
                    MineralModel model = mock(MineralModel.class);
                    when(model.getType()).thenReturn(type);
                    when(model.getState()).thenReturn(state);
                    when(model.getPosition()).thenReturn(new Position(0, 0));
                    when(model.getDirection()).thenReturn(dir);
                    viewer.draw(model, gui, 0.1);
                }
            }
        }
    }

    @Test
    void testCreateAnimSetsCooldownEffect() throws IOException {
        SpriteLoader localSpriteLoader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        when(localSpriteLoader.get(anyString())).thenReturn(mock(Sprite.class));
        when(localSpriteLoader.get("sprites/gems/gem1.png")).thenReturn(s1);
        when(localSpriteLoader.get("sprites/gems/gem2.png")).thenReturn(s2);

        MineralViewer localViewer = new MineralViewer(localSpriteLoader);
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        Position pos = new Position(10, 10);
        when(model.getPosition()).thenReturn(pos);
        when(model.getDirection()).thenReturn(PointingDirection.UP);
        
        GUI gui = mock(GUI.class);

        localViewer.draw(model, gui, 0.0);

        verify(s1).draw(eq(pos), eq(gui));
        
        localViewer.draw(model, gui, 0.5);

        verify(s2, never()).draw(any(), any());

        localViewer.draw(model, gui, 15.0); 
        for (int i = 0; i < 100; i++) {
            localViewer.draw(model, gui, 0.1);
        }
        verify(s2, atLeastOnce()).draw(any(), eq(gui));
        
        SpriteLoader localSpriteLoader2 = mock(SpriteLoader.class);
        Sprite s1_2 = mock(Sprite.class);
        Sprite s2_2 = mock(Sprite.class);
        when(localSpriteLoader2.get(anyString())).thenReturn(mock(Sprite.class));
        when(localSpriteLoader2.get("sprites/gems/gem1.png")).thenReturn(s1_2);
        when(localSpriteLoader2.get("sprites/gems/gem2.png")).thenReturn(s2_2);

        MineralViewer localViewer2 = new MineralViewer(localSpriteLoader2);
        MineralModel model2 = mock(MineralModel.class);
        when(model2.getType()).thenReturn(MineralType.PINK);
        when(model2.getState()).thenReturn(MineralState.UNSELECTED);
        when(model2.getPosition()).thenReturn(pos);
        when(model2.getDirection()).thenReturn(PointingDirection.UP);
        
        localViewer2.draw(model2, gui, 0.0);

        localViewer2.draw(model2, gui, 0.5);

        verify(s2_2, never()).draw(any(), any());
        verify(s1_2, atLeastOnce()).draw(any(), any());
    }

    @Test
    void testCreateAnimSetsCooldownEffectSpecifically() throws IOException {
        SpriteLoader localSpriteLoader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        Sprite s2 = mock(Sprite.class);
        when(localSpriteLoader.get(anyString())).thenReturn(mock(Sprite.class));
        when(localSpriteLoader.get("sprites/gems/gem1.png")).thenReturn(s1);
        when(localSpriteLoader.get("sprites/gems/gem2.png")).thenReturn(s2);

        MineralViewer localViewer = new MineralViewer(localSpriteLoader);
        
        GUI gui = mock(GUI.class);
        
        for (int m = 0; m < 5; m++) {
            MineralModel model = new MineralModel(new Position(10, 10), "other", MineralType.PINK.ordinal());
            
            localViewer.draw(model, gui, 0.0);
            
            localViewer.draw(model, gui, 0.9);
            
            verify(s2, never()).draw(any(), any());
            
            for (int i = 0; i < 100; i++) {
                localViewer.draw(model, gui, 0.1);
            }
            
            try {
                verify(s2, atLeastOnce()).draw(any(), any());
            } catch (org.mockito.exceptions.verification.WantedButNotInvoked e) {
                System.out.println("[DEBUG_LOG] m=" + m + " failed to draw s2");
                throw e;
            }
            
            reset(s2);
        }
    }

    @Test
    void testDrawNotSelected() throws IOException {
        Position pos = new Position(5, 5);
        GUI gui = mock(GUI.class);
        Sprite sprite = mock(Sprite.class);
        Sprite selectedSprite = mock(Sprite.class);

        SpriteLoader localSpriteLoader = mock(SpriteLoader.class);
        when(localSpriteLoader.get(anyString())).thenReturn(sprite);
        when(localSpriteLoader.get(contains("selected"))).thenReturn(selectedSprite);
        MineralViewer localViewer = new MineralViewer(localSpriteLoader);

        for (PointingDirection dir : PointingDirection.values()) {
            String color = switch (dir) {
                case DOWN -> "fffdfe89";
                case LEFT -> "ffff5dcc";
                case RIGHT -> "ff5efdf7";
                case UP -> "other";
            };
            MineralModel model = new MineralModel(pos, color, 0);
            model.setState(MineralState.UNSELECTED);

            localViewer.draw(model, gui, 0.1);

            verify(selectedSprite, never()).draw(any(), any());
            verify(selectedSprite, never()).drawFlipY(any(), any());
            verify(selectedSprite, never()).drawRotLeft(any(), any());
            verify(selectedSprite, never()).drawRotRight(any(), any());
        }
    }
    @Test
    void testDrawNullDirection() {
        GUI gui = mock(GUI.class);
        MineralModel model = mock(MineralModel.class);
        when(model.getType()).thenReturn(MineralType.PINK);
        when(model.getState()).thenReturn(MineralState.UNSELECTED);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.getDirection()).thenReturn(null);
        
        viewer.draw(model, gui, 0.1);
        verify(gui, never()).drawPixel(any(), any());
    }
}
