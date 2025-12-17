package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private Position p;

    @BeforeEach
    void setUp() {
        p = new Position(3, 4);
    }

    @Test
    void getTest() {
        assertEquals(3, p.getX());
        assertEquals(4, p.getY());
    }

    @Test
    void copyConstructorTest() {
        Position copy = new Position(p);
        assertEquals(p.getX(), copy.getX());
        assertEquals(p.getY(), copy.getY());
    }

    @Test
    void magnitudeTest() {
        assertEquals(5.0, p.magnitude(), 1e-9);
    }

    @Test
    void distanceTest() {
        Position other = new Position(6, 8);
        assertEquals(5.0, p.distance(other), 1e-9);
    }
}
