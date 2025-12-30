package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RigidBodyTest {
    @Test
    void testRigidBodyBasic() {
        RigidBody rb = new RigidBody(new Position(10, 20));
        assertEquals(10.0f, rb.getPosition().getX());
        assertEquals(20.0f, rb.getPosition().getY());
        
        rb.setVelocity(new Vector(1.0, 2.0));
        assertEquals(1.0f, rb.getVelocity().getX());
        assertEquals(2.0f, rb.getVelocity().getY());
        
        rb.setAcceleration(new Vector(0.1, 0.2));
        assertEquals(0.1f, rb.getAcceleration().getX());
        assertEquals(0.2f, rb.getAcceleration().getY());
        
        rb.setPosition(new Vector(100, 200));
        assertEquals(100.0f, rb.getPosition().getX());
        
        rb.update();
        assertEquals(1.0f, rb.getVelocity().getX()); // maxVelocity.x = 1
        assertEquals(2.2f, rb.getVelocity().getY());
        
        Vector next = rb.getNextPos();
        assertEquals(101.0f, next.getX());
        assertEquals(202.2f, next.getY());
    }

    @Test
    void testMovement() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        
        rb.moveRight();
        assertEquals(rb.getSpeed(), rb.getAcceleration().getX());
        
        rb.setVelocity(new Vector(-1, 0));
        rb.moveRight();
        assertEquals(0f, rb.getVelocity().getX());
        
        rb.moveLeft();
        assertEquals(-rb.getSpeed(), rb.getAcceleration().getX());
        
        rb.setVelocity(new Vector(1, 0));
        rb.moveLeft();
        assertEquals(0f, rb.getVelocity().getX());
        
        rb.jump(5.0f);
        assertEquals(-5.0f, rb.getVelocity().getY());
    }

    @Test
    void testFriction() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setVelocity(new Vector(0.1, 0));
        rb.applyFriction();
        assertEquals(0f, rb.getVelocity().getX());
        assertEquals(0f, rb.getAcceleration().getX());
        
        rb.setVelocity(new Vector(0.5, 0));
        rb.applyFriction();
        assertEquals(-rb.getFriction(), rb.getAcceleration().getX());

        rb.setVelocity(new Vector(-0.5, 0));
        rb.applyFriction();
        assertEquals(rb.getFriction(), rb.getAcceleration().getX());
    }

    @Test
    void testUpdateMaxVelocity() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setVelocity(new Vector(2, 20));
        rb.setAcceleration(new Vector(0, 0));
        rb.update();
        // maxVelocity is (1, 10), gravity is 0.4
        // velocity.y = 20 + 0 + 0.4 = 20.4 -> cap at 10
        assertEquals(1.0f, rb.getVelocity().getX());
        assertEquals(10.0f, rb.getVelocity().getY());

        rb.setVelocity(new Vector(-2, -20));
        rb.update();
        // velocity.y = -20 + 0 + 0.4 = -19.6 -> cap at -10
        assertEquals(-1.0f, rb.getVelocity().getX());
        assertEquals(-10.0f, rb.getVelocity().getY());
    }

    @Test
    void testUpdateVelocityNearZero() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        // maxVelocity is (1, 10), gravity is 0.4
        // update() just adds acceleration to velocity and caps it.
        // It does NOT set to 0 if near zero. applyFriction() does that.
        
        rb.setVelocity(new Vector(0.005, 0));
        rb.setAcceleration(new Vector(0, 0));
        rb.update();
        assertEquals(0.005f, rb.getVelocity().getX(), 0.0001f);

        rb.applyFriction();
        assertEquals(0f, rb.getVelocity().getX());
    }

    @Test
    void testApplyFrictionOpposite() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setVelocity(new Vector(0.5, 0));
        rb.applyFriction();
        assertEquals(-rb.getFriction(), rb.getAcceleration().getX());

        rb.setVelocity(new Vector(-0.5, 0));
        rb.applyFriction();
        assertEquals(rb.getFriction(), rb.getAcceleration().getX());
    }
    @Test
    void testMoveRightFromLeft() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setVelocity(new Vector(-0.5, 0));
        rb.moveRight();
        assertEquals(0f, rb.getVelocity().getX());
        assertEquals(rb.getSpeed(), rb.getAcceleration().getX());
    }

    @Test
    void testMoveLeftFromRight() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setVelocity(new Vector(0.5, 0));
        rb.moveLeft();
        assertEquals(0f, rb.getVelocity().getX());
        assertEquals(-rb.getSpeed(), rb.getAcceleration().getX());
    }

    @Test
    void testJump() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.jump(5.0f);
        assertEquals(-5.0f, rb.getVelocity().getY());
    }
    @Test
    void testApplyFrictionNearZero() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setVelocity(new Vector(0.15, 0));
        rb.applyFriction();
        assertEquals(0f, rb.getVelocity().getX());
        assertEquals(0f, rb.getAcceleration().getX());
    }
    @Test
    void testGettersSetters() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setSpeed(0.5f);
        assertEquals(0.5f, rb.getSpeed());
        rb.setFriction(0.3f);
        assertEquals(0.3f, rb.getFriction());
        assertEquals(0.4f, rb.getGravity());
    }
}
