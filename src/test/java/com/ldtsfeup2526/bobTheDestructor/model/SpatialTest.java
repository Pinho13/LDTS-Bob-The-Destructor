package com.ldtsfeup2526.bobTheDestructor.model;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SpatialTest {
    private static class TestSpatial extends Spatial<Double> {
        public TestSpatial(Number x, Number y) {
            super(x, y, Number::doubleValue);
        }
        public TestSpatial(Spatial<? extends Number> spatial) {
            super(spatial, Number::doubleValue);
        }
    }

    @Test
    void testGetters() {
        TestSpatial spatial = new TestSpatial(10.5, 20.7);
        assertEquals(10.5, spatial.getX());
        assertEquals(20.7, spatial.getY());
    }

    @Test
    void testCopyConstructor() {
        TestSpatial spatial1 = new TestSpatial(10.5, 20.7);
        TestSpatial spatial2 = new TestSpatial(spatial1);
        assertEquals(10.5, spatial2.getX());
        assertEquals(20.7, spatial2.getY());
    }

    @Test
    void testMagnitude() {
        TestSpatial spatial = new TestSpatial(3, 4);
        assertEquals(5.0, spatial.magnitude(), 0.0001);
    }

    @Test
    void testDistance() {
        TestSpatial spatial = new TestSpatial(0, 0);
        Position pos = new Position(3, 4);
        assertEquals(5.0, spatial.distance(pos), 0.0001);
    }

    @Test
    void testPrint() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        TestSpatial spatial = new TestSpatial(10, 20);
        spatial.print();
        
        System.setOut(originalOut);
        assertEquals("10.0, 20.0" + System.lineSeparator(), outContent.toString());
    }
}
