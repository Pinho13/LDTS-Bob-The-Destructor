package com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MiningStateTest {
    private PlayerModel playerModel;
    private MineralModel mineralModel;
    private MiningState miningState;

    @BeforeEach
    void setUp() {
        playerModel = mock(PlayerModel.class);
        mineralModel = mock(MineralModel.class);
        RigidBody rigidBody = mock(RigidBody.class);
        Vector velocity = new Vector(10, 10);
        Vector acceleration = new Vector(10, 10);

        when(playerModel.getRigidBody()).thenReturn(rigidBody);
        when(rigidBody.getVelocity()).thenReturn(velocity);
        when(rigidBody.getAcceleration()).thenReturn(acceleration);

        miningState = new MiningState(playerModel, mineralModel);
    }

    @Test
    void testConstructorSetsRigidBody() {
        RigidBody rigidBody = playerModel.getRigidBody();
        verify(rigidBody).setAcceleration(any(Vector.class));
        verify(rigidBody).setVelocity(any(Vector.class));
    }

    @Test
    void testMovePlayerLeft() {
        miningState.movePlayerLeft();
        verify(playerModel.getRigidBody(), never()).moveLeft();
    }

    @Test
    void testMovePlayerRight() {
        miningState.movePlayerRight();
        verify(playerModel.getRigidBody(), never()).moveRight();
    }

    @Test
    void testJump() {
        miningState.jump();
        verify(playerModel.getRigidBody(), never()).jump(anyFloat());
    }

    @Test
    void testGetNextState() {
        assertEquals(miningState, miningState.getNextState());
    }

    @Test
    void testGetMineral() {
        assertEquals(mineralModel, miningState.getMineral());
    }
}
