package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WidgetTest {
    @Test
    void testConstructor() {
        Widget widget = new Widget(WidgetType.PLAY);
        assertEquals(WidgetType.PLAY, widget.getWidgetType());
        assertEquals(WidgetState.UNSELECTED, widget.getWidgetState());
        assertEquals(0, widget.getPosition().getX());
        assertEquals(0, widget.getPosition().getY());

        Position pos = new Position(10, 20);
        Widget widget2 = new Widget(WidgetType.EXIT, WidgetState.SELECTED, pos);
        assertEquals(WidgetType.EXIT, widget2.getWidgetType());
        assertEquals(WidgetState.SELECTED, widget2.getWidgetState());
        assertEquals(pos, widget2.getPosition());
    }

    @Test
    void testSetWidgetState() {
        Widget widget = new Widget(WidgetType.CONFIG);
        widget.setWidgetState(WidgetState.CLICKED);
        assertEquals(WidgetState.CLICKED, widget.getWidgetState());
    }
}
