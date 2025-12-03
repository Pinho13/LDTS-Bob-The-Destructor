package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.util.HashMap;

public final class Collisions {
    public static boolean collidesLeft(Position p, HashMap<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getLeft(), false);
    }
    public static boolean collidesRight(Position p, HashMap<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getRight(), false);
    }
    public static boolean collidesUp(Position p, HashMap<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getUp(), false);
    }
    public static boolean collidesDown(Position p, HashMap<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getDown(), false);
    }
}