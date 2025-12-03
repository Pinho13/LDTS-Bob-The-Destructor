package com.ldtsfeup2526.bobTheDestructor.controller.elements;

import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;

import java.util.HashMap;
import java.util.List;

import static com.ldtsfeup2526.bobTheDestructor.controller.Collisions.*;
import static com.ldtsfeup2526.bobTheDestructor.controller.Collisions.collidesDown;
import static com.ldtsfeup2526.bobTheDestructor.model.game.elements.ElementModel.getOccupancy;

public class PlayerController extends Controller<PlayerModel> {
    private static HashMap<Position, Boolean> occupancy = getOccupancy();

    public PlayerController(PlayerModel player) {
        super(player);
    }

    public void update(List<Action> actions) {
        if (actions.contains(Action.LEFT)) moveLeft();
        if (actions.contains(Action.RIGHT)) moveRight();
        if (actions.contains(Action.UP)) moveUp();
        if (actions.contains(Action.DOWN)) moveDown();
    }

    public void moveLeft() {
        if (!collidesLeft(getModel().getPosition(), occupancy)) {
            this.occupancy.put(getModel().getPosition(), false);
            getModel().setPosition(getModel().getPosition().getLeft());
            this.occupancy.put(getModel().getPosition(), true);
        }
    }

    public void moveRight() {
        if (!collidesRight(getModel().getPosition(), occupancy)) {
            this.occupancy.put(getModel().getPosition(), false);
            getModel().setPosition(getModel().getPosition().getRight());
            this.occupancy.put(getModel().getPosition(), true);
        }
    }

    public void moveUp() {
        if (!collidesUp(getModel().getPosition(), occupancy)) {
            this.occupancy.put(getModel().getPosition(), false);
            getModel().setPosition(getModel().getPosition().getUp());
            this.occupancy.put(getModel().getPosition(), true);
        }
    }

    public void moveDown() {
        if (!collidesDown(getModel().getPosition(), occupancy)) {
            this.occupancy.put(getModel().getPosition(), false);
            getModel().setPosition(getModel().getPosition().getDown());
            this.occupancy.put(getModel().getPosition(), true);
        }
    }
}
