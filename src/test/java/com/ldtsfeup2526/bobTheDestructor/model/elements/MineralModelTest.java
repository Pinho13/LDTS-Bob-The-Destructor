package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralType;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PointingDirection;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MineralModelTest {
    private MineralModel mineral;

    @BeforeEach
    void setUp() {
        mineral = new MineralModel(new Position(1, 2), "fffdfe89", MineralType.PINK.ordinal());
    }

    @Test
    void mineralGetPositionTest() {
        assertEquals(1, mineral.getPosition().getX());
        assertEquals(2, mineral.getPosition().getY());
    }

    @Test
    void mineralSetPositionTest() {
        mineral.setPosition(new Position(3, 4));
        assertEquals(3, mineral.getPosition().getX());
        assertEquals(4, mineral.getPosition().getY());
    }

    @Test
    void mineralTypeTest() {
        assertEquals(MineralType.PINK, mineral.getType());
    }

    @Test
    void mineralDirectionParsesFromColorTest() {
        assertEquals(PointingDirection.DOWN, mineral.getDirection());
    }

    @Test
    void defaultStateIsUnselectedTest() {
        assertEquals(MineralState.UNSELECTED, mineral.getState());
    }
}
