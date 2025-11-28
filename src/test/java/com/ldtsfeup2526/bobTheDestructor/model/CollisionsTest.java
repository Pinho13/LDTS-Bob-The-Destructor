package com.ldtsfeup2526.bobTheDestructor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionsTest {
    Position p1, p2;
    Map<Position, Boolean> occupancy;

    @BeforeEach
    void setUp() {
        p1 = new Position(5, 5);
        p2 = new Position(8, 8);
        occupancy = new HashMap<>();
        occupancy.put(new Position(5,4), true);
        occupancy.put(new Position(5,6), true);
        occupancy.put(new Position(6,5), true);
        occupancy.put(new Position(4,5), true);
    }

    @Test
    void collidesLeftTest() {
        assertTrue(Collisions.collidesLeft(p1, occupancy));
        assertFalse(Collisions.collidesLeft(p2, occupancy));
    }

    @Test
    void collidesRightTest() {
        assertTrue(Collisions.collidesRight(p1, occupancy));
        assertFalse(Collisions.collidesRight(p2, occupancy));
    }

    @Test
    void collidesUoTest() {
        assertTrue(Collisions.collidesUp(p1, occupancy));
        assertFalse(Collisions.collidesUp(p2, occupancy));
    }

    @Test
    void collidesDownTest() {
        assertTrue(Collisions.collidesDown(p1, occupancy));
        assertFalse(Collisions.collidesDown(p2, occupancy));
    }
}
