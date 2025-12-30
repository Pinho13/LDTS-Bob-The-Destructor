package com.ldtsfeup2526.bobTheDestructor.model.game.physics;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColliderTest {
    private Collider collider;
    private Position position;
    private Size size;

    @BeforeEach
    void setUp() {
        position = new Position(10, 10);
        size = new Size(5, 5);
        collider = new Collider(position, size);
    }

    @Test
    void testConstructorWithDefaultSize() {
        Collider defaultCollider = new Collider(position);
        assertEquals(new Size(8, 8).getX(), defaultCollider.getSize().getX());
        assertEquals(new Size(8, 8).getY(), defaultCollider.getSize().getY());
    }

    @Test
    void testIsPointOver() {
        assertTrue(collider.isPointOver(new Position(12, 12)));

        assertTrue(collider.isPointOver(new Position(10, 10)));
        assertTrue(collider.isPointOver(new Position(15, 15)));

        assertFalse(collider.isPointOver(new Position(9, 10)));
        assertFalse(collider.isPointOver(new Position(10, 9)));
        assertFalse(collider.isPointOver(new Position(16, 10)));
        assertFalse(collider.isPointOver(new Position(10, 16)));
    }

    @Test
    void testIsColliderOver() {
        assertTrue(collider.isColliderOver(new Collider(new Position(11, 11), new Size(2, 2))));
        assertTrue(collider.isColliderOver(new Collider(new Position(8, 8), new Size(4, 4))));
        assertTrue(collider.isColliderOver(new Collider(new Position(15, 10), new Size(2, 2))));
        
        assertTrue(collider.isColliderOver(new Collider(new Position(5, 5), new Size(5, 5))));
        assertTrue(collider.isColliderOver(new Collider(new Position(15, 15), new Size(5, 5))));

        assertFalse(collider.isColliderOver(new Collider(new Position(16, 10), new Size(2, 2))));
        assertFalse(collider.isColliderOver(new Collider(new Position(10, 16), new Size(2, 2))));
        assertFalse(collider.isColliderOver(new Collider(new Position(0, 0), new Size(5, 5))));
    }

    @Test
    void testSettersAndGetters() {
        Position newPos = new Position(20, 20);
        collider.setPosition(newPos);
        assertEquals(newPos, collider.getPosition());

        Size newSize = new Size(10, 10);
        collider.setSize(newSize);
        assertEquals(newSize, collider.getSize());
    }

    @Test
    void testGetOppositeCorner() {
        Position opposite = collider.getOppositeCorner();
        assertEquals(15, opposite.getX());
        assertEquals(15, opposite.getY());
    }

    @Test
    void testColPosCheck() {
        Position checkPos = new Position(30, 30);
        Collider checkCollider = collider.colPosCheck(checkPos);
        assertEquals(checkPos, checkCollider.getPosition());
        assertEquals(collider.getSize(), checkCollider.getSize());
    }

    @Test
    void testSetters() {
        Collider collider = new Collider(new Position(0, 0));
        Position newPos = new Position(5, 5);
        Size newSize = new Size(10, 10);
        collider.setPosition(newPos);
        collider.setSize(newSize);
        assertEquals(newPos, collider.getPosition());
        assertEquals(newSize, collider.getSize());
    }
    @Test
    void testIsPointOverBoundaries() {
        assertTrue(collider.isPointOver(new Position(10, 10)));
        assertTrue(collider.isPointOver(new Position(15, 10)));
        assertTrue(collider.isPointOver(new Position(10, 15)));
        assertTrue(collider.isPointOver(new Position(15, 15)));
        
        assertFalse(collider.isPointOver(new Position(9, 10)));
        assertFalse(collider.isPointOver(new Position(16, 10)));
        assertFalse(collider.isPointOver(new Position(10, 9)));
        assertFalse(collider.isPointOver(new Position(10, 16)));
    }

    @Test
    void testIsColliderOverBoundaries() {
        // Left overlap
        assertTrue(collider.isColliderOver(new Collider(new Position(5, 10), new Size(5, 5))));
        assertFalse(collider.isColliderOver(new Collider(new Position(4, 10), new Size(5, 5))));
        
        // Top overlap
        assertTrue(collider.isColliderOver(new Collider(new Position(10, 5), new Size(5, 5))));
        assertFalse(collider.isColliderOver(new Collider(new Position(10, 4), new Size(5, 5))));

        // Right overlap
        assertTrue(collider.isColliderOver(new Collider(new Position(15, 10), new Size(5, 5))));
        assertFalse(collider.isColliderOver(new Collider(new Position(16, 10), new Size(5, 5))));

        // Bottom overlap
        assertTrue(collider.isColliderOver(new Collider(new Position(10, 15), new Size(5, 5))));
        assertFalse(collider.isColliderOver(new Collider(new Position(10, 16), new Size(5, 5))));

        // Internal
        assertTrue(collider.isColliderOver(new Collider(new Position(11, 11), new Size(1, 1))));
        
        // External
        assertFalse(collider.isColliderOver(new Collider(new Position(0, 0), new Size(1, 1))));
    }
    @Test
    void testIsColliderOverCombinations() {
        // C1 is (10, 10) to (15, 15)
        
        // 1. C1.x > C2.oppositeX
        assertFalse(collider.isColliderOver(new Collider(new Position(4, 10), new Size(5, 5)))); // oppositeX = 9
        
        // 2. C1.oppositeX < C2.x
        assertFalse(collider.isColliderOver(new Collider(new Position(16, 10), new Size(5, 5)))); // C2.x = 16
        
        // 3. C1.y > C2.oppositeY
        assertFalse(collider.isColliderOver(new Collider(new Position(10, 4), new Size(5, 5)))); // oppositeY = 9
        
        // 4. C1.oppositeY < C2.y
        assertFalse(collider.isColliderOver(new Collider(new Position(10, 16), new Size(5, 5)))); // C2.y = 16
    }
}
