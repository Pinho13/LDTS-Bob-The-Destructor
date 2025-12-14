package com.ldtsfeup2526.bobTheDestructor.model.spatials;

import com.ldtsfeup2526.bobTheDestructor.model.Spatial;

public class Size extends Spatial<Integer> {
    public Size(int x, int y) {
        super(x, y);
    }

    public Size(Spatial<?> spatial) {
        super(spatial, Number::intValue);
    }
}
