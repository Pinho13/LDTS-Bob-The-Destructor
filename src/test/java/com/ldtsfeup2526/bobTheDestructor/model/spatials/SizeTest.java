package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SizeTest {
    @Test
    void testConstructorAndGetters() {
        Size size = new Size(100, 200);
        assertEquals(100, size.getX());
        assertEquals(200, size.getY());
    }

    @Test
    void testCopyConstructor() {
        Size size1 = new Size(100, 200);
        Size size2 = new Size(size1);
        assertEquals(100, size2.getX());
        assertEquals(200, size2.getY());
    }
}
