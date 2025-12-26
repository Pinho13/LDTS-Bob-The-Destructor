package com.ldtsfeup2526.bobTheDestructor.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolutionTest {
    @Test
    void testGetters() {
        Resolution res = new Resolution(100, 200);
        assertEquals(100, res.width());
        assertEquals(200, res.height());
    }

    @Test
    void testEquals() {
        Resolution res1 = new Resolution(100, 200);
        Resolution res2 = new Resolution(100, 200);
        Resolution res3 = new Resolution(200, 100);

        assertEquals(res1, res2);
        assert(res1.equals(res2));
        assert(!res1.equals(res3));
        assert(!res1.equals(null));
        assert(!res1.equals("string"));
    }

    @Test
    void testHashCode() {
        Resolution res1 = new Resolution(100, 200);
        Resolution res2 = new Resolution(100, 200);
        assertEquals(res1.hashCode(), res2.hashCode());
    }

    @Test
    void testToString() {
        Resolution res = new Resolution(100, 200);
        assertEquals("Resolution[width=100, height=200]", res.toString());
    }
}
