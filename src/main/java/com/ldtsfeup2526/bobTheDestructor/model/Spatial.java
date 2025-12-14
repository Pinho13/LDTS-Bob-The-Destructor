package com.ldtsfeup2526.bobTheDestructor.model;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class Spatial<T extends Number> {
    private final T x;
    private final T y;
    public Spatial(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Spatial(Spatial<? extends Number> spatial, Function<Number, T> converter) {
        this.x = converter.apply(spatial.getX());
        this.y = converter.apply(spatial.getY());
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public void print() {
        System.out.println((x.toString() + ", " + y.toString()));
    }

}
