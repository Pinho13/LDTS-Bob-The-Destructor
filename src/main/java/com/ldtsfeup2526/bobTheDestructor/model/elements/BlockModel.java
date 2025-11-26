package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

public class BlockModel extends ElementModel {
    public enum Type {DIRT, ROCK, COAL, IRON, GOLD, DIAMOND}

    private final Type type;

    public BlockModel(Position position, Type type) {
        super(position);
        this.type = type;
    }

    public Type getType() {return type;}
}
