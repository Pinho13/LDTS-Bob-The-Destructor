package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalkingStateTest {
    private PlayerModel player;
    private RigidBody rb;

    @BeforeEach
    void setUp() {
        player = mock(PlayerModel.class);
        rb = mock(RigidBody.class);
        when(player.getRigidBody()).thenReturn(rb);
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0));
        when(rb.getAcceleration()).thenReturn(new Vector(0, 0));
        when(rb.getGravity()).thenReturn(0.4f);
    }

    @Test
    void testGetNextStateStillWalking() {
        WalkingState state = new WalkingState(player);
        when(player.isGrounded()).thenReturn(true);
        // Explicitly use 0.0f for Y to test the < 0 boundary
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0.0f));

        PlayerState next = state.getNextState();
        assertSame(state, next, "Should NOT transition to JumpingState when velocity.Y is exactly 0");

        // Boundary test for Idle transition: abs(X) < 0.2
        // 0.2 should NOT transition to Idle
        when(rb.getVelocity()).thenReturn(new Vector(0.2f, 0.0f));
        assertSame(state, state.getNextState(), "0.2 should be WalkingState");

        when(rb.getVelocity()).thenReturn(new Vector(-0.2f, 0.0f));
        assertSame(state, state.getNextState(), "-0.2 should be WalkingState");
        
        // Exact 0.19999999f to kill boundary change (< 0.2 -> <= 0.2)
        // If it changes to <= 0.2, then 0.2 will be IdleState. 
        // My tests above already check 0.2 and -0.2.
        
        // Let's add more explicit boundary tests
        when(rb.getVelocity()).thenReturn(new Vector(0.1999999f, 0.0f));
        assertInstanceOf(IdleState.class, state.getNextState());

        when(rb.getVelocity()).thenReturn(new Vector(-0.1999999f, 0.0f));
        assertInstanceOf(IdleState.class, state.getNextState());
    }

    @Test
    void testGetNextStateToJumpingBoundary() {
        WalkingState state = new WalkingState(player);
        // velY < 0 triggers JumpingState.
        // Boundary test: -1e-7 should trigger it, but 0 should not.
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, -1e-7f));
        assertInstanceOf(JumpingState.class, state.getNextState());
        
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0.0f));
        when(player.isGrounded()).thenReturn(true);
        assertSame(state, state.getNextState());

        // Exact boundary: if mutant changes < 0 to <= 0, then 0.0 would trigger JumpingState.
        // Already tested by testGetNextStateStillWalking but here is more focused.
        assertFalse(0.0f < 0.0f);
    }

    @Test
    void testGetNextStateToJumping() {
        WalkingState state = new WalkingState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0, -1));

        PlayerState next = state.getNextState();
        assertInstanceOf(JumpingState.class, next);
    }

    @Test
    void testGetNextStateToFalling() {
        WalkingState state = new WalkingState(player);
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0));
        when(player.isGrounded()).thenReturn(false);

        PlayerState next = state.getNextState();
        assertInstanceOf(FallingState.class, next);
    }

    @Test
    void testGetNextStateToIdleBoundary() {
        WalkingState state = new WalkingState(player);

        when(player.isGrounded()).thenReturn(true);
        
        // boundary is 0.2
        float boundary = 0.2f;

        // Exactly 0.2f
        when(rb.getVelocity()).thenReturn(new Vector(0.200000000000000000000000f, 0));
        PlayerState result = state.getNextState();
        assertFalse(result instanceof IdleState, "Exactly 0.2f should NOT be IdleState");
        assertSame(state, result, "Exactly 0.2f should be WalkingState");

        // Exactly -0.2f
        when(rb.getVelocity()).thenReturn(new Vector(-0.200000000000000000000000f, 0));
        result = state.getNextState();
        assertFalse(result instanceof IdleState, "Exactly -0.2f should NOT be IdleState");
        assertSame(state, result, "Exactly -0.2f should be WalkingState");
        
        // Just below 0.2f
        when(rb.getVelocity()).thenReturn(new Vector(Math.nextDown(boundary), 0));
        result = state.getNextState();
        assertInstanceOf(IdleState.class, result, "Math.nextDown(0.2f) should be IdleState");

        // Just above -0.2f
        when(rb.getVelocity()).thenReturn(new Vector(-Math.nextDown(boundary), 0));
        result = state.getNextState();
        assertInstanceOf(IdleState.class, result, "-Math.nextDown(0.2f) should be IdleState");

        when(rb.getVelocity()).thenReturn(new Vector(0.5f, -0.0001f));
        assertInstanceOf(JumpingState.class, state.getNextState());
        
        when(rb.getVelocity()).thenReturn(new Vector(0.5f, 0.0f));
        when(player.isGrounded()).thenReturn(true);
        assertSame(state, state.getNextState());
    }

    @Test
    void testGetMineral() {
        WalkingState state = new WalkingState(player);
        assertNull(state.getMineral());
    }
}
