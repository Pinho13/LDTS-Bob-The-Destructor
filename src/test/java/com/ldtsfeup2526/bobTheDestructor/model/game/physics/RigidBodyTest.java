package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RigidBodyTest {
    private RigidBody rigidBody;
    private Position initialPosition;

    @BeforeEach
    void setUp() {
        initialPosition = new Position(10, 20);
        rigidBody = new RigidBody(initialPosition);
    }

    @Test
    void testConstructor() {
        assertEquals(10.0f, rigidBody.getPosition().getX());
        assertEquals(20.0f, rigidBody.getPosition().getY());
        assertEquals(0.0f, rigidBody.getVelocity().getX());
        assertEquals(0.0f, rigidBody.getVelocity().getY());
        assertEquals(0.0f, rigidBody.getAcceleration().getX());
        assertEquals(rigidBody.getGravity(), rigidBody.getAcceleration().getY());
    }

    @Test
    void testSettersAndGetters() {
        Vector velocity = new Vector(1, 2);
        rigidBody.setVelocity(velocity);
        assertEquals(velocity, rigidBody.getVelocity());

        Vector acceleration = new Vector(3, 4);
        rigidBody.setAcceleration(acceleration);
        assertEquals(acceleration, rigidBody.getAcceleration());

        Vector position = new Vector(5, 6);
        rigidBody.setPosition(position);
        assertEquals(position, rigidBody.getPosition());

        rigidBody.setSpeed(0.5f);
        assertEquals(0.5f, rigidBody.getSpeed());

        rigidBody.setFriction(0.3f);
        assertEquals(0.3f, rigidBody.getFriction());
    }

    @Test
    void testUpdate() {
        rigidBody.setAcceleration(new Vector(0.1f, 0.2f));
        rigidBody.update();
        assertEquals(0.1f, rigidBody.getVelocity().getX(), 0.001);
        assertEquals(0.2f, rigidBody.getVelocity().getY(), 0.001);


        rigidBody.setAcceleration(new Vector(2.0f, 11.0f));
        rigidBody.update();

        assertEquals(1.0f, rigidBody.getVelocity().getX(), 0.001);
        assertEquals(10.0f, rigidBody.getVelocity().getY(), 0.001);

        rigidBody.setVelocity(new Vector(-2.0f, -11.0f));
        rigidBody.setAcceleration(new Vector(0, 0));
        rigidBody.update();
        assertEquals(-1.0f, rigidBody.getVelocity().getX(), 0.001);
        assertEquals(-10.0f, rigidBody.getVelocity().getY(), 0.001);
    }

    @Test
    void testGetNextPos() {
        rigidBody.setVelocity(new Vector(1, 2));
        Vector nextPos = rigidBody.getNextPos();
        assertEquals(11.0f, nextPos.getX());
        assertEquals(22.0f, nextPos.getY());
    }

    @Test
    void testMoveRight() {
        rigidBody.setVelocity(new Vector(-1, 0));
        rigidBody.moveRight();
        assertEquals(0, rigidBody.getVelocity().getX());
        assertEquals(rigidBody.getSpeed(), rigidBody.getAcceleration().getX());

        rigidBody.setVelocity(new Vector(1, 0));
        rigidBody.moveRight();
        assertEquals(1, rigidBody.getVelocity().getX());
        assertEquals(rigidBody.getSpeed(), rigidBody.getAcceleration().getX());
    }

    @Test
    void testMoveLeft() {
        rigidBody.setVelocity(new Vector(1, 0));
        rigidBody.moveLeft();
        assertEquals(0, rigidBody.getVelocity().getX());
        assertEquals(-rigidBody.getSpeed(), rigidBody.getAcceleration().getX());

        rigidBody.setVelocity(new Vector(-1, 0));
        rigidBody.moveLeft();
        assertEquals(-1, rigidBody.getVelocity().getX());
        assertEquals(-rigidBody.getSpeed(), rigidBody.getAcceleration().getX());
    }

    @Test
    void testJump() {
        rigidBody.jump(5.0f);
        assertEquals(-5.0f, rigidBody.getVelocity().getY());
    }

    @Test
    void testApplyFrictionSmallVelocity() {
        rigidBody.setVelocity(new Vector(0.1f, 1.0f));
        rigidBody.setAcceleration(new Vector(1.0f, 1.0f));
        rigidBody.applyFriction();
        assertEquals(0, rigidBody.getVelocity().getX());
        assertEquals(0, rigidBody.getAcceleration().getX());
        assertEquals(1.0f, rigidBody.getVelocity().getY());
        assertEquals(1.0f, rigidBody.getAcceleration().getY());
    }

    @Test
    void testApplyFrictionLargeVelocity() {
        rigidBody.setVelocity(new Vector(1.0f, 1.0f));
        rigidBody.setAcceleration(new Vector(0, 0.4f));
        rigidBody.applyFriction();
        assertEquals(-rigidBody.getFriction(), rigidBody.getAcceleration().getX());

        rigidBody.setVelocity(new Vector(-1.0f, 1.0f));
        rigidBody.applyFriction();
        assertEquals(rigidBody.getFriction(), rigidBody.getAcceleration().getX());
    }
}
