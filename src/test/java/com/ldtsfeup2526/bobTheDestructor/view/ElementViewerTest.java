package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ElementViewerTest {
    @Test
    void testInterface() {
        ElementViewer<Object> viewer = new ElementViewer<Object>() {
            @Override
            public void draw(Object model, GUI gui, double deltaTime) {}
        };
        assertNotNull(viewer);
    }
}
