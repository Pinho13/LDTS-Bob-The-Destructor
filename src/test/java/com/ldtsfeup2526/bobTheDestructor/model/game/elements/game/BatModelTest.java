package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BatModelTest {
    @Test
    void testConstructorAndGetters() {
        Position pos = new Position(10, 20);
        BatModel bat = new BatModel(pos);
        
        assertEquals(pos, bat.getPosition());
        assertEquals(BatState.SLEEPING, bat.getBatState());
    }

    @Test
    void testSetBatState() {
        BatModel bat = new BatModel(new Position(0, 0));
        bat.setBatState(BatState.FLAPPING);
        assertEquals(BatState.FLAPPING, bat.getBatState());
    }

    @Test
    void testEnumBatState() {
        assertEquals(2, BatState.values().length);
        assertEquals(BatState.SLEEPING, BatState.valueOf("SLEEPING"));
        assertEquals(BatState.FLAPPING, BatState.valueOf("FLAPPING"));
    }
}
