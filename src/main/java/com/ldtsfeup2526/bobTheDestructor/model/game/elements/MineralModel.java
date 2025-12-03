package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import java.util.HashMap;
import java.util.Map;

public class MineralModel extends ElementModel {
    public enum Type {COAL, IRON, GOLD, DIAMOND}

    private final int value;

    private final Type type;

    public MineralModel(Position position, Type type, Map<Position, Boolean> occupancy) {
        super(position, occupancy);
        this.type = type;
        switch (type) {
            case DIAMOND: value = 20; break;
            case GOLD: value = 10; break;
            case IRON: value = 3; break;
            default: value = 1; break;
        }
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
