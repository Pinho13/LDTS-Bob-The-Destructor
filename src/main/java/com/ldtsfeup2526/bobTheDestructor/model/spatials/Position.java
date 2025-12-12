package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;

public class Position extends Spatial<Integer> {

    public Position(int x, int y) {
        super(x, y);
    }

    public Position(Spatial<?> spatial) {
        super(spatial);
    }
}
