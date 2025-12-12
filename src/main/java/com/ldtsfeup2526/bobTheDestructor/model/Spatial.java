package com.ldtsfeup2526.bobTheDestructor.model;

public abstract class Spatial<T> {
    private final T x;
    private final T y;
    public Spatial(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Spatial(Spatial<?> spatial) {
        this.x = (T) spatial.getX();
        this.y = (T) spatial.getY();
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
