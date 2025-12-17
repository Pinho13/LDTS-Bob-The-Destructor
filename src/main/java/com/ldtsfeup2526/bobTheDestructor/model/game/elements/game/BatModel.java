package com.ldtsfeup2526.bobTheDestructor.model.game.elements.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public class BatModel extends ElementModel {
    private BatState batState = BatState.SLEEPING;

    public BatModel(Position position) {
        super(position);
    }

    public BatState getBatState() {
        return batState;
    }

    public void setBatState(BatState batState) {
        this.batState = batState;
    }
}
