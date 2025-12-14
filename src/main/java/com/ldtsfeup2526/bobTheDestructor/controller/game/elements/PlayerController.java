package com.ldtsfeup2526.bobTheDestructor.controller.game.elements;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;

import java.util.List;

public class PlayerController extends Controller<PlayerModel> {
    public PlayerController(PlayerModel player) {
        super(player);
    }

    @Override
    public void update(Game game, List<Action> actions) {
        if (actions.contains(Action.LEFT)) moveLeft();
        if (actions.contains(Action.RIGHT)) moveRight();
        if (actions.contains(Action.UP)) moveUp();
        if (actions.contains(Action.DOWN)) moveDown();
    }

    public void moveLeft() {
        getModel().setPosition(new Position(getModel().getPosition().getX()-1, getModel().getPosition().getY()));
    }

    public void moveRight() {
        getModel().setPosition(new Position(getModel().getPosition().getX()+1, getModel().getPosition().getY()));
    }

    public void moveUp() {
        getModel().setPosition(new Position(getModel().getPosition().getX(), getModel().getPosition().getY()-1));
    }

    public void moveDown() {
        getModel().setPosition(new Position(getModel().getPosition().getX(), getModel().getPosition().getY()+1));
    }
}