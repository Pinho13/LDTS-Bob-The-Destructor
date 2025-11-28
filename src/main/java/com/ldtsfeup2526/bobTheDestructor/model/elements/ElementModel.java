package com.ldtsfeup2526.bobTheDestructor.model.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.util.Map;

import static com.ldtsfeup2526.bobTheDestructor.model.Collisions.*;

public abstract class ElementModel {
    private Position position;
    private Map<Position, Boolean> occupancy;

    public ElementModel(Position position, Map<Position, Boolean> occupancy) {
        this.position = position;
        this.occupancy = occupancy;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
        occupancy.put(position, true);
    }

    public void moveLeft() {
        if (!collidesLeft(position, occupancy)) {
            occupancy.put(position.getLeft(), true);
            this.position = position.getLeft();
        }
    }

    public void moveRight() {
        if (!collidesRight(position, occupancy)) {
            occupancy.put(position.getRight(), true);
            this.position = position.getRight();
        }
    }

    public void moveUp() {
        if (!collidesUp(position, occupancy)) {
            occupancy.put(position.getUp(), true);
            this.position = position.getUp();
        }
    }

    public void moveDown() {
        if (!collidesDown(position, occupancy)) {
            occupancy.put(position.getDown(), true);
            this.position = position.getDown();
        }
    }
}
