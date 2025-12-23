package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreenViewerTest {
    private static class TestScreenViewer extends ScreenViewer<Object> {
        public TestScreenViewer(Object model) {
            super(model);
        }

        @Override
        public void draw(GUI gui, double deltaTime) throws IOException {}
    }

    @Test
    void testGetModel() {
        Object model = new Object();
        TestScreenViewer viewer = new TestScreenViewer(model);
        assertEquals(model, viewer.getModel());
    }

    @Test
    void testDrawElement() {
        Object model = new Object();
        TestScreenViewer viewer = new TestScreenViewer(model);
        GUI gui = mock(GUI.class);
        ElementModel element = mock(ElementModel.class);
        ElementViewer<ElementModel> elementViewer = mock(ElementViewer.class);
        
        viewer.drawElement(gui, element, elementViewer, 0.1);
        verify(elementViewer).draw(element, gui, 0.1);
    }

    @Test
    void testDrawElements() {
        Object model = new Object();
        TestScreenViewer viewer = new TestScreenViewer(model);
        GUI gui = mock(GUI.class);
        ElementModel element1 = mock(ElementModel.class);
        ElementModel element2 = mock(ElementModel.class);
        ElementViewer<ElementModel> elementViewer = mock(ElementViewer.class);
        
        viewer.drawElements(gui, List.of(element1, element2), elementViewer, 0.1);
        verify(elementViewer).draw(element1, gui, 0.1);
        verify(elementViewer).draw(element2, gui, 0.1);
    }
}
