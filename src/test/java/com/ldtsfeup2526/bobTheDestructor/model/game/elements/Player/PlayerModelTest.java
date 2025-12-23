package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerMiningListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerModelTest {
    private PlayerModel player;
    private Position startPos;

    @BeforeEach
    void setUp() {
        startPos = new Position(10, 20);
        player = new PlayerModel(startPos);
        Scene scene = mock(Scene.class);
        player.setScene(scene);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(startPos, player.getPosition());
        assertNotNull(player.getCollider());
        assertNotNull(player.getRigidBody());
        assertTrue(player.isLookingRight());
        assertInstanceOf(IdleState.class, player.getState());
        assertEquals(2.6f, player.getJumpForce(), 0.001f);
    }

    @Test
    void testSetLookRight() {
        player.setLookRight(false);
        assertFalse(player.isLookingRight());
    }

    @Test
    void testMovementCallsState() {
        player.moveRight();
        assertTrue(player.isLookingRight());
        assertTrue(player.getRigidBody().getAcceleration().getX() > 0);
    }

    @Test
    void testMoveLeft() {
        player.moveLeft();
        assertFalse(player.isLookingRight());
        assertTrue(player.getRigidBody().getAcceleration().getX() < 0);
    }

    @Test
    void testJump() {
        when(player.getScene().checkCollision(any())).thenReturn(true);
        player.jump();
        assertTrue(player.getRigidBody().getVelocity().getY() < 0);
    }

    @Test
    void testMine() {
        player.mine();
        assertInstanceOf(MiningState.class, player.getState());
    }

    @Test
    void testNotifyWhenAnimFinished() {
        player.mine();
        player.notifyWhenAnimFinished("MineAnim");
        assertInstanceOf(IdleState.class, player.getState());
        
        player.mine();
        player.notifyWhenAnimFinished("OtherAnim");
        assertInstanceOf(MiningState.class, player.getState());
    }

    @Test
    void testFindMineralInReach() {
        Scene scene = player.getScene();
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(12, 20)); // Close to player(10,20)
        when(mineral.getState()).thenReturn(MineralState.UNSELECTED);
        when(scene.getMineralModels()).thenReturn(List.of(mineral));

        player.findMineralInReach();

        assertEquals(mineral, player.getMineralSelected());
        verify(mineral).setState(MineralState.SELECTED);
    }

    @Test
    void testMiningListeners() {
        PlayerMiningListener listener = mock(PlayerMiningListener.class);
        player.addMiningListener(listener);
        player.notifyWhenPickaxeHit();
        verify(listener).onMiningFinished(player);

        player.removeMiningListener(listener);
        player.notifyWhenPickaxeHit();
        verify(listener, times(1)).onMiningFinished(player);
    }
}
