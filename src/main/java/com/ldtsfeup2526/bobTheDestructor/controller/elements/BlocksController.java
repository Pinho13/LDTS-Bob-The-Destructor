package com.ldtsfeup2526.bobTheDestructor.controller.elements;

import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.BlockModel;

import java.util.List;

public class BlocksController {
    private final BlockModel[][] blocks;

    public BlocksController(BlockModel[][] blocks) {
        this.blocks = blocks;
    }

    public void update(List<Action> actions) {

    }
}
