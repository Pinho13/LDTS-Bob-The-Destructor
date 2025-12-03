package com.ldtsfeup2526.bobTheDestructor.controller.elements;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.BlockModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PickaxeModel;

import java.util.HashMap;
import java.util.List;

import static com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel.getOccupancy;

public class BlockController extends Controller<BlockModel> {
    private static HashMap<Position, Boolean> occupancy = getOccupancy();

    public BlockController(BlockModel block) {
        super(block);
    }

    @Override
    public void update(List<Action> actions) {

    }

    public void decreaseDurability(PickaxeModel pickaxe) {
        getModel().setDurability(getModel().getDurability() - pickaxe.getDamage());
    }
}
