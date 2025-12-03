package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.BlockModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PickaxeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.Map;

public class BlockModelTest {
    private BlockModel block;
    private PickaxeModel pickaxe;
    private Map<Position, Boolean> occupancy;

    @BeforeEach
    void setup() {
        occupancy = Mockito.mock(Map.class);
        block = new BlockModel(new Position(1,2), BlockModel.Type.DIAMOND, occupancy);
    }

    @Test
    void blockGetTypeTest() {
        assert block.getType() == BlockModel.Type.DIAMOND;
    }

    @Test
    void blockGetPositionTest() {
        assert block.getPosition().getX() == 1;
        assert block.getPosition().getY() == 2;
    }

    @Test
    void blockSetPositionTest() {
        block.setPosition(new Position(3,4));
        assert block.getPosition().getX() == 3;
        assert block.getPosition().getY() == 4;
    }

    @Test
    void blockGetDurabilityTest() {
        assert block.getDurability() == 10;
    }

    @Test
    void blockSetDurabilityTest() {
        block.setDurability(8);
        assert block.getDurability() == 8;
    }

    @Test
    void blockDecreaseDurabilityTest() {
        pickaxe = new PickaxeModel(PickaxeModel.Type.GOLD);
        block.decreaseDurability(pickaxe);
        assert block.decreaseDurability(pickaxe) == 8;
    }
}