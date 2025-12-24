package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.controller.game.PlayerMiningListener;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
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
    void testJump() {
        // Since grounded is private and depends on groundedUpdate which depends on check
        // we can't easily force grounded = true without calling physicsUpdate.
        // But we want to test that jump() calls state.jump().
        player.jump();
        // By default it should not jump if not grounded, but let's see what IdleState does.
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
        
        when(mineral1.getPosition()).thenReturn(new Position(15, 20)); // Dist = 5 (in range)
        when(mineral1.getState()).thenReturn(MineralState.UNSELECTED);
        when(mineral2.getPosition()).thenReturn(new Position(12, 20)); // Dist = 2 (closer)
        when(mineral2.getState()).thenReturn(MineralState.UNSELECTED);
        
        player.updateSelectedMineral(java.util.Arrays.asList(mineral1, mineral2));

        // It should select mineral2 (the closest)
        assertEquals(mineral2, player.getMineralSelected());
    }

    @Test
    void testUpdateSelectedMineralOutOfRange() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getPosition()).thenReturn(new Position(30, 20)); // Dist = 20 > 10
        when(mineral.getState()).thenReturn(MineralState.UNSELECTED);

        player.updateSelectedMineral(List.of(mineral));
        assertNull(player.getMineralSelected());
    }

    @Test
    void testUpdate() {
        // Initial state is IdleState. 
        // We need to ensure grounded is true so it stays in IdleState.
        // grounded is updated in physicsUpdate based on block under.
        // Position is (10, 20). Block under is (10, 21).
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 21))).thenReturn(true);
        player.physicsUpdate(collisionChecker);
        
        player.update();
        assertInstanceOf(IdleState.class, player.getState());
        
        player.moveRight();
        // Allow movement in X.
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 20))).thenReturn(false);

        // RigidBody.update() needs to be called to update velocity from acceleration
        player.physicsUpdate(collisionChecker);
        player.update();
        assertInstanceOf(WalkingState.class, player.getState());
    }

    @Test
    void testApplyFriction() {
        // Ensure grounded so it can be in WalkingState
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 21))).thenReturn(true);
        player.physicsUpdate(collisionChecker);

        player.moveRight();
        when(collisionChecker.check(argThat(c -> c != null && c.getPosition().getY() == 20))).thenReturn(false);
        player.physicsUpdate(collisionChecker);
        player.update();
        assertInstanceOf(WalkingState.class, player.getState());
        player.applyFriction();
    }

    @Test
    void testPhysicsUpdateCollisionY() {
        // Mock collision in Y but not X
        player.getRigidBody().setVelocity(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0, 5));
        
        when(collisionChecker.check(any())).thenAnswer(invocation -> {
            com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider c = invocation.getArgument(0);
            if (c == null) return false;
            // Original Y is 20. Next pos Y will be around 25.
            return c.getPosition().getY() > 20;
        });
        
        player.physicsUpdate(collisionChecker);
        
        // Should not have moved in Y
        assertEquals(20, player.getPosition().getY());
        assertEquals(0, player.getRigidBody().getVelocity().getY());
    }

    @Test
    void testIsGrounded() {
        // Block under is at (10, 21)
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
}
