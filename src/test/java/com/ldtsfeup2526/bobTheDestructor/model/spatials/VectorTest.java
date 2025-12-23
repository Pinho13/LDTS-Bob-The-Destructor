package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {
    @Test
    void testConstructorAndGetters() {
        Vector vec = new Vector(10.5, 20.7);
        assertEquals(10.5, vec.getX(), 0.0001);
        assertEquals(20.7, vec.getY(), 0.0001);
    }

    @Test
    void testCopyConstructor() {
        Vector vec1 = new Vector(10.5, 20.7);
        Vector vec2 = new Vector(vec1);
        assertEquals(10.5, vec2.getX(), 0.0001);
        assertEquals(20.7, vec2.getY(), 0.0001);
    }
}
