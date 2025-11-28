package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

public class MinerModelTest {
    private MinerModel miner;
    private Map<Position, Boolean> occupancy;

    @BeforeEach
    void setUp() {
        occupancy = Mockito.mock(Map.class);
        miner = new MinerModel(new Position(1,2), occupancy);
    }

    @Test
    void minerGetPositionTest() {
        assert miner.getPosition().getX() == 1;
        assert miner.getPosition().getY() == 2;
    }

    @Test
    void minerSetPositionTest() {
        miner.setPosition(new Position(3,4));
        assert miner.getPosition().getX() == 3;
        assert miner.getPosition().getY() == 4;
    }

    @Test
    void minerMoveLeftTest() {
        miner.moveLeft();
        assert miner.getPosition().getX() == 0;
    }

    @Test
    void minerMoveRightTest() {
        miner.moveRight();
        assert miner.getPosition().getX() == 2;
    }

    @Test
    void minerMoveUpTest() {
        miner.moveUp();
        assert miner.getPosition().getY() == 1; // up = y - 1
    }

    @Test
    void minerMoveDownTest() {
        miner.moveDown();
        assert miner.getPosition().getY() == 3; // down = y + 1
    }

    @Test
    void minerBlockedByOccupancy() {
        // Block the left tile
        occupancy.put(miner.getPosition().getLeft(), true);
        miner.moveLeft();
        // Position should remain unchanged due to collision
        assert miner.getPosition().getX() == 1;
        assert miner.getPosition().getY() == 2;
    }
}
