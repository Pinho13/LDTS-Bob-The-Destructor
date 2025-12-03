package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.util.HashMap;

import static com.ldtsfeup2526.bobTheDestructor.controller.Collisions.*;

public abstract class ElementModel {
    private Position position;
    private static HashMap<Position, Boolean> occupancy;

    public ElementModel(Position position, HashMap<Position, Boolean> occupancy) {
        this.position = position;
        ElementModel.occupancy = occupancy;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {this.position = position;}

    public static HashMap<Position, Boolean> getOccupancy() {return occupancy;}

    public static void setOccupancy(Position position, Boolean bool) {occupancy.put(position, bool);}
}
