package com.ldtsfeup2526.bobTheDestructor.model;

import java.util.Map;

public final class Collisions {
    public static boolean collidesLeft(Position p, Map<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getLeft(), false);
    }
    public static boolean collidesRight(Position p, Map<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getRight(), false);
    }
    public static boolean collidesUp(Position p, Map<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getUp(), false);
    }
    public static boolean collidesDown(Position p, Map<Position, Boolean> occupancy) {
        return occupancy.getOrDefault(p.getDown(), false);
    }
}