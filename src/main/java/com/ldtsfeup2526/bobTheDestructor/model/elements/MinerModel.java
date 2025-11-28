package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;
import java.util.HashMap;
import java.util.Map;

public class MinerModel extends ElementModel {
    public MinerModel(Position position, Map<Position, Boolean> occupancy) {
        super(position, occupancy);
    }
}
