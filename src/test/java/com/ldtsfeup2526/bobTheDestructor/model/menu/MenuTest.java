package com.ldtsfeup2526.bobTheDestructor.model.menu;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    @Test
    void testMenuLogic() {
        Menu menu = new Menu() {
            @Override
            protected List<Widget> createWidgets() {
                List<Widget> widgets = new ArrayList<>();
                widgets.add(new Widget(WidgetType.PLAY, WidgetState.SELECTED, null));
                widgets.add(new Widget(WidgetType.EXIT, WidgetState.UNSELECTED, null));
                widgets.add(new Widget(WidgetType.CREDITS, WidgetState.UNSELECTED, null));
                return widgets;
            }
        };

        assertEquals(3, menu.getNumberOfWidgets());
        assertEquals(WidgetType.PLAY, menu.getCurrentWidget().getWidgetType());
        
        menu.moveDown();
        assertEquals(WidgetType.EXIT, menu.getCurrentWidget().getWidgetType());
        
        menu.moveDown();
        assertEquals(WidgetType.CREDITS, menu.getCurrentWidget().getWidgetType());

        menu.moveDown();
        assertEquals(WidgetType.PLAY, menu.getCurrentWidget().getWidgetType()); // Wrap around

        menu.moveUp();
        assertEquals(WidgetType.CREDITS, menu.getCurrentWidget().getWidgetType()); // Wrap around up
        
        menu.moveUp();
        assertEquals(WidgetType.EXIT, menu.getCurrentWidget().getWidgetType());
        
        assertNotNull(menu.getWidgets());
    }

    @Test
    void testMainMenu() {
        MainMenu menu = new MainMenu();
        assertEquals(4, menu.getNumberOfWidgets());
        // Verify positions are exactly correct (using Game.resolution)
        // res.width = 165, res.height = 90
        
        // Widget 1: center width, center height - 15
        assertEquals(165/2, menu.getWidgets().get(0).getPosition().getX());
        assertEquals(90/2 - 15, menu.getWidgets().get(0).getPosition().getY());
        
        // Widget 2: center width, center height - 5
        assertEquals(165/2, menu.getWidgets().get(1).getPosition().getX());
        assertEquals(90/2 - 5, menu.getWidgets().get(1).getPosition().getY());
        
        // Widget 3: center width, center height + 5
        assertEquals(165/2, menu.getWidgets().get(2).getPosition().getX());
        assertEquals(90/2 + 5, menu.getWidgets().get(2).getPosition().getY());
        
        // Widget 4: center width, center height + 15
        assertEquals(165/2, menu.getWidgets().get(3).getPosition().getX());
        assertEquals(90/2 + 15, menu.getWidgets().get(3).getPosition().getY());
    }

    @Test
    void testSettingsMenu() {
        SettingsMenu menu = new SettingsMenu();
        assertEquals(3, menu.getNumberOfWidgets());
        
        // Master volume: width/2, height/2 - 20
        assertEquals(165/2, menu.getWidgets().get(0).getPosition().getX());
        assertEquals(90/2 - 20, menu.getWidgets().get(0).getPosition().getY());
        
        // Music volume: width/2, height/2
        assertEquals(165/2, menu.getWidgets().get(1).getPosition().getX());
        assertEquals(90/2, menu.getWidgets().get(1).getPosition().getY());
        
        // SFX volume: width/2, height/2 + 20
        assertEquals(165/2, menu.getWidgets().get(2).getPosition().getX());
        assertEquals(90/2 + 20, menu.getWidgets().get(2).getPosition().getY());
    }
}
