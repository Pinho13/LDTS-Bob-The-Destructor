package com.ldtsfeup2526.bobTheDestructor.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ResolutionTest {
    @Test
    void testGetters() {
        Resolution res = new Resolution(100, 200);
        assertEquals(100, res.width());
        assertEquals(200, res.height());
    }

    @Test
    void testEqualsAndHashCode() {
        Resolution res1 = new Resolution(100, 200);
        Resolution res2 = new Resolution(100, 200);
        Resolution res3 = new Resolution(200, 100);

        assertEquals(res1, res2);
        assertNotEquals(res1, res3);
        assertNotEquals(res1, null);
        assertNotEquals(res1, "string");
        assertEquals(res1.hashCode(), res2.hashCode());
    }

    @Test
    void testToString() {
        Resolution res = new Resolution(100, 200);
        assertEquals("Resolution[width=100, height=200]", res.toString());
    }
}
