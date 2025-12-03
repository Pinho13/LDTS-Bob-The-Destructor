package com.ldtsfeup2526.bobTheDestructor.model.game.elements;

import com.ldtsfeup2526.bobTheDestructor.model.Position;

import java.util.HashMap;

import static com.ldtsfeup2526.bobTheDestructor.controller.Collisions.*;

public abstract class ElementModel {
    private Position position;
    protected HashMap<Position, Boolean> occupancy;

    public ElementModel(Position position, HashMap<Position, Boolean> occupancy) {
        this.position = position;
        this.occupancy = occupancy;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.occupancy.put(position, true);
    }

    public void moveLeft() {
        if (!collidesLeft(position, occupancy)) {
            this.occupancy.put(position, false);
            this.position = position.getLeft();
            this.occupancy.put(position, true);
        }
    }

    public void moveRight() {
        if (!collidesRight(position, occupancy)) {
            this.occupancy.put(position, false);
            this.position = position.getRight();
            this.occupancy.put(position, true);
        }
    }

    public void moveUp() {
        if (!collidesUp(position, occupancy)) {
            this.occupancy.put(position, false);
            this.position = position.getUp();
            this.occupancy.put(position, true);
        }
    }

    public void moveDown() {
        if (!collidesDown(position, occupancy)) {
            this.occupancy.put(position, false);
            this.position = position.getDown();
            this.occupancy.put(position, false);
        }
    }
}
