package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerViewerTest {
    private PlayerViewer viewer;
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() throws IOException {
        spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);

        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            com.ldtsfeup2526.bobTheDestructor.gui.GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).draw(any(), any());

        doAnswer(invocation -> {
            Position pos = invocation.getArgument(0);
            com.ldtsfeup2526.bobTheDestructor.gui.GUI gui = invocation.getArgument(1);
            gui.drawPixel(pos, null);
            return null;
        }).when(sprite).drawFlipX(any(), any());

        viewer = new PlayerViewer(spriteLoader);
    }

    @Test
    void testDraw() {
        PlayerModel model = mock(PlayerModel.class);
        when(model.getState()).thenReturn(new IdleState(model));
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        
        GUI gui = mock(GUI.class);
        viewer.draw(model, gui, 0.1);

        verify(gui, atLeastOnce()).drawPixel(any(), any());
    }

    @Test
    void testDrawMining() {
        PlayerModel model = mock(PlayerModel.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        when(model.getRigidBody()).thenReturn(rb);
        when(rb.getAcceleration()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));

        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState miningState = new com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState(model, null);
        when(model.getState()).thenReturn(miningState);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        GUI gui = mock(GUI.class);
        
        viewer.draw(model, gui, 0.3);
        verify(model).notifyWhenPickaxeHit();
        verify(model).notifyWhenAnimFinished(eq("MineAnim"));
    }

    @Test
    void testStateChangeResetsAnimation() throws IOException {
        SpriteLoader spyLoader = mock(SpriteLoader.class);
        Sprite s1 = mock(Sprite.class);
        when(spyLoader.get(anyString())).thenReturn(s1);
        
        PlayerViewer v = new PlayerViewer(spyLoader);
        PlayerModel model = mock(PlayerModel.class);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        
        // Use MiningState as it has multiple frames
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        when(model.getRigidBody()).thenReturn(rb);
        when(rb.getAcceleration()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        when(rb.getVelocity()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 0));
        com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState miningState = new com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState(model, null);
        
        when(model.getState()).thenReturn(miningState);
        GUI gui = mock(GUI.class);
        
        Sprite m1 = mock(Sprite.class);
        Sprite m2 = mock(Sprite.class);
        Sprite m3 = mock(Sprite.class);
        when(spyLoader.get("sprites/player/player_mine1.png")).thenReturn(m1);
        when(spyLoader.get("sprites/player/player_mine2.png")).thenReturn(m2);
        when(spyLoader.get("sprites/player/player_mine3.png")).thenReturn(m3);
        
        PlayerViewer v2 = new PlayerViewer(spyLoader);
        
        v2.draw(model, gui, 0.0); // Frame 0 (m1)
        verify(m1).draw(any(), eq(gui));
        
        v2.draw(model, gui, 0.11); // Frame 1 (m2)
        verify(m2).draw(any(), eq(gui));
        
        // Change state to Idle
        when(model.getState()).thenReturn(new IdleState(model));
        v2.draw(model, gui, 0.0); 
        
        // Change back to Mining - should be reset to Frame 0 (m1)
        when(model.getState()).thenReturn(miningState);
        v2.draw(model, gui, 0.0); 
        verify(m1, times(2)).draw(any(), eq(gui));
    }

    @Test
    void testConstructorSetsOffsets() throws IOException {
        Sprite mockSprite = spriteLoader.get("");
        verify(mockSprite, atLeastOnce()).setOffset(any());
    }
    @Test
    void testDrawLookingRight() throws IOException {
        PlayerModel model = mock(PlayerModel.class);
        when(model.getState()).thenReturn(new IdleState(model));
        Position pos = new Position(10, 11);
        when(model.getPosition()).thenReturn(pos);
        when(model.isLookingRight()).thenReturn(true);
        
        GUI gui = mock(GUI.class);
        Sprite sprite = spriteLoader.get("");
        viewer.draw(model, gui, 0.1);

        verify(sprite, atLeastOnce()).draw(argThat(p -> p.getX() == 10 && p.getY() == 11), eq(gui));
        verify(sprite, never()).drawFlipX(any(), any());
    }

    @Test
    void testDrawLookingLeft() throws IOException {
        PlayerModel model = mock(PlayerModel.class);
        when(model.getState()).thenReturn(new IdleState(model));
        Position pos = new Position(12, 13);
        when(model.getPosition()).thenReturn(pos);
        when(model.isLookingRight()).thenReturn(false);
        
        GUI gui = mock(GUI.class);
        Sprite sprite = spriteLoader.get("");
        viewer.draw(model, gui, 0.1);

        verify(sprite, atLeastOnce()).drawFlipX(argThat(p -> p.getX() == 12 && p.getY() == 13), eq(gui));
        verify(sprite, never()).draw(any(), any());
    }
    @Test
    void testDrawAllStates() {
        Class<?>[] states = {
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.WalkingState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.JumpingState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.FallingState.class,
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.MiningState.class
        };
        
        GUI gui = mock(GUI.class);
        PlayerModel model = mock(PlayerModel.class);
        when(model.getPosition()).thenReturn(new Position(0, 0));
        when(model.isLookingRight()).thenReturn(true);
        
        for (Class<?> stateClass : states) {
            com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState state = (com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState) mock(stateClass);
            when(model.getState()).thenReturn(state);
            viewer.draw(model, gui, 0.1);
        }
    }
}
