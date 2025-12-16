package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class MineralModel extends ElementModel {

    private final MineralType type = MineralType.BLUE;

    public MineralModel(Position position) {
        super(position);
    }

    public MineralType getType() {
        return type;
    }
}
