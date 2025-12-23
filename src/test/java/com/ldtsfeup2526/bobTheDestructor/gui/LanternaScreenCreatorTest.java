package com.ldtsfeup2526.bobTheDestructor.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LanternaScreenCreatorTest {
    @Test
    void testCreateScreen() {
        LanternaScreenCreator creator = new LanternaScreenCreator();
        assertNotNull(creator);
    }
}
