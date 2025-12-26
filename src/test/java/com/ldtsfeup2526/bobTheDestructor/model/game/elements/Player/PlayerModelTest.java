package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerMiningListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerModelTest {
    private PlayerModel player;
    private Position startPos;

    private CollisionChecker collisionChecker;

    @BeforeEach
    void setUp() {
        startPos = new Position(10, 20);
        player = new PlayerModel(startPos);
        collisionChecker = mock(CollisionChecker.class);
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
    void testJumpCallsState() {
        PlayerState mockState = mock(PlayerState.class);
        try {
            java.lang.reflect.Field stateField = PlayerModel.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.set(player, mockState);
        } catch (Exception e) { fail(e); }

        assertTrue(player.jump());
        verify(mockState).jump();
        
        try {
            java.lang.reflect.Field stateField = PlayerModel.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.set(player, null);
        } catch (Exception e) { fail(e); }
        assertFalse(player.jump());
    }

    @Test
    void testPhysicsUpdateCallsSetters() {
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 1));
        when(collisionChecker.check(any())).thenReturn(false);
        
        com.ldtsfeup2526.bobTheDestructor.model.spatials.Position oldPos = player.getPosition();
        player.physicsUpdate(collisionChecker);
        
        assertNotEquals(oldPos, player.getPosition());
        assertEquals(player.getPosition(), player.getCollider().getPosition());
        assertEquals(player.getPosition().getX().floatValue(), player.getRigidBody().getPosition().getX());
        assertEquals(player.getPosition().getY().floatValue(), player.getRigidBody().getPosition().getY());
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
    void testUpdateSelectedMineral() {
        MineralModel mineral1 = mock(MineralModel.class);
        MineralModel mineral2 = mock(MineralModel.class);

        when(mineral1.getPosition()).thenReturn(new Position(15, 20));
        when(mineral1.getState()).thenReturn(MineralState.UNSELECTED);
        when(mineral2.getPosition()).thenReturn(new Position(12, 20));
        when(mineral2.getState()).thenReturn(MineralState.UNSELECTED);

        player.updateSelectedMineral(java.util.Arrays.asList(mineral1, mineral2));

        assertEquals(mineral2, player.getMineralSelected());

        MineralModel mineral3 = mock(MineralModel.class);
        when(mineral3.getPosition()).thenReturn(new Position(20, 20));
        when(mineral3.getState()).thenReturn(MineralState.UNSELECTED);

        player.updateSelectedMineral(List.of(mineral3));
        assertEquals(mineral3, player.getMineralSelected());

        when(mineral3.getPosition()).thenReturn(new Position(21, 20));
    }

    @Test
    void testUpdateSelectedMineralOutOfRange() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(30, 20));
        when(mineral.getState()).thenReturn(MineralState.UNSELECTED);

        player.updateSelectedMineral(List.of(mineral));
        assertNull(player.getMineralSelected());
    }

    @Test
    void testUpdate() {
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 21))).thenReturn(true);
        player.physicsUpdate(collisionChecker);

        player.update();
        assertInstanceOf(IdleState.class, player.getState());

        player.moveRight();
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 20))).thenReturn(false);

        player.physicsUpdate(collisionChecker);
        player.update();
        assertInstanceOf(WalkingState.class, player.getState());
    }

    @Test
    void testApplyFriction() {
        PlayerState mockState = mock(PlayerState.class);
        try {
            java.lang.reflect.Field stateField = PlayerModel.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.set(player, mockState);
        } catch (Exception e) { fail(e); }

        assertTrue(player.applyFriction());
        verify(mockState).applyFriction();

        try {
            java.lang.reflect.Field stateField = PlayerModel.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.set(player, null);
        } catch (Exception e) { fail(e); }
        assertFalse(player.applyFriction());
    }

    @Test
    void testPhysicsUpdateCollisionX() {
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(5, 0));
        player.getRigidBody().setAcceleration(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(1, 0));

        when(collisionChecker.check(any())).thenAnswer(invocation -> {
            com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider c = invocation.getArgument(0);
            if (c == null) return false;
            return c.getPosition().getX() > 10 && c.getPosition().getY() == 20;
        });

        player.physicsUpdate(collisionChecker);

        assertEquals(10, player.getPosition().getX());
        assertEquals(0, player.getRigidBody().getVelocity().getX());
        assertEquals(0, player.getRigidBody().getAcceleration().getX());
    }

    @Test
    void testPhysicsUpdateCollisionXAndY() {
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(5, 5));

        when(collisionChecker.check(any())).thenReturn(true);

        player.physicsUpdate(collisionChecker);

        assertEquals(10, player.getPosition().getX());
        assertEquals(20, player.getPosition().getY());
        assertEquals(0, player.getRigidBody().getVelocity().getX());
        assertEquals(0, player.getRigidBody().getVelocity().getY());
    }

    @Test
    void testRemoveMiningListener() {
        PlayerMiningListener listener = mock(PlayerMiningListener.class);
        player.addMiningListener(listener);
        player.removeMiningListener(listener);
        player.notifyWhenPickaxeHit();
        verify(listener, never()).onMiningFinished(any());
    }

    @Test
    void testIsGrounded() {
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 21))).thenReturn(true);
        player.physicsUpdate(collisionChecker);
        assertTrue(player.isGrounded());

        when(collisionChecker.check(any())).thenReturn(false);
        player.physicsUpdate(collisionChecker);
        assertFalse(player.isGrounded());
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
    @Test
    void testJumpNullState() throws Exception {
        java.lang.reflect.Field stateField = PlayerModel.class.getDeclaredField("state");
        stateField.setAccessible(true);
        stateField.set(player, null);
        assertFalse(player.jump());
    }

    @Test
    void testUpdateSelectedMineralDestroyed() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(11, 20));
        when(mineral.getState()).thenReturn(MineralState.DESTROYED);

        player.updateSelectedMineral(List.of(mineral));
        assertNull(player.getMineralSelected());
    }
}
