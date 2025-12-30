package com.ldtsfeup2526.bobTheDestructor.model;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpatialTest {
    @Test
    void testSpatial() {
        Spatial<Float> s = new Spatial<>(10, 20, Number::floatValue) {};
        assertEquals(10f, s.getX());
        assertEquals(20f, s.getY());
        
        Spatial<Integer> s2 = new Spatial<>(s, Number::intValue) {};
        assertEquals(10, s2.getX());
        assertEquals(20, s2.getY());
        
        assertEquals(Math.sqrt(500), s.magnitude(), 0.0001);
        assertEquals(0.0, s.distance(new Position(10, 20)), 0.0001);
        assertEquals(5.0, s.distance(new Position(13, 24)), 0.0001);
        
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        s.print();
        assertEquals("10.0, 20.0" + System.lineSeparator(), outContent.toString());
        System.setOut(System.out);
    }

    @Test
    void testEqualsAndHashCode() {
        Spatial<Integer> s1 = new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(10, 20);
        Spatial<Integer> s2 = new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(10, 20);
        Spatial<Integer> s3 = new com.ldtsfeup2526.bobTheDestructor.model.spatials.Position(10, 21);
        
        // Since Spatial doesn't override equals, we check coordinates
        assertEquals(s1.getX(), s2.getX());
        assertEquals(s1.getY(), s2.getY());
        assertNotEquals(s1, s2);
        
        assertNotEquals(s1, s3);
        assertNotEquals(s1, null);
    }
}
