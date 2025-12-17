package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BlockModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralType;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PickaxeModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockModelTest {
    private BlockModel block;
    private PickaxeModel pickaxe;

    @BeforeEach
    void setup() {
        block = new BlockModel(new Position(1, 2), BlockModel.Type.DIAMOND);
        pickaxe = new PickaxeModel(MineralType.PINK);
    }

    @Test
    void blockGetTypeTest() {
        assertEquals(BlockModel.Type.DIAMOND, block.getType());
    }

    @Test
    void blockGetPositionTest() {
        assertEquals(1, block.getPosition().getX());
        assertEquals(2, block.getPosition().getY());
    }

    @Test
    void blockSetPositionTest() {
        block.setPosition(new Position(3, 4));
        assertEquals(3, block.getPosition().getX());
        assertEquals(4, block.getPosition().getY());
    }

    @Test
    void blockGetDurabilityTest() {
        assertEquals(10, block.getDurability());
    }

    @Test
    void blockSetDurabilityTest() {
        block.setDurability(8);
        assertEquals(8, block.getDurability());
    }

    @Test
    void blockDecreaseDurabilityTest() {
        int decreased = block.decreaseDurability(pickaxe);
        assertEquals(block.getDurability() - pickaxe.getDamage(), decreased);
        assertEquals(10, block.getDurability());
    }
}