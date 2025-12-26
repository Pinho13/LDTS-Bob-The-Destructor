package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MineralModelTest {
    @Test
    void testConstructorAndGetters() {
        Position pos = new Position(10, 20);
        MineralModel mineral = new MineralModel(pos, "fffdfe89", 0); // DOWN, PINK
        
        assertEquals(pos, mineral.getPosition());
        assertEquals(MineralType.PINK, mineral.getType());
        assertEquals(PointingDirection.DOWN, mineral.getDirection());
        assertEquals(MineralState.UNSELECTED, mineral.getState());
    }

    @Test
    void testDirectionParser() {
        Position pos = new Position(0, 0);
        assertEquals(PointingDirection.DOWN, new MineralModel(pos, "fffdfe89", 0).getDirection());
        assertEquals(PointingDirection.LEFT, new MineralModel(pos, "ffff5dcc", 0).getDirection());
        assertEquals(PointingDirection.RIGHT, new MineralModel(pos, "ff5efdf7", 0).getDirection());
        assertEquals(PointingDirection.UP, new MineralModel(pos, "anything else", 0).getDirection());
    }

    @Test
    void testSetState() {
        MineralModel mineral = new MineralModel(new Position(0, 0), "fffdfe89", 0);
        mineral.setState(MineralState.SELECTED);
        assertEquals(MineralState.SELECTED, mineral.getState());
    }

    @Test
    void testNotifyWhenAnimFinished() {
        MineralModel mineral = new MineralModel(new Position(0, 0), "fffdfe89", 0);
        mineral.notifyWhenAnimFinished("OtherAnim");
        assertEquals(MineralState.UNSELECTED, mineral.getState());
        
        mineral.notifyWhenAnimFinished("CrackAnim");
        assertEquals(MineralState.CLEANUP, mineral.getState());
    }

    @Test
    void testEnums() {
        assertEquals(3, MineralType.values().length);
        assertEquals(MineralType.PINK, MineralType.valueOf("PINK"));
        
        assertEquals(4, MineralState.values().length);
        assertEquals(MineralState.UNSELECTED, MineralState.valueOf("UNSELECTED"));
        
        assertEquals(4, PointingDirection.values().length);
        assertEquals(PointingDirection.UP, PointingDirection.valueOf("UP"));
    }
}
