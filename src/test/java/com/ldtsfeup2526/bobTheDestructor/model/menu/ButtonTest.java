package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ButtonTest {
    @Test
    void testConstructor() {
        Button button = new Button(ButtonType.PLAY);
        assertEquals(ButtonType.PLAY, button.getButtonType());
        assertEquals(ButtonState.UNSELECTED, button.getButtonState());
        assertEquals(0, button.getPosition().getX());
        assertEquals(0, button.getPosition().getY());

        Position pos = new Position(10, 20);
        Button button2 = new Button(ButtonType.EXIT, ButtonState.SELECTED, pos);
        assertEquals(ButtonType.EXIT, button2.getButtonType());
        assertEquals(ButtonState.SELECTED, button2.getButtonState());
        assertEquals(pos, button2.getPosition());
    }

    @Test
    void testSetButtonState() {
        Button button = new Button(ButtonType.CONFIG);
        button.setButtonState(ButtonState.CLICKED);
        assertEquals(ButtonState.CLICKED, button.getButtonState());
    }
}
