package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElementModelTest {
    private static class TestElementModel extends ElementModel {
        public TestElementModel(Position position) {
            super(position);
        }
    }

    @Test
    void testGetSetPosition() {
        Position pos1 = new Position(10, 20);
        TestElementModel element = new TestElementModel(pos1);
        assertEquals(pos1, element.getPosition());

        Position pos2 = new Position(30, 40);
        element.setPosition(pos2);
        assertEquals(pos2, element.getPosition());
    }
}
