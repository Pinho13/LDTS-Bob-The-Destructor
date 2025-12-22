package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

public abstract class ElementModel {
    private Position position;

    public ElementModel(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
