package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuTest {
    private MainMenu mainMenu;

    @BeforeEach
    void setUp() {
        mainMenu = new MainMenu();
    }

    @Test
    void testCreateWidgets() {
        List<Widget> widgets = mainMenu.getWidgets();
        assertEquals(4, widgets.size());
        assertEquals(WidgetType.PLAY, widgets.get(0).getWidgetType());
        assertEquals(WidgetState.SELECTED, widgets.get(0).getWidgetState());
        assertEquals(WidgetType.CONFIG, widgets.get(1).getWidgetType());
        assertEquals(WidgetType.CREDITS, widgets.get(2).getWidgetType());
        assertEquals(WidgetType.EXIT, widgets.get(3).getWidgetType());
    }

    @Test
    void testMoveDown() {
        mainMenu.moveDown();
        assertEquals(WidgetType.CONFIG, mainMenu.getCurrentWidget().getWidgetType());

        mainMenu.moveDown();
        assertEquals(WidgetType.CREDITS, mainMenu.getCurrentWidget().getWidgetType());

        mainMenu.moveDown();
        assertEquals(WidgetType.EXIT, mainMenu.getCurrentWidget().getWidgetType());

        mainMenu.moveDown();
        assertEquals(WidgetType.PLAY, mainMenu.getCurrentWidget().getWidgetType());
    }

    @Test
    void testMoveUp() {
        mainMenu.moveUp();
        assertEquals(WidgetType.EXIT, mainMenu.getCurrentWidget().getWidgetType());

        mainMenu.moveUp();
        assertEquals(WidgetType.CREDITS, mainMenu.getCurrentWidget().getWidgetType());
    }
}
