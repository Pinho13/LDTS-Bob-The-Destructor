package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PickaxeModelTest {
    private PickaxeModel pickaxe;

    @BeforeEach
    void setUp() {
        pickaxe = new PickaxeModel(MineralType.PINK);
    }

    @Test
    void pickaxeGetDamageTest() {
        assertEquals(0, pickaxe.getDamage());
    }

    @Test
    void pickaxeGetTypeTest() {
        assertEquals(MineralType.PINK, pickaxe.getType());
    }

    @Test
    void pickaxeSetTypeTest() {
        pickaxe.setType(MineralType.BLUE);
        assertEquals(MineralType.BLUE, pickaxe.getType());
        assertEquals(0, pickaxe.getDamage());
    }
}