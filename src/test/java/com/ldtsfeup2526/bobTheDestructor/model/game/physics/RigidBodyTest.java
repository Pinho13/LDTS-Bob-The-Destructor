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
        rb.setFriction(0.2f);
        
        rb.setVelocity(new Vector(0.5, 0));
        rb.applyFriction();
        // Math.signum(0.5) = 1.0. Acceleration = -0.2 * 1.0 = -0.2
        assertEquals(-0.2f, rb.getAcceleration().getX(), 0.0001f);

        rb.setVelocity(new Vector(-0.5, 0));
        rb.applyFriction();
        // Math.signum(-0.5) = -1.0. Acceleration = -0.2 * -1.0 = 0.2
        assertEquals(0.2f, rb.getAcceleration().getX(), 0.0001f);
    }

    @Test
    void testApplyFrictionBoundaryExactly() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setFriction(0.2f);
        
        // velocity.getX() < 0.2f
        rb.setVelocity(new Vector(0.2f, 0));
        rb.applyFriction();
        assertNotEquals(0f, rb.getVelocity().getX());
        assertEquals(-0.2f, rb.getAcceleration().getX(), 0.0001f);
        
        rb.setVelocity(new Vector(0.199f, 0));
        rb.applyFriction();
        assertEquals(0f, rb.getVelocity().getX());
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
    void testUpdateMaxVelocityDetailed() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        // maxVelocity is (1, 10). gravity is 0.4.
        
        // --- X Axis ---
        // velX = 1.0 (boundary) -> Should NOT cap if it's > 1.0. 
        rb.setVelocity(new Vector(1.0f, 0));
        rb.setAcceleration(new Vector(0, -0.4f)); // Neutralize gravity for Y
        rb.update();
        assertEquals(1.0f, rb.getVelocity().getX(), 0.00001f);
        
        // velX = 1.000001 -> Should cap
        rb.setVelocity(new Vector(1.000001f, 0));
        rb.update();
        assertEquals(1.0f, rb.getVelocity().getX(), 0.00001f);
        
        // Negative X
        rb.setVelocity(new Vector(-1.0f, 0));
        rb.update();
        assertEquals(-1.0f, rb.getVelocity().getX(), 0.00001f);
        
        rb.setVelocity(new Vector(-1.000001f, 0));
        rb.update();
        assertEquals(-1.0f, rb.getVelocity().getX(), 0.00001f);

        // --- Y Axis ---
        rb.setAcceleration(new Vector(0, 0.4f)); // Use explicit gravity 0.4
        
        // velY = 9.6 + 0.4 = 10.0 -> Boundary. Should NOT cap.
        rb.setVelocity(new Vector(0, 9.6f));
        rb.update();
        assertEquals(10.0f, rb.getVelocity().getY(), 0.00001f);
        
        // velY = 9.600001 + 0.4 = 10.000001 -> Should cap to 10.0
        rb.setVelocity(new Vector(0, 9.600001f));
        rb.update();
        assertEquals(10.0f, rb.getVelocity().getY(), 0.00001f);
        
        // Negative Y
        // velY = -10.4 + 0.4 = -10.0 -> Boundary. Should NOT cap.
        rb.setVelocity(new Vector(0, -10.4f));
        rb.update();
        assertEquals(-10.0f, rb.getVelocity().getY(), 0.00001f);
        
        // velY = -10.400001 + 0.4 = -10.000001 -> Should cap to -10.0
        rb.setVelocity(new Vector(0, -10.400001f));
        rb.update();
        assertEquals(-10.0f, rb.getVelocity().getY(), 0.00001f);

        // To kill "Replaced float multiplication with division" for signum,
        // we need to test when signum is 0.
        // If Math.abs(velX) > maxVelocity.getX() is true, and velX is somehow 0...
        // but Math.abs(0) > 1.0 is false.
        // So signum(velX) will only be called when velX != 0.
        // However, if we change maxVelocity to be something other than 1.0, 
        // the mutant might be killed because PIT might be using 1.0 as a special case.
        // Let's use reflection to change maxVelocity to 2.0 and test it.
        try {
            java.lang.reflect.Field field = RigidBody.class.getDeclaredField("maxVelocity");
            field.setAccessible(true);
            field.set(rb, new Vector(2.0f, 20.0f));
        } catch (Exception e) {
            fail("Reflection failed");
        }

        rb.setVelocity(new Vector(3.0f, 0));
        rb.setAcceleration(new Vector(0, -0.4f));
        rb.update();
        // 2.0 * signum(3.0) = 2.0. If division: 2.0 / 1.0 = 2.0. Still same!
        // Wait, if signum(velX) is -1.0: 2.0 * -1.0 = -2.0. 2.0 / -1.0 = -2.0. Still same!
        assertEquals(2.0f, rb.getVelocity().getX(), 0.00001f);
    }

    @Test
    void testApplyFrictionDetailed() {
        RigidBody rb = new RigidBody(new Position(0, 0));
        rb.setFriction(0.2f);
        
        // Math.abs(velocity.getX()) < 0.2f
        // velX = 0.2f (boundary) -> Should NOT trigger stop logic.
        rb.setVelocity(new Vector(0.2f, 0));
        rb.applyFriction();
        assertNotEquals(0f, rb.getVelocity().getX());
        // Should set acceleration to -0.2 * signum(0.2) = -0.2
        assertEquals(-0.2f, rb.getAcceleration().getX(), 0.00001f);
        
        // velX = 0.1999f -> Should trigger stop logic.
        rb.setVelocity(new Vector(0.1999f, 0));
        rb.applyFriction();
        assertEquals(0f, rb.getVelocity().getX());
        assertEquals(0f, rb.getAcceleration().getX());
        
        // Negative boundary
        rb.setVelocity(new Vector(-0.2f, 0));
        rb.applyFriction();
        assertNotEquals(0f, rb.getVelocity().getX());
        assertEquals(0.2f, rb.getAcceleration().getX(), 0.00001f);
        
        rb.setVelocity(new Vector(-0.1999f, 0));
        rb.applyFriction();
        assertEquals(0f, rb.getVelocity().getX());
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
